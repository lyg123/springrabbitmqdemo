package com.liyghting.rabbitmqdemo.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Map;

@ConfigurationProperties
@Component
public class RabbitmqConfig implements ApplicationContextAware, InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(RabbitmqConfig.class);
    private Map<String, Map<String, String>> rabbitmqBindingMap;
    private Map<String, Map<String, String>> rabbitmqProducerMap;
    private ApplicationContext applicationContext;
    @Autowired
    private AmqpAdmin rabbitAdmin;
    @Autowired
    private ConnectionFactory connectionFactory;
    @Autowired
    private MessageConverter jsonMessageConverter;


    @Override
    public void afterPropertiesSet() throws Exception {
        logger.info("RabbitMQConfig 加载开始....................");
        ((RabbitAdmin) rabbitAdmin).getRabbitTemplate().setMessageConverter(jsonMessageConverter);
        // 用来向spring容器中注入相应主题的消息生产者
        DefaultListableBeanFactory defaultListableBeanFactory = (DefaultListableBeanFactory) applicationContext
                .getAutowireCapableBeanFactory();
        if (rabbitmqProducerMap != null && rabbitmqProducerMap.size() > 0) {
            logger.info("rabbitmqProducerMap size {}", rabbitmqProducerMap.size());
            for (Map<String, String> hm : rabbitmqProducerMap.values()) {
                String exchangeName = hm.get("exchangeName");
                String routingKey = hm.get("routingKey");
                String producerBeanName = hm.get("producerBeanName");

                BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder
                        .genericBeanDefinition(JsonStringProducer.class);
                beanDefinitionBuilder.addConstructorArgValue(defaultListableBeanFactory.getBean(AmqpTemplate.class));
                beanDefinitionBuilder.addConstructorArgValue(exchangeName);
                beanDefinitionBuilder.addConstructorArgValue(routingKey);
                defaultListableBeanFactory.registerBeanDefinition(producerBeanName,
                        beanDefinitionBuilder.getBeanDefinition());

            }

        }

        if (rabbitmqBindingMap != null && rabbitmqBindingMap.size() > 0) {
            logger.info("rabbitmqBindingMap size {}", rabbitmqBindingMap.size());
            for (Map<String, String> hm : rabbitmqBindingMap.values()) {
                String exchangeName = hm.get("exchangeName");
                String queueName = hm.get("queueName");
                String routingKey = hm.get("routingKey");
                String consumerBeanName = hm.get("consumerBeanName");
                // 创建exchange等信息
                rabbitAdmin.declareExchange(new TopicExchange(exchangeName));
                rabbitAdmin.declareQueue(new Queue(queueName));
                rabbitAdmin.declareBinding(
                        new Binding(queueName, Binding.DestinationType.QUEUE, exchangeName, routingKey, null));

                // 建立消费代码与队列的关系
                MessageListener messageListener = applicationContext.getBean(consumerBeanName, MessageListener.class);
                SimpleMessageListenerContainer simpleMessageListenerContainer = new SimpleMessageListenerContainer(
                        connectionFactory);
                simpleMessageListenerContainer.setMessageListener(messageListener);
                simpleMessageListenerContainer.setQueueNames(queueName);
                simpleMessageListenerContainer.start();

            }
        }


        logger.info("RabbitMQConfig 加载结束....................");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public Map<String, Map<String, String>> getRabbitmqBindingMap() {
        return rabbitmqBindingMap;
    }

    public void setRabbitmqBindingMap(Map<String, Map<String, String>> rabbitmqBindingMap) {
        this.rabbitmqBindingMap = rabbitmqBindingMap;
    }

    public Map<String, Map<String, String>> getRabbitmqProducerMap() {
        return rabbitmqProducerMap;
    }

    public void setRabbitmqProducerMap(Map<String, Map<String, String>> rabbitmqProducerMap) {
        this.rabbitmqProducerMap = rabbitmqProducerMap;
    }


}
