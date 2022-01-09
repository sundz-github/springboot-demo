package com.sun.springbootdemo.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.retry.MessageRecoverer;
import org.springframework.amqp.rabbit.retry.RepublishMessageRecoverer;
import org.springframework.beans.factory.annotation.Qualifier;
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

    public static final String NORMAL_EXCHANGE = "normal_exchange";

    public static final String DEADED_LETTER_EXCHANGE = "deaded_letter_exchange";

    /**
     * @field 备份交换机
     */
    public static final String BACKUP_EXCHANGE = "backup_exchange";

    public static final String NORMAL_QUEUE = "normal_queue";

    /**
     * @field 备份交换机
     */
    public static final String BACKUP_QUEUE = "backup_queue";

    public static final String DEADED_LETTER_QUEUE = "deaded_letter_queue";

    public static final String NORMAL_ROUTE_KEY = "normal_route_key";

    public static final String DEADED_LETTER_ROUTE_KEY = "deaded_letter_route_key";

    public static final String BACKUP_ROUTE_KEY = "backup_route_key";


    /*@Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.set
        //factory.setMessageConverter(new Jackson2JsonMessageConverter());
        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        return factory;
    }*/
    // 重试后的消息进行转发
    @Bean
    public MessageRecoverer messageConverter(RabbitTemplate rabbitTemplate) {
        return new RepublishMessageRecoverer(rabbitTemplate, "direct_exchange", "route_key");
    }

    /**
     * @field 与死信交换机进行绑定
     */
    @Bean
    public Queue normalQueue() {
        return QueueBuilder.durable(NORMAL_QUEUE).deadLetterExchange(DEADED_LETTER_EXCHANGE).deadLetterRoutingKey(DEADED_LETTER_ROUTE_KEY).build();
    }

    @Bean
    public Queue deadedQueue() {
        return QueueBuilder.durable(DEADED_LETTER_QUEUE).build();
    }

    @Bean
    public Queue backupQueue() {
        return QueueBuilder.durable(BACKUP_QUEUE).build();
    }

    /**
     * @field 设置备份交换机
     */
    @Bean
    public Exchange normalExchange() {
        return ExchangeBuilder.directExchange(NORMAL_EXCHANGE).alternate(BACKUP_EXCHANGE).durable(true).build();
    }

    @Bean
    public Exchange backupExchange() {
        return ExchangeBuilder.fanoutExchange(BACKUP_EXCHANGE).durable(true).build();
    }

    @Bean
    public Exchange deadedExchange() {
        return ExchangeBuilder.directExchange(DEADED_LETTER_EXCHANGE).durable(true).build();
    }

    @Bean
    public Binding normalBinding(@Qualifier("normalQueue") Queue queue, @Qualifier("normalExchange") Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(NORMAL_ROUTE_KEY).noargs();
    }

    /**
     * @field 建议类型设置为 fanout 类型；也可以设置direct或者topic，
     * 但是需要注意的是：消息被重新发送到备份交换器时的路由键和从生产者发出的路由键是一样的，所以备份交换器和备份队列的绑定键要匹配上消息发出时的路由键；
     * 其实我们设置备份交换器的目的就是为了在消息的路由键没有匹配上时来接收这类“无家可归”的消息，所以没必要将类型设置为direct或者topic。
     */
    @Bean
    public Binding backupBinding(@Qualifier("backupQueue") Queue queue, @Qualifier("backupExchange") Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("").noargs();
    }

    @Bean
    public Binding deadedBinding(@Qualifier("deadedQueue") Queue queue, @Qualifier("deadedExchange") Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(DEADED_LETTER_ROUTE_KEY).noargs();
    }


}
