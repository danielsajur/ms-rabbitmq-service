package com.sajur.msrabbitmqservice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sajur.msrabbitmqservice.config.properties.RabbitMQProperties;
import com.sajur.msrabbitmqservice.service.MessagingService;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class MessagingServiceImpl implements MessagingService {

    private final RabbitTemplate rabbitTemplate;

    private final RabbitMQProperties rabbitMQProperties;

    private final ObjectMapper objectMapper;

    public MessagingServiceImpl(RabbitTemplate rabbitTemplate
            , RabbitMQProperties rabbitMQProperties
            , ObjectMapper objectMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.rabbitMQProperties = rabbitMQProperties;
        this.objectMapper = objectMapper;
    }

    @Override
    public <T> void publish(final String queue, T message) throws JsonProcessingException {

        if(Objects.isNull(message)){
            throw new IllegalArgumentException("Message cannot be null");
        }

        byte[] messaAsBytes = objectMapper.writeValueAsBytes(message);
        Message newMessage = new Message(messaAsBytes);

        rabbitTemplate.send(rabbitMQProperties.getExchange(), queue, newMessage);
    }

}
