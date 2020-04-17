package com.ylz.ai.mobile.controller;

import com.ylz.ai.mobile.rabbitmq.sender.QueueSender;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Description
 * @Author haifeng.lv
 * @Date 2020/4/17 10:29
 */
@RestController
public class TestController {
    @Resource(name = "aiQueueSender")
    private QueueSender queueSender;

    @RequestMapping("hello")
    public String hello() {
        queueSender.sendMessage("hello");
        return "hello";
    }
}
