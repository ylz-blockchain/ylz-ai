package com.ylz.ai.mobile.rabbitmq.consumer;

import com.rabbitmq.client.Channel;
import com.ylz.ai.common.util.JsonUtils;
import com.ylz.ai.mobile.rabbitmq.RabbitConfig;
import com.ylz.ai.mobile.rabbitmq.vo.ImageAiResponse;
import com.ylz.ai.mobile.service.IImageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private IImageService imageService;

    @RabbitHandler
    @RabbitListener(queues = RabbitConfig.YLZ_AI_BACK)
    public void lisMsg(byte[] msg, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) {
        try {
            log.info("接收消息：{}", new String(msg));
            ImageAiResponse imageAiResponse = JsonUtils.parseObject(new String(msg), ImageAiResponse.class);
            // 修改识别访问地址
            imageService.alterImageRVAById(imageAiResponse.getId(), imageAiResponse.getRecognitionVisitAddress(), imageAiResponse.getStatus());
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
