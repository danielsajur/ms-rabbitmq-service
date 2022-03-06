package com.sajur.msrabbitmqservice.config;

import com.sajur.msrabbitmqservice.config.properties.RabbitMQProperties;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQDirectConfig {

    private final RabbitMQProperties properties;

    public RabbitMQDirectConfig(RabbitMQProperties properties) {
        this.properties = properties;
    }

    @Bean
    Queue userErrorQueue() {
        return new Queue(properties.getQueue().getUserError(), false);
    }

    @Bean
    Queue productErrorQueue() {
        return new Queue(properties.getQueue().getProductError(), false);
    }

    @Bean
    DirectExchange errorExchange() {
        return new DirectExchange("error-exchange");
    }

    @Bean
    Binding userErrorBinding(Queue userErrorQueue, DirectExchange errorExchange) {
        return BindingBuilder.bind(userErrorQueue).to(errorExchange).with(properties.getQueue().getUserError());
    }

    @Bean
    Binding productErrorBinding(Queue productErrorQueue, DirectExchange errorExchange) {
        return BindingBuilder.bind(productErrorQueue).to(errorExchange).with(properties.getQueue().getProductError());
    }

}
