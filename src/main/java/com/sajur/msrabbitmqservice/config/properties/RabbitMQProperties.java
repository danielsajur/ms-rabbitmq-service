package com.sajur.msrabbitmqservice.config.properties;

import com.sajur.msrabbitmqservice.config.Queues;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "spring.rabbitmq")
public class RabbitMQProperties {

    private String host;
    private String port;
    private String exchange;
    private String password;
    private String username;
    private String virtualHost;
    private Queues queue;

}
