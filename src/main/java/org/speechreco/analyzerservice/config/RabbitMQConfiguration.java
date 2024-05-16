package org.speechreco.analyzerservice.config;


import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class RabbitMQConfiguration {
    public static final String DIRECT_EXCHANGE_NAME = "amq.direct";
    public static final String MESSAGE_QUEUE = "stt-queue";
    public static final String RESPONSE_QUEUE = "stt-response";

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(DIRECT_EXCHANGE_NAME);
    }

    @Bean
    @Primary
    public Queue messagesQueue() {
        return new Queue(MESSAGE_QUEUE);
    }

    @Bean
    public Queue responseQueue() {
        return new Queue(RESPONSE_QUEUE);
    }

    @Bean
    public Binding messageQueueBinding(Queue messagesQueue, DirectExchange exchange) {
        return BindingBuilder.bind(messagesQueue).to(exchange).with(MESSAGE_QUEUE);
    }

    @Bean
    public Binding messageResponseQueueBinding(DirectExchange exchange) {
        return BindingBuilder.bind(responseQueue()).to(exchange).with(RESPONSE_QUEUE);
    }
}