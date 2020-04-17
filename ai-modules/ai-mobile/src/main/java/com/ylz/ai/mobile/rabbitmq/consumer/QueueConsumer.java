package com.ylz.ai.mobile.rabbitmq.consumer;

import com.rabbitmq.client.Channel;
import com.ylz.ai.mobile.rabbitmq.RabbitConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @Description 监听队列
 * @Author haifeng.lv
 * @Date 2020/4/16 17:56
 */
@Component
@Slf4j
public class QueueConsumer {

    @RabbitHandler
    @RabbitListener(queues = RabbitConfig.YLZ_AI_BACK)
    public void lisMsg(byte[] msg, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) {
        try {
            log.info("接收消息：{}", new String(msg));
            channel.basicAck(tag, false);
        }catch (Exception e){
            e.printStackTrace();
            log.error(e.getMessage());
            try {
                channel.basicNack(tag, false, false);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

}
