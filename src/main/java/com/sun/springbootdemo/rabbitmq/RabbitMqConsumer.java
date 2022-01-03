package com.sun.springbootdemo.rabbitmq;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;


/**
 * <p>  </p>
 *
 * @author Sundz
 * @date 2022/1/2 19:35
 */
@Component
@Log4j2
public class RabbitMqConsumer {

    /*@RabbitListener(ackMode = "MANUAL", bindings = {@QueueBinding(value = @Queue(name = "direct_queue", durable = "true", exclusive = "false",
            arguments = @Argument(name = "x-max-priority", value = "50", type = "java.lang.Long")),
            exchange = @Exchange(name = "direct_exchange", durable = Exchange.TRUE, type = ExchangeTypes.DIRECT), key = {"route_key"})})
    public void listen1(Channel channel, Message message, @Headers Map<String, Object> headers) throws Exception {
        try {
            //int a = 1 / 0;
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            System.out.println("收到得消息<1>:" + new String(message.getBody(), StandardCharsets.UTF_8));
        } catch (IOException e) {
            log.info("<------------------------------手动ack失败------------------------------>");
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
        }
    }*/

    /*@RabbitListener(ackMode = "MANUAL", bindings = {@QueueBinding(value = @Queue(name = "direct_queue", durable = "true", exclusive = "false",
            arguments = @Argument(name = "x-max-priority", value = "50", type = "java.lang.Long")),
            exchange = @Exchange(name = "direct_exchange", durable = Exchange.TRUE, type = ExchangeTypes.DIRECT), key = {"route_key"})})
    public void listen2(Channel channel, Message message, @Headers Map<String, Object> headers) throws Exception {
        try {

            //int a = 1 / 0;  //不能写在手动ack后面  否则就会报  unknown delivery tag 1  错误
            System.out.println("收到得消息<2>:" + new String(message.getBody(), StandardCharsets.UTF_8) + "\nheaders:" + headers);
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
        }
    }*/

    /*@RabbitListener(ackMode = "MANUAL", bindings = {@QueueBinding(value = @Queue(name = "topic_queue", durable = "true", exclusive = "false",
            arguments = @Argument(name = "x-max-priority", value = "50", type = "java.lang.Long")),
            exchange = @Exchange(name = "topic_exchange", durable = Exchange.TRUE, type = ExchangeTypes.TOPIC), key = {"#.topic"})})
    @RabbitHandler
    public void listen3(Channel channel, Message message, @Payload Person payLoad) throws Exceptio                                                                                                                              n {
        try {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            System.out.println("收到得消息<1>:" + payLoad);
        } catch (IOException e) {
            log.info("<------------------------------手动ack失败------------------------------>");
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
        }
    }*/

    /*@RabbitListener(ackMode = "MANUAL", bindings = {@QueueBinding(value = @Queue(name = "topic_queue", durable = "true", exclusive = "false",
            arguments = @Argument(name = "x-max-priority", value = "50", type = "java.lang.Long")),
            exchange = @Exchange(name = "topic_exchange", durable = Exchange.TRUE, type = ExchangeTypes.TOPIC), key = {"#.topic"})})
    @RabbitHandler
    public void listen4(Channel channel, Message message, @Payload Map<String, Object> payLoad) throws Exception {
        try {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            System.out.println("收到得消息<1>:" + payLoad);
        } catch (IOException e) {
            log.info("<------------------------------手动ack失败------------------------------>");
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
        }
    }*/

}
