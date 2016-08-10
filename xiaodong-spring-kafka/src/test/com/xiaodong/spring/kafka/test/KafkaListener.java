package com.xiaodong.spring.kafka.test;

import com.xiaodong.spring.kafka.KafkaManager;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.kafka.listener.config.ContainerProperties;

public class KafkaListener {

    public static void main(String[] args) {
        KafkaManager manager = new KafkaManager();
        ContainerProperties containerProperties = new ContainerProperties("topic1");
        KafkaMessageListenerContainer<String, String> container = manager.createContainer(containerProperties);
        containerProperties.setMessageListener(new MessageListener<Integer, String>() {
            @Override
            public void onMessage(ConsumerRecord<Integer, String> record) {
                System.out.println("接收到新的请求:"+record);
            }
        });
        container.start();
    }
}
