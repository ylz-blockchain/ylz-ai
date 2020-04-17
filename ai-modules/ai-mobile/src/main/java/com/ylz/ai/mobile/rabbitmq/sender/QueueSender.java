package com.ylz.ai.mobile.rabbitmq.sender;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description 队列发送者
 * @Author haifeng.lv
 * @Date 2020/4/17 10:10
 */
public abstract class QueueSender {
    @Autowired
    protected RabbitTemplate rabbitTemplate;

    public abstract void sendMessage(String message);
}
