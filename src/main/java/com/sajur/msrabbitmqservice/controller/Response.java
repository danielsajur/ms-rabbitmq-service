package com.sajur.msrabbitmqservice.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Data
@RequiredArgsConstructor
public class Response<T> {

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime timestamp = LocalDateTime.now();
    private final Integer size = 0;
    private final List<T> data;

    public Integer getSize() {
        if(Objects.isNull(data)){
            return size;
        }
        return data.size();
    }
}
