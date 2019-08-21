package com.liyghting.rabbitmqdemo.core;

public class ProducerUtil {

    public static <T> void send(String producerBeanName, T msg) {
        JsonStringProducer producer = SpringContextUtil.getBean(producerBeanName, JsonStringProducer.class);
        producer.send(msg);
    }
}
