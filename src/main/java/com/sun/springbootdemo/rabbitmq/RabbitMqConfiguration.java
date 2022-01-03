package com.sun.springbootdemo.rabbitmq;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>  </p>
 *
 * @author Sundz
 * @date 2022/1/2 22:07
 */
@Configuration
public class RabbitMqConfiguration {

    /*@Autowired
    @Lazy
    private RabbitTemplate rabbitTemplate;*/


    /*@Bean
    public RabbitTemplate rabbitTemplate() {
        // 发布确认模式
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            if (ack) {
                System.out.println("消息发布成功:" + new String(correlationData.getReturnedMessage().getBody(), StandardCharsets.UTF_8));
            } else {
                System.out.println("消息发布失败:" + cause);
            }
        });
        // 返回模式
        rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> {
            System.out.println("返回消息:" + new String(message.getBody(), StandardCharsets.UTF_8) + ",replyCode:" + replyCode + ",replyText:" + replyText);
        });

        return rabbitTemplate;
    }
*/

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        //factory.setMessageConverter(new Jackson2JsonMessageConverter());
        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        return factory;
    }
}
