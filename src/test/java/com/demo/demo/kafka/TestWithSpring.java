package com.demo.demo.kafka;

import com.demo.demo.Application;
import com.demo.demo.common.kafka.impl.BroadcastTestModel;
import com.demo.demo.common.kafka.impl.StandaloneTestModel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by liuyang on 2018/11/9
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestWithSpring {

    @Autowired
    private BroadcastTestModel broadcastTestModel;
    @Autowired
    private StandaloneTestModel standaloneTestModel;

    @Test
    public void test() throws InterruptedException {
        Thread.sleep(5000);

        broadcastTestModel.sendAsync("this is the message ...........");

        Thread.sleep(5000);
    }

    @Test
    public void test2() throws InterruptedException {
        Thread.sleep(5000);

        Map<String, Integer> map = new HashMap<>();
        map.put("1", 1);
        map.put("2", 2);
        map.put("3", 3);
        standaloneTestModel.sendAsync(map);

        Thread.sleep(10000);
    }
}
