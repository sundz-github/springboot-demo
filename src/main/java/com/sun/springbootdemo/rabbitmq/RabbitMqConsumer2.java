package com.sun.springbootdemo.rabbitmq;

import com.rabbitmq.client.Channel;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Argument;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

/**
 * <p>  </p>
 *
 * @author Sundz
 * @date 2022/1/3 22:06
 */
@RabbitListener(ackMode = "MANUAL", bindings = {@QueueBinding(value = @Queue(name = "topic_queue", durable = "true", exclusive = "false",
        arguments = @Argument(name = "x-max-priority", value = "50", type = "java.lang.Long")),
        exchange = @Exchange(name = "topic_exchange", durable = Exchange.TRUE, type = ExchangeTypes.TOPIC), key = {"#.topic"})})
@Log4j2
@Component
public class RabbitMqConsumer2 {


    @RabbitHandler
    public void listen1(Channel channel, Message message, @Payload String payLoad) throws Exception {
        try {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            System.out.println("收到得消息<1>:" + payLoad);
        } catch (IOException e) {
            log.info("<------------------------------手动ack失败------------------------------>");
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
        }
    }

    @RabbitHandler
    public void listen2(Channel channel, Message message, @Payload Map<String, Object> payLoad) throws Exception {
        try {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            System.out.println("收到得消息<1>:" + payLoad);
        } catch (IOException e) {
            log.info("<------------------------------手动ack失败------------------------------>");
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
        }
    }
}
