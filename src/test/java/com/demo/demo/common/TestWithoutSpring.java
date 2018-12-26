package com.demo.demo.common;

import cn.hutool.core.map.MapProxy;
import cn.hutool.core.map.MapUtil;
import cn.hutool.log.Log;
import cn.hutool.log.dialect.slf4j.Slf4jLog;
import cn.hutool.log.dialect.slf4j.Slf4jLogFactory;
import com.demo.demo.common.utils.common.CollectionUtil;
import com.demo.demo.java8.TestBean;
import org.junit.Test;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by liuyang on 2018/11/15
 */
public class TestWithoutSpring {

    @Test
    public void test20(){
    }

    @Test
    public void test19(){
        Map<String, String> map = MapUtil.builder("name", "ly").build();
        MapProxy proxy = MapProxy.create(map);
        String name = proxy.getStr("name");
        System.out.println(name);
    }

    @Test
    public void test18(){
        //测试MapUtil的getXxx方法
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> data = MapUtil.builder(map).put("name", "ly").put("age", "27").put("sex", true).build();
        System.out.println(MapUtil.getStr(data, "name"));
        System.out.println(MapUtil.getInt(data, "age"));
        System.out.println(MapUtil.getBool(data, "sex"));
        User user = new User();
        user.setAge(10);
        Map<String, User> userMap = MapUtil.builder("user", user).build();
        User usr = MapUtil.get(userMap, "user", User.class);
        System.out.println(usr.getAge());
    }

    @Test
    public void test17(){
        //测试MapUtil的getAny方法
        Map<String, String> map = MapUtil.builder("name", "ly").put("city", "cd").build();
        Map<String, String> newMap = MapUtil.getAny(map, "name");
        System.out.println(newMap);
    }

    @Test
    public void test16(){
        Map<String, String> map = MapUtil.builder("name", "jack")
                .put("hobby", "girl").build();
        System.out.println(map);
        System.out.println(map.getClass());
        Map<String, String> mp = new ConcurrentHashMap<>();
        Map<String, String> build = MapUtil.builder(mp).put("name", "ly").build();
        System.out.println(build);
        System.out.println(build.getClass());
    }

    @Test
    public void test15() {
        Map<String, String> map = new HashMap<>();
        map.put("name", "jack");
        CollectionUtil.forEach(map, (k, v) -> System.out.println(String.format("key -- %s; value -- %s", k, v)));
    }

    @Test
    public void test14() {
        List<String> list = Arrays.asList("1", "as", "3as", "asda");
        CollectionUtil.forEach(list, str -> System.out.println(str.length()));
    }

    @Test
    public void test13() {
        List<String> list = Arrays.asList("1", "as", "3as", "asda");
        Map<Integer, String> map = CollectionUtil.trans2Map(list, String::length);
        System.out.println(map);
    }

    @Test
    public void test1() {
        String str = "1,2,4,5,";
        String[] split = ",".split(str);
        for (String s : split) {
            System.out.println(s);
        }
    }

    @Test
    public void test2() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
        List<Integer> list2 = new ArrayList<>(list);
        list2.removeIf(i -> i >= 2);
        System.out.println(list);
        System.out.println(list2);
    }

    @Test
    public void test3() {
        List<Integer> lst1 = Arrays.asList(1, 2, 3, 4, 5);
        List<Integer> lst2 = Arrays.asList(1, 2, 3, 4, 5);
        System.out.println(lst1.equals(lst2));
    }

    @Test
    public void test4() {
        List<TestBean> beans = Arrays.asList(TestBean.of("jack", 12),
                TestBean.of("lucy", 18),
                TestBean.of("lily", 10),
                TestBean.of("jackson", 23));
        IntSummaryStatistics st = beans.stream().mapToInt(TestBean::getAge).summaryStatistics();
        int max = st.getMax();
    }

    /**
     * 测试map的putIfAbsent是否线程安全
     * 方法：
     * 50个线程，随机从0-9中选取一个数作为key，如果有这个key，则往value中增加一个元素，否则设置为List
     */
    @Test
    public void test5() throws InterruptedException {
        List<Integer> duplicates = new ArrayList<>();
        Random random = new Random();
        Map<Integer, List<Integer>> map = new ConcurrentHashMap<>();
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            threads.add(new Thread(() -> {
                int key = random.nextInt(10);
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                List<Integer> list = map.putIfAbsent(key, new ArrayList<>());
                if (!CollectionUtils.isEmpty(list)) {
                    duplicates.add(key);
                }
                map.get(key).add(1);
            }));
        }
        threads.forEach(Thread::start);

        Thread.sleep(8000);

        long count = map.values().stream().mapToLong(Collection::size).sum();
        System.out.println(count);
    }

    /**
     * 做一个数据缓冲区，需要实现：
     * 1.缓冲区数据足够大时，进行刷新
     * 2.最低每隔xx时间执行一次刷新
     * <p>
     * 实现思路：
     * 1.借鉴redis单线程原理，使用单线程池来接收数据请求
     * 2.新数据最多等待1秒就会刷新
     */
    private final ExecutorService acceptor = Executors.newSingleThreadExecutor();
    private Map<Integer, Integer> buffer = new HashMap<>();
    private int MAX_BUFFER_SIZE = 8;
    private Long lastFlushTime = System.currentTimeMillis();

    @Test
    public void test6() throws InterruptedException {
        //启动定时任务
        this.timeTask();
        Random random = new Random();
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            threads.add(new Thread(() -> {
                while (true) {
                    try {
                        Thread.sleep(random.nextInt(10000));
                    } catch (InterruptedException ignored) {

                    }
                    this.accept(random.nextInt(10));
                }
            }));
        }
        threads.forEach(Thread::start);
        Thread.sleep(20000);
    }

    private void accept(Integer id) {
        acceptor.execute(() -> {
            buffer.putIfAbsent(id, 0);
            int value = buffer.get(id);
            value++;
            buffer.put(id, value);
            if (buffer.size() >= MAX_BUFFER_SIZE) {
                this.flush();
            }
        });
    }

    private void flush() {
        Map<Integer, Integer> toFlush = buffer;
        buffer = new HashMap<>();
        System.out.println("执行刷新任务");
        System.out.println(toFlush);
        lastFlushTime = System.currentTimeMillis();
    }

    /**
     * 监控任务的定时器
     */
    private void timeTask() {
        new Thread(() -> {
            while (true) {
                if (System.currentTimeMillis() - lastFlushTime >= 1000) {
                    this.flush();
                }
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Test
    public void test7() {
        List<User> users = new ArrayList<>();
        Stream.of(1, 2, 4, 5, 6).forEach(i -> users.add(new User(i)));
        System.out.println(users.size());
    }

    class User {
        int age;

        @Override
        public String toString() {
            return "User{" +
                    "age=" + age +
                    '}';
        }

        public User() {
        }

        User(int age) {
            this.age = age;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }
    }

    @Test
    public void test8() {
        Function<User, Integer> getAge = User::getAge;
        List<User> users = Arrays.asList(new User(1), new User(2), new User(3), new User(7), new User(6));
        Optional<User> max = users.stream().max(Comparator.comparing(getAge));
        Optional<User> min = users.stream().min(Comparator.comparing(getAge));


    }

    @Test
    public void test9() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 1, 2, 3, 4, 5, 6, 7, 8);
        Map<Boolean, List<Integer>> map = list.stream().collect(Collectors.partitioningBy(i -> i % 2 == 0));
        List<Integer> even = map.get(true);
        List<Integer> odd = map.get(false);
        System.out.println(even);
        System.out.println(odd);
    }

    @Test
    public void test10() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 1, 2, 3, 4, 5, 6, 7, 8);
        Map<Integer, List<Integer>> nums = list.stream().collect(Collectors.groupingBy(i -> i));
        nums.forEach((i, lst) -> System.out.println(i + ":" + lst));
    }

    /**
     * 测试groupingBy
     */
    @Test
    public void test11() {
        List<User> users = Arrays.asList(new User(1), new User(2), new User(3), new User(7), new User(6));
        Map<Integer, List<User>> map = users.stream().collect(Collectors.groupingBy(User::getAge));
        map.forEach((k, v) -> System.out.println(k + ":" + v));
    }

    /**
     * 测试joining
     */
    @Test
    public void test12() {
        List<User> users = Arrays.asList(new User(1), new User(2), new User(3), new User(7), new User(6));
        String str = users.stream().map(user -> user.getAge().toString()).collect(Collectors.joining(":", "{", "}"));
        System.out.println(str);
    }


}









