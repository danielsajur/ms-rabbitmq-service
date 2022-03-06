package com.sajur.msrabbitmqservice.config;

import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import com.rabbitmq.client.Channel;
import com.sajur.msrabbitmqservice.config.properties.RabbitMQProperties;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.Connection;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.Map;

@Configuration
public class RabbitMQConfig {

    public static final Boolean ACK = false;
    private static final boolean DURABLE = true;
    private static final boolean EXCLUSIVE = false;
    private static final boolean AUTO_DELETE = false;

    private final RabbitMQProperties queueProperties;

    public RabbitMQConfig(RabbitMQProperties queueProperties) {
        this.queueProperties = queueProperties;
    }

    @Bean
    public ConnectionFactory connectionFactory(){
        return getConnectionFactory();
    }

    @Bean
    public ConnectionFactory producerConnectionFactory(){
        return getConnectionFactory();
    }

    private CachingConnectionFactory getConnectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost(queueProperties.getHost());
        connectionFactory.setUsername(queueProperties.getUsername());
        connectionFactory.setPassword(queueProperties.getPassword());
        connectionFactory.setVirtualHost(queueProperties.getVirtualHost());
        return connectionFactory;
    }

    @Bean
    public Connection connection(ConnectionFactory connectionFactory) {
        return connectionFactory.createConnection();
    }

    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public JsonMapper objectMapper(){
        return JsonMapper.builder()
                .addModule(new ParameterNamesModule())
                .addModule(new Jdk8Module())
                .addModule(new JavaTimeModule())
                .build();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory producerConnectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(producerConnectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public Exchange exchange() {
        return ExchangeBuilder
                .directExchange(queueProperties.getExchange())
                .durable(DURABLE)
                .build();
    }

    @Bean
    public Channel channel(ConnectionFactory producerConnectionFactory, Exchange exchange) throws Exception {
        final Channel channel = producerConnectionFactory.createConnection().createChannel(true);

        channel.exchangeDeclare(exchange.getName(), exchange.getType(), DURABLE);

        queueProperties.getQueue().getAllQueues().forEach(queueName ->{
            try {
                final Map<String, Object> header = queueProperties.getQueue().getHeader(queueName);
                channel.queueDeclare(queueName, DURABLE, EXCLUSIVE, AUTO_DELETE, header);
                channel.queueBind(queueName, exchange.getName(), queueName, header);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        return channel;
    }

}
