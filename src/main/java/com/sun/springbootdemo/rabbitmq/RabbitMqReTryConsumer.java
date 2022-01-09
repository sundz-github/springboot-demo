package com.sun.springbootdemo.rabbitmq;

import com.rabbitmq.client.Channel;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.Argument;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Date;

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
public class RabbitMqReTryConsumer {

    @Value(value = "${spring.rabbitmq.template.retry.max-attempts}")
    private Integer maxTryAttempts;


    @RabbitHandler
    public void listen1(Channel channel, Message message, @Payload String payLoad) throws Exception {

        try {
            //int a = 1 / 0;
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            System.out.println("收到得消息<RabbitMqReTryConsumer>:" + payLoad);
        } catch (Exception e) {
            log.info("<--------重试时间:" + DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss") + ",内容:" + payLoad + "-------->");
            throw new RuntimeException("重试异常");
        } finally {
            // 重试一定次数后需要将其删除
            String retryHeadKey = "TryAttempts";
            MessageProperties messageProperties = message.getMessageProperties();
            Integer tryAttempts = messageProperties.getHeader(retryHeadKey);
            if (null == tryAttempts) {
                messageProperties.setHeader(retryHeadKey, 1);
            } else {
                tryAttempts = messageProperties.getHeader(retryHeadKey);
                tryAttempts++;
                if (tryAttempts == maxTryAttempts) {
                    channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
                } else {
                    messageProperties.setHeader(retryHeadKey, tryAttempts);
                }
            }
        }
    }

   /* @RabbitHandler
    public void listen2(Channel channel, Message message, @Payload Map<String, Object> payLoad) throws Exception {
        try {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            System.out.println("收到得消息<1>:" + payLoad);
        } catch (IOException e) {
            log.info("<------------------------------手动ack失败------------------------------>");
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
        }
    }*/
}
