package com.liyghting.rabbitmqdemo.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

public class JsonStringProducer {
    private static final Logger log = LoggerFactory.getLogger(JsonStringProducer.class);
    private String exchange;
    private String routingKey;

    private AmqpTemplate rabbitTemplate;

    public JsonStringProducer(AmqpTemplate rabbitTemplate, String exchange, String routingKey) {
        this.rabbitTemplate = rabbitTemplate;
        this.exchange = exchange;
        this.routingKey = routingKey;
    }

    public <T> void send(T msg) {
        log.info("sender begin");
        MessageProperties properties = new MessageProperties();
        properties.setContentType(MessageProperties.CONTENT_TYPE_JSON);
        Message message =  ((RabbitTemplate)rabbitTemplate).getMessageConverter().toMessage(msg, properties);
        rabbitTemplate.convertAndSend(exchange, routingKey, message);
        log.info("rabbitmq发送成功");
    }
}
