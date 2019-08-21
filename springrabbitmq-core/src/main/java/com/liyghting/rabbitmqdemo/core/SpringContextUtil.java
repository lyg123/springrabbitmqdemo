package com.liyghting.rabbitmqdemo.core;

import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class SpringContextUtil implements ApplicationContextAware {
    private static ApplicationContext applicationContext;

    public SpringContextUtil() {
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        SpringContextUtil.applicationContext = applicationContext;
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static <T> T getBean(String beanName, Class<T> clazz) {
        return applicationContext.getBean(beanName, clazz);
    }

    public static <T> T getBeanOfType(Class<T> type) {
        Map<String, T> beans = applicationContext.getBeansOfType(type);
        if (beans.size() == 0) {
            throw new NoSuchBeanDefinitionException(type, "Unsatisfied dependency of type [" + type + "]: expected at least 1 matching bean");
        } else if (beans.size() > 1) {
            throw new NoSuchBeanDefinitionException(type, "expected single matching bean but found " + beans.size() + ": " + beans.keySet());
        } else {
            return beans.values().iterator().next();
        }
    }
}