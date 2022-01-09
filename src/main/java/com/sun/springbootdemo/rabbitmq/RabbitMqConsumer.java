package com.sun.springbootdemo.rabbitmq;

import com.rabbitmq.client.Channel;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Argument;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;


/**
 * <p>  </p>
 *
 * @author Sundz
 * @date 2022/1/2 19:35
 */
@Component
@Log4j2
public class RabbitMqConsumer {

    @RabbitListener(ackMode = "MANUAL", bindings = {@QueueBinding(value = @Queue(name = "direct_queue", durable = "true", exclusive = "false",
            arguments = @Argument(name = "x-max-priority", value = "50", type = "java.lang.Long")),
            exchange = @Exchange(name = "direct_exchange", durable = Exchange.TRUE, type = ExchangeTypes.DIRECT), key = {"route_key"})})
    public void listenDirectQueue(Channel channel, Message message, @Headers Map<String, Object> headers) throws Exception {
        try {
            //int a = 1 / 0;
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            System.out.println("收到得消息<listenDirectQueue>:" + new String(message.getBody(), StandardCharsets.UTF_8));
        } catch (IOException e) {
            log.info("<------------------------------手动ack失败<listenDirectQueue>------------------------------>");
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
        }
    }

    /**
     * @field 交换机备份转发来的消息
     */
    @RabbitListener(queues = RabbitMqConfiguration.BACKUP_QUEUE, ackMode = "MANUAL")
    public void listnerBackupQueue(Channel channel, Message message) throws Exception {
        try {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            System.out.println("收到得消息<listnerBackupQueue>:" + new String(message.getBody(), StandardCharsets.UTF_8));
        } catch (Exception e) {
            log.info("<------------------------------手动ack失败<listnerBackupQueue>------------------------------>");
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
        }
    }

    /**
     * @field 延迟队列
     */
    @RabbitListener(queues = RabbitMqConfiguration.DEADED_LETTER_QUEUE, ackMode = "MANUAL")
    public void listnerDeadedQueue(Channel channel, Message message) throws Exception {
        try {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            System.out.println("收到得消息<listnerDeadedQueue>:" + new String(message.getBody(), StandardCharsets.UTF_8));
        } catch (Exception e) {
            log.info("<------------------------------手动ack失败<listnerDeadedQueue>------------------------------>");
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
        }
    }


}
