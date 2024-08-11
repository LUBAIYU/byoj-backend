package com.by.judge.consumer;

import com.by.common.constant.QuestionConstants;
import com.by.judge.service.JudgeService;
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

import javax.annotation.Resource;

/**
 * 消息消费者，负责消费题目提交信息
 *
 * @author lzh
 */
@Component
@Slf4j
public class MessageConsumer {

    @Resource
    private JudgeService judgeService;

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
            // 执行判题
            judgeService.doJudge(questionSubmitId);
            // 确认消息
            channel.basicAck(deliveryTag, false);
        } catch (Exception e) {
            // 拒绝消息
            channel.basicNack(deliveryTag, false, false);
        }
    }
}
