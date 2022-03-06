package com.sajur.msrabbitmqservice.config;

import lombok.Data;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Data
public class Queues {

    private String productError;
    private String productRegister;
    private String userError;
    private String userRegister;

    private static final Map<String, Map<String, Object>> headers = new HashMap<>();

    private void initHeaders() {
        headers.put(userRegister, header("user-error"));
        headers.put(userRegister, header("user-register"));

//        headers.put(productRegister, header("product-error"));
//        headers.put(productRegister, header("product-register"));
    }

    private Map<String, Object> header(String headerValue) {
        final String X_MATCH = "x-match";
        final String NEXT_STEP = "next-step";
        final Map<String, Object> mapFilePontosIn = new HashMap<>();
        mapFilePontosIn.put(X_MATCH, "any");
        mapFilePontosIn.put(NEXT_STEP, headerValue);
        return mapFilePontosIn;
    }

    public Set<String> getAllQueues(){
        if(CollectionUtils.isEmpty(headers)){
            initHeaders();
        }
        return headers.keySet();
    }

    public Map<String, Object> getHeader(String queueName){
        if(CollectionUtils.isEmpty(headers)){
            initHeaders();
        }
        return headers.get(queueName);
    }

}
