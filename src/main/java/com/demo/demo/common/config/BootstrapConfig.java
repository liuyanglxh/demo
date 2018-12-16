package com.demo.demo.common.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by liuyang on 2018/11/8
 */
public class BootstrapConfig {

    private static final Logger logger = LoggerFactory.getLogger(BootstrapConfig.class);
    private static final Properties bootstrap = new Properties();

    static {
        try {
            bootstrap.load(new FileInputStream("config/bootstrap.properties"));
        } catch (IOException e) {
            logger.error("error load file config/bootstrap.properties", e);
        }
    }

    public static String get(String key){
        return bootstrap.getProperty(key);
    }

}
