package com.demo.demo.common.kafka.impl;

import com.demo.demo.common.SpringContextHolder;
import com.demo.demo.common.config.BootstrapConfig;
import com.demo.demo.common.config.kafka.KafkaConfig;
import com.demo.demo.common.kafka.EventManager;
import com.demo.demo.common.kafka.EventModel;
import com.demo.demo.common.kafka.consts.EventTypeEnum;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Component
public class EventManagerImpl implements EventManager, ApplicationRunner {

    private static final Logger logger = LoggerFactory.getLogger(EventManagerImpl.class);

    /**
     * 生产者
     */
    private KafkaProducer<String, String> producer;
    /**
     * 单播
     */
    private ExecutorService exclusivePool;
    private Map<String, List<EventModel>> exclusiveModelMap = new HashMap<>();
    private KafkaConsumer<String, String> exclusiveConsumer;
    /**
     * 广播
     */
    private ExecutorService standalonePool;
    private Map<String, List<EventModel>> standaloneModelMap = new HashMap<>();
    private KafkaConsumer<String, String> standaloneConsumer;

    /**
     * 异步发送消息
     */
    @Override
    public void sendAsync(String topic, Object target, EventTypeEnum eventType) {
        topic = this.getTopic(topic, eventType);
        ObjectMapper mapper = new ObjectMapper();
        try {
            String message;
            if (target instanceof String) {
                message = (String) target;
            } else {
                message = mapper.writeValueAsString(target);
            }
            ProducerRecord<String, String> record = new ProducerRecord<>(topic, message);
            producer.send(record, (data, e) -> {
                if (e != null) {
                    logger.error("fail send message ", e);
                }
            });
        } catch (JsonProcessingException e) {
            logger.error("fail writeValueAsString ", e);
        }
    }

    /**
     * 同步发送消息
     */
    @Override
    public boolean sendSync(String topic, Object target, EventTypeEnum eventType) {
        topic = this.getTopic(topic, eventType);
        ObjectMapper mapper = new ObjectMapper();
        try {
            String message;
            if (target instanceof String) {
                message = (String) target;
            } else {
                message = mapper.writeValueAsString(target);
            }
            ProducerRecord<String, String> record = new ProducerRecord<>(topic, message);
            producer.send(record).get();
            return true;
        } catch (JsonProcessingException e) {
            logger.error("fail writeValueAsString ", e);
            return false;
        } catch (InterruptedException | ExecutionException e) {
            logger.error("false sendAsync message ", e);
            return false;
        }
    }

    @Override
    public void run(ApplicationArguments args) throws UnknownHostException {
        //spring启动后初始化kafka
        this.init();
    }

    /**
     * 初始化生产者和消费者
     */
    private void init() throws UnknownHostException {

        Properties p = KafkaConfig.INSTANCE.getProducerProperties();
        producer = new KafkaProducer<>(p);

        //本机服务名称
        String appName = BootstrapConfig.get("spring.application.name");

        p = KafkaConfig.INSTANCE.getConsumerProperties();
        //消费者group.id格式：当前服务名字+“exclusive-group”
        p.put("group.id", appName + ".exclusive-group");
        exclusiveConsumer = new KafkaConsumer<>(p);

        //服务名+ip+port作为group.id，确保当前机器的group.id是惟一且不变的
        String ip = InetAddress.getLocalHost().getHostAddress();
        String port = BootstrapConfig.get("server.port");
        p.put("group.id", appName + "." + ip + "." + port);
        standaloneConsumer = new KafkaConsumer<>(p);

        this.createThreadPool();
        this.initConsumers();
        logger.info("success init kafka");
    }

    /**
     * 初始化监听器
     */
    private void createThreadPool() {
        Map<String, EventModel> modelMap = SpringContextHolder.getBeanOfType(EventModel.class);

        if (!CollectionUtils.isEmpty(modelMap)) {
            Collection<EventModel> handlers = modelMap.values();

            long nullTopics = handlers.stream().filter(h -> StringUtils.isEmpty(h.getEventType())).count();
            if (nullTopics != 0) {
                throw new RuntimeException("All EventModel must have a eventType");
            }

            List<EventModel> standalones = handlers.stream()
                    .filter(h -> h.getEventType() == EventTypeEnum.EXCLUSIVE)
                    .collect(Collectors.toList());
            List<EventModel> broadcasts = handlers.stream()
                    .filter(h -> h.getEventType() == EventTypeEnum.STAND_ALONE)
                    .collect(Collectors.toList());

            //创建线程池，管理所有的消费者
            if (0 != standalones.size()) {
                for (EventModel standAlone : standalones) {
                    String topic = this.getTopic(standAlone.getTopic(), EventTypeEnum.EXCLUSIVE);
                    exclusiveModelMap.computeIfAbsent(topic, v -> new ArrayList<>());
                    exclusiveModelMap.get(topic).add(standAlone);
                }
                exclusivePool = Executors.newFixedThreadPool(standalones.size());
            }

            if (broadcasts.size() != 0) {
                for (EventModel standAlone : broadcasts) {
                    String topic = this.getTopic(standAlone.getTopic(), EventTypeEnum.STAND_ALONE);
                    standaloneModelMap.computeIfAbsent(topic, v -> new ArrayList<>());
                    standaloneModelMap.get(topic).add(standAlone);
                }
                standalonePool = Executors.newFixedThreadPool(broadcasts.size());
            }
        }
    }


    /**
     * 启动监听器
     */
    private void initConsumers() {
        //单播消息
        intConsumer(exclusiveModelMap, exclusiveConsumer, exclusivePool);
        //广播消息
        intConsumer(standaloneModelMap, standaloneConsumer, standalonePool);
    }

    /**
     * 启动单个监听器
     */
    private void intConsumer(Map<String, List<EventModel>> modelMap,
                             KafkaConsumer<String, String> consumer,
                             ExecutorService executorService) {
        if (modelMap.size() != 0) {
            new Thread(() -> {
                consumer.subscribe(modelMap.keySet());

                while (true) {

                    try {
                        ConsumerRecords<String, String> records = consumer.poll(3000);
                        for (ConsumerRecord<String, String> record : records) {
                            executorService.execute(() -> this.dispatchRecord(modelMap, record));
                        }
                        consumer.commitSync();
                    } catch (Exception e) {
                        try {
                            Thread.sleep(200);
                        } catch (InterruptedException ignored) {
                        }
                    }

                }
            }).start();
        }
    }

    /**
     * 根据将消息分发到不同的model上
     */
    private void dispatchRecord(Map<String, List<EventModel>> modelMap, ConsumerRecord<String, String> record) {
        String topic = record.topic();
        List<EventModel> models = modelMap.get(topic);
        for (EventModel model : models) {
            Class clz = this.getActualType(model);
            if (clz == String.class) {
                model.handle(record.value(), record);
            } else {
                model.handle(this.convertValue(record.value(), clz), record);
            }
        }
    }

    private <T> T convertValue(String value, Class<T> clz) {
        try {
            return new ObjectMapper().readValue(value, clz);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取EventModel实现类T的真实类型
     */
    private <T> Class<T> getActualType(EventModel<? extends T> eventModel) {
        Type[] interfaces = eventModel.getClass().getGenericInterfaces();
        for (Type type : interfaces) {
            ParameterizedType t = (ParameterizedType) type;
            for (Type actual : t.getActualTypeArguments()) {
                //T是泛型的
                if (actual instanceof ParameterizedType) {
                    return (Class<T>) ((ParameterizedType) actual).getRawType();
                } else {
                    //T是非泛型的
                    return (Class<T>) actual;
                }
            }
        }
        throw new RuntimeException("呵呵呵呵呵你妹啊出错了");
    }

    private String getTopic(String topic, EventTypeEnum typeEnum) {
        if (typeEnum == EventTypeEnum.EXCLUSIVE) {
            return "exclusive." + topic;
        }

        return "standalone." + topic;
    }

}
