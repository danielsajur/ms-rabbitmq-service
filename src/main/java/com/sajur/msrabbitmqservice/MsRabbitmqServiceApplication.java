package com.sajur.msrabbitmqservice;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
@EnableFeignClients
public class MsRabbitmqServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsRabbitmqServiceApplication.class, args);
    }


    @Bean
    public JavaTimeModule dateTimeModule(){
        return new JavaTimeModule();
    }
}
