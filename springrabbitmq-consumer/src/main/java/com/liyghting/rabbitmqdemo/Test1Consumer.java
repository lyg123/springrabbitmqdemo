package com.liyghting.rabbitmqdemo;

import com.liyghting.rabbitmqdemo.core.BaseConsumer;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;

@Component
public class Test1Consumer extends BaseConsumer<ReceiveUser> {

    @Override
    public ParameterizedTypeReference<ReceiveUser> getParameterizedTypeReference() {
        return new ParameterizedTypeReference<ReceiveUser>() {
        };
    }

    @Override
    public void handleMsg(ReceiveUser msg) {
        logger.info("Test1Consumer " + msg);

    }
}
