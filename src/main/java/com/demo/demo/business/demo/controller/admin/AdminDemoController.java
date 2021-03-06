package com.demo.demo.business.demo.controller.admin;

import cn.hutool.core.map.MapBuilder;
import cn.hutool.core.map.MapUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.demo.demo.common.advice.BusinessException;
import com.demo.demo.common.kafka.impl.BroadcastTestModel;
import com.demo.demo.common.web.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;
import java.io.IOException;
import java.util.Arrays;
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

    /**
     * 测试GlobalException
     */
    @GetMapping("advice/test")
    public Result testAdvice(@ModelAttribute("goal") String goal,
                             @RequestParam(value = "doThrow", defaultValue = "false") Boolean doThrow) {
        if (doThrow) {
            throw new BusinessException(goal);
        }
        return Result.success(String.format("the message is : %s", goal));
    }

    @GetMapping("test/excel")
    public Result testExcel(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ExcelWriter writer = new ExcelWriter();
        writer.addHeaderAlias("name", "姓名");
        writer.addHeaderAlias("age", "年龄");

        writer.renameSheet("用户信息表");

        //head
        writer.writeHeadRow(Arrays.asList("姓名", "年龄"));

        Map<String, String> data1 = MapUtil.builder("name", "ly").put("age", "20").build();
        Map<String, String> data2 = MapUtil.builder("name", "lxh").put("age", "18").build();

        writer.writeRow(data1, false);
        writer.writeRow(data2, false);

        ServletOutputStream outputStream = response.getOutputStream();
        writer.flush(outputStream);
        return null;
    }


}
