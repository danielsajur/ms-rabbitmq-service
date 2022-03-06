package com.sajur.msrabbitmqservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface MessagingService {

    <T> void publish(final String queue, T message) throws JsonProcessingException;

}
