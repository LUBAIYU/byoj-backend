package com.by.judge.consumer;

import com.by.common.constant.QuestionConstants;
import com.rabbitmq.client.Channel;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

/**
 * 消息消费者，负责消费题目提交信息
 *
 * @author lzh
 */
@Component
@Slf4j
public class MessageConsumer {

    @SneakyThrows
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = QuestionConstants.QUEUE_NAME),
            exchange = @Exchange(name = QuestionConstants.EXCHANGE_NAME),
            key = QuestionConstants.ROUTING_KEY
    ), ackMode = "MANUAL")
    public void consumeMessage(String message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) {
        log.info("rabbitmq receive message = {}", message);
        long questionSubmitId = Long.parseLong(message);
        try {
            //TODO 调用判题服务
        } catch (Exception e) {
            // 拒绝消息
            channel.basicNack(deliveryTag, false, false);
        }
    }
}
