package com.baozi.sender;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import javax.annotation.Resource;

/**
 * @Description: kafka消息发送器
 *               这是一个默认的发送器，对应接收器中的DefaultDeserializer解析器，
 *               如果需要自定义其它发送器，需要实现consumer中的AbstractDeserializer
 *               接口，从而实现配套的接收数据转换器
 * @Author: lirl
 * @Create: 2018-10-23 13:50
 */
@Component
@Slf4j
public class KafkaDefaultSender<T> implements KafkaSender<T> {

    @Resource
    private KafkaTemplate<String,String> kafkaTemplate;

    @Override
    public void send(T obj,String topic){
        String msg = JSON.toJSONString(obj);

        // 发送消息
        ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(topic, msg);
        future.addCallback(new ListenableFutureCallback<>() {
            @Override
            public void onFailure(Throwable throwable) {
                log.error("Produce: The message failed to be sent: {}",throwable.getMessage());
            }

            @Override
            public void onSuccess(SendResult<String, String> stringObjectSendResult) {
                log.info("Produce: The message {} was sent successfully",msg);
            }
        });
    }
}
