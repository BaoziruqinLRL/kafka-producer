package com.laidantech.sender;

/**
 * kafka发送者接口
 * @author 郑凯努 date 2018/10/23
 */
public interface KafkaSender<T> {

    /**
     * 指定主题发送数据
     * @param obj 发送的数据
     * @param topic topic 主题
     */
    public void send(T obj,String topic);
}
