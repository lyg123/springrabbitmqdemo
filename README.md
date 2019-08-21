# springrabbitmqdemo
基于spring的主题发布和订阅代码

# 原理
基于spring启动的时候加载发布订阅配置，发送基于json的消息，具体代码请看`RabbitmqConfig.java`

# 发布消息例子
## 配置application.yml
```
rabbitmqProducerMap:
  testProducer1:
    exchangeName: testExchange
    routingKey: test1
    producerBeanName: test1Producer
  testProducer2:
    exchangeName: testExchange
    routingKey: test2
    producerBeanName: test2Producer
```

## 代码
```
ProducerUtil.send("test1Producer", user);
```
具体请看`TestProducer.java`

# 订阅消息例子
## 配置application.yml
```
rabbitmqBindingMap:
  test1Binding:
    queueName: test1Queue
    exchangeName: testExchange
    routingKey: test1
    consumerBeanName: test1Consumer
  test2Binding:
    queueName: test2Queue
    exchangeName: testExchange
    routingKey: test2
    consumerBeanName: test2Consumer
```
## 代码实现BaseConsumer两个方法
```
@Override
    public ParameterizedTypeReference<ReceiveUser> getParameterizedTypeReference() {
        return new ParameterizedTypeReference<ReceiveUser>() {
        };
    }

    @Override
    public void handleMsg(ReceiveUser msg) {
        logger.info("Test1Consumer " + msg);

    }
```
具体请看`Test1Consumer.java`
