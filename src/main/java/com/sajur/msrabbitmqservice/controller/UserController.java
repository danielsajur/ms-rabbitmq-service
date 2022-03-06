package com.sajur.msrabbitmqservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sajur.msrabbitmqservice.config.properties.RabbitMQProperties;
import com.sajur.msrabbitmqservice.model.User;
import com.sajur.msrabbitmqservice.repository.UserRepository;
import com.sajur.msrabbitmqservice.service.MessagingService;
import com.sajur.msrabbitmqservice.service.RandomDataServiceClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;
import java.text.MessageFormat;
import java.util.List;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    private final UserRepository userRepository;

    private final RabbitMQProperties rabbitMQProperties;

    private final RandomDataServiceClient randomDataService;

    private final MessagingService messagingService;

    public UserController(RandomDataServiceClient randomDataService
            , UserRepository userRepository
            , RabbitMQProperties rabbitMQProperties
            , MessagingService messagingService) {
        this.randomDataService = randomDataService;
        this.userRepository = userRepository;
        this.rabbitMQProperties = rabbitMQProperties;
        this.messagingService = messagingService;
    }

    @GetMapping(value = "/generate")
    public ResponseEntity<Response<User>> generateUsers(@RequestParam("size") Integer size) throws URISyntaxException {
        final List<User> users = randomDataService.getUsers(size);
        final List<User> savedUsers = userRepository.saveAll(users);

        return ResponseEntity.created(new URI("/users")).body(new Response<>(savedUsers));
    }

    @GetMapping(value = "/generate/async")
    public ResponseEntity<Response<String>> generateUsersAsync(@RequestParam("size") Integer size) {
        final List<User> users = randomDataService.getUsers(size);

        users.forEach(user -> {
            try {
                messagingService.publish(rabbitMQProperties.getQueue().getUserRegister(), user);
            } catch (JsonProcessingException e) {
                throw new IllegalCallerException(e);
            }
        });

        final String message = MessageFormat.format("Request to generate {0} users was accepted.", size);
        return ResponseEntity.accepted().body(new Response(List.of(message)));
    }

    @GetMapping
    public ResponseEntity<Response<User>> getUsers(){
        final List<User> users = userRepository.findAll();
        return ResponseEntity.ok(new Response(users));
    }

}
