package com.demo.demo.business.demo.controller.admin;

import com.demo.demo.common.kafka.impl.BroadcastTestModel;
import com.demo.demo.common.web.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Created by liuyang on 2018/11/9
 */
@RestController
@RequestMapping(value = "admin/demo")
public class AdminDemoController {

    @Autowired
    private BroadcastTestModel broadcastmodel;

    @PostMapping("kafka/send")
    public Result testKafka(@RequestBody Map<String, String> map) {
        String message = map.get("message");
        broadcastmodel.sendAsync(message);
        return Result.success("成功发送消息 : " + message);
    }
}
