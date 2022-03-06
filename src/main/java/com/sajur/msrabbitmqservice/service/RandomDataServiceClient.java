package com.sajur.msrabbitmqservice.service;

import com.sajur.msrabbitmqservice.model.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "${random-data.name}", url = "${random-data.url}")
public interface RandomDataServiceClient {

    @GetMapping(value = "/users/random_user")
    List<User> getUsers(@RequestParam("size") Integer size);
}
