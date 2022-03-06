package com.sajur.msrabbitmqservice.listener;

import com.sajur.msrabbitmqservice.config.properties.RabbitMQProperties;
import com.sajur.msrabbitmqservice.model.User;
import com.sajur.msrabbitmqservice.repository.UserRepository;
import org.springframework.amqp.rabbit.connection.Connection;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class UserConsumer extends AbstractConsumer<User> {

    private final UserRepository userRepository;

    /**
     * Constructs a new instance and records its association to the passed-in channel.
     *
     */
    public UserConsumer(Connection connection
            , RabbitMQProperties properties
            , UserRepository userRepository) throws IOException {
        super(connection, properties.getQueue().getUserRegister());
        this.userRepository = userRepository;
    }

    @Override
    void process(User user) throws Exception {
        userRepository.save(user);
    }
}
