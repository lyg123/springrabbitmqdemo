package com.liyghting.rabbitmqdemo.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

@Component
public abstract class BaseConsumer<T> implements MessageListener {

    protected static final Logger logger = LoggerFactory.getLogger(BaseConsumer.class);

    @Autowired
    private Jackson2JsonMessageConverter jsonMessageConverter;

    @Override
    public void onMessage(Message message) {
        logger.info("receive msg start : {}", message);
        T msg = (T) jsonMessageConverter.fromMessage(message, getParameterizedTypeReference());
        handleMsg(msg);
        logger.info("receive msg end");
    }

    public abstract ParameterizedTypeReference<T> getParameterizedTypeReference();

    public abstract void handleMsg(T msg);

}
