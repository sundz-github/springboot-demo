package com.sun.springbootdemo.rabbitmq;

import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <p>  </p>
 *
 * @author Sundz
 * @date 2022/1/2 22:43
 */
@Component
@Log4j2
public class RabitMqProducer {

    @Autowired

    private RabbitTemplate rabbitTemplate;

    private AtomicInteger tryCount = new AtomicInteger(0);


    /**
     * confirm只负责保证到exchange，return保证到queue
     *
     * @param msg
     * @return void
     */
    public void send(String msg, Long expSecond) {
        // 发布确认模式  只负责将消息发送到exchange
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            if (ack) {
                log.info("<----------------------------消息发布成功---------------------------->");
            } else {
                log.error("消息发布失败:" + cause);
            }
        });
        /*rabbitTemplate.setMandatory(true);
        // 返回模式  负责将exchange到queue
        rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> {
            log.error("返回消息:" + new String(message.getBody(), StandardCharsets.UTF_8) + ",replyCode:" + replyCode + ",replyText:" + replyText);
        });*/

        String exchange = RabbitMqConfiguration.NORMAL_EXCHANGE;
        String routeKey = RabbitMqConfiguration.NORMAL_ROUTE_KEY + "1";
        //rabbitTemplate.convertAndSend(exchange, routeKey, msg);
        MessageProperties messageProperties = new MessageProperties();
        String uuid = UUID.randomUUID().toString();
        messageProperties.setHeader("uuid", uuid);
        messageProperties.setContentType(MessageProperties.CONTENT_TYPE_TEXT_PLAIN);
        messageProperties.setMessageId(uuid);
        // 设置过期时间
        //messageProperties.setExpiration(String.valueOf(expSecond));
        // 消息持久化
        messageProperties.setDeliveryMode(MessageDeliveryMode.PERSISTENT);
        Message sendMsg = new Message(msg.getBytes(StandardCharsets.UTF_8), messageProperties);
        CorrelationData data = new CorrelationData(uuid);
        rabbitTemplate.send(exchange, routeKey, sendMsg, data);
    }

}
