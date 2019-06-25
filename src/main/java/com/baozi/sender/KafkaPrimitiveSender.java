package com.baozi.sender;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import javax.annotation.Resource;

/**
 * 不对数据作任何格式化处理的发送者
 * @author 郑凯努 date 2018/10/23
 */
@Component
@Slf4j
public class KafkaPrimitiveSender<T> implements KafkaSender<T> {

    @Resource
    private KafkaTemplate<String,T> kafkaTemplate;

    @Override
    public void send(T obj, String topic) {
        ListenableFuture<SendResult<String, T>> future = kafkaTemplate.send(topic, obj);
        future.addCallback(new ListenableFutureCallback<>() {
            @Override
            public void onFailure(@NonNull Throwable throwable) {
                log.error("Produce: The message failed to be sent: {}",throwable.getMessage());
            }

            @Override
            public void onSuccess(SendResult<String, T> stringObjectSendResult) {
                log.info("Produce: The message {} was sent successfully",obj);
            }
        });
    }
}
