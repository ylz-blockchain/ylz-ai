package com.ylz.ai.mobile.rabbitmq.sender;

import com.ylz.ai.mobile.rabbitmq.RabbitConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @Description ai 队列发送者
 * @Author haifeng.lv
 * @Date 2020/4/17 10:17
 */
@Service
@Slf4j
public class AiQueueSender extends QueueSender {
    @Override
    public void sendMessage(String message) {
        log.info("发送消息内容: " + message);
        rabbitTemplate.convertAndSend(RabbitConfig.YLZ_AI_NOTICE, message.getBytes());
    }
}
