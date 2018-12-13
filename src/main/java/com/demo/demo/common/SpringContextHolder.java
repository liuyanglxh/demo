package com.demo.demo.common;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by liuyang on 2018/11/9
 */
@Component
public class SpringContextHolder implements ApplicationContextAware {

    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    public static <T> T getBean(Class<T> clz) {
        return context.getBean(clz);
    }

    public static Object getBean(String name) {
        return context.getBean(name);
    }

    public static <T> Map<String, T> getBeanOfType(Class<T> clz) {
        return context.getBeansOfType(clz);
    }


}
