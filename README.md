# kafka-producer
kafka生产者工具，该项目开箱即用，默认连接localhost:9092

### 使用方式
目前有两个发送器实现  

1.KafkaDefaultSender  
  - 默认发送器，发送的数据为String格式   
2.KafkaPrimitiveSender   
  - 发送的数据为原始格式，即Object   
通过如下注入方式调用   
```java
@Resource(type = KafkaDefaultSender.class)
private KafkaSender<String> kafkaSender;

public void send(){
  kafkaSender.send("test","topic-test");
}
```
