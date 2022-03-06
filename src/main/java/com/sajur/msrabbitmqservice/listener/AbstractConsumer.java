package com.sajur.msrabbitmqservice.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import org.springframework.amqp.rabbit.connection.Connection;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;

import static java.util.Objects.isNull;

abstract class AbstractConsumer<R> extends DefaultConsumer {

    private static final Boolean AUTO_ACK = Boolean.FALSE;

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * Constructs a new instance and records its association to the passed-in channel.
     */
    public AbstractConsumer(Connection connection, String queue) throws IOException {
        super(connection.createChannel(true));
        getChannel().basicConsume(queue, AUTO_ACK, this);
    }

    @Override
    public void handleDelivery(String s, Envelope envelope, AMQP.BasicProperties basicProperties, byte[] bytes) throws IOException {
        if (isNull(bytes)) {
            return;
        }

        try{
            Class<R> type = (Class<R>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
            R messageAsObject = objectMapper.readValue(new String(bytes), type);
            process(messageAsObject);
            getChannel().basicAck(envelope.getDeliveryTag(), false);
        } catch (Exception ex) {
            getChannel().basicReject(envelope.getDeliveryTag(), true);
        }
    }

    abstract void process(R object) throws Exception;
}
