package com.liyghting.rabbitmqdemo;

import com.liyghting.rabbitmqdemo.core.BaseConsumer;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Test2Consumer extends BaseConsumer<List<ReceiveUser>> {
    @Override
    public ParameterizedTypeReference<List<ReceiveUser>> getParameterizedTypeReference() {
        return new ParameterizedTypeReference<List<ReceiveUser>>() {
        };
    }

    @Override
    public void handleMsg(List<ReceiveUser> users) {
        logger.info("Test2Consumer " + users);

    }
}
