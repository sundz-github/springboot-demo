package com.sun.springbootdemo.rabbitmq;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

/**
 * <p>  </p>
 *
 * @author Sundz
 * @date 2022/1/2 22:43
 */
@Component
public class RabitMqProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;


    /**
     * confirm只负责保证到exchange，return保证到queue
     *
     * @param msg
     * @return void
     */
    public void send(String msg) throws Exception {
        // 发布确认模式  只负责将消息发送到exchange
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            if (ack) {
                System.out.println("<----------------------------消息发布成功---------------------------->");
            } else {
                System.out.println("消息发布失败:" + cause);
            }
        });
        // 返回模式  负责将exchange到queue
        rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> {
            System.out.println("返回消息:" + new String(message.getBody(), StandardCharsets.UTF_8) + ",replyCode:" + replyCode + ",replyText:" + replyText);
        });

        /*String exchange = "direct_exchange";
        String routeKey = "route_key";*/
        String exchange = "topic_exchange";
        String routeKey = "route_key.topic";
        //rabbitTemplate.convertAndSend(exchange, routeKey, msg);
        MessageProperties messageProperties = new MessageProperties();
        String uuid = UUID.randomUUID().toString();
        messageProperties.setHeader("uuid", uuid);
        messageProperties.setContentType(MessageProperties.CONTENT_TYPE_TEXT_PLAIN);
        messageProperties.setMessageId(uuid);
        Message sendMsg = new Message(msg.getBytes(StandardCharsets.UTF_8), messageProperties);
        rabbitTemplate.send(exchange, routeKey, sendMsg);
    }

}
