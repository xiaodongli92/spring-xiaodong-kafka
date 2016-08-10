package com.xiaodong.spring.kafka.test;

import com.xiaodong.spring.kafka.KafkaManager;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.kafka.listener.config.ContainerProperties;

import java.util.concurrent.CountDownLatch;

/**
 * Created by lixiaodong on 16/7/29.
 */
public class KafkaManagerTest {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("----------start");
        KafkaManager manager = new KafkaManager();
        ContainerProperties containerProperties = new ContainerProperties("topic1", "topic2");
        KafkaMessageListenerContainer<String, String> container = manager.createContainer(containerProperties);
        final CountDownLatch latch = new CountDownLatch(4);
        containerProperties.setMessageListener(new MessageListener<Integer, String>() {
            @Override
            public void onMessage(ConsumerRecord<Integer, String> record) {
                System.out.println("接受到的消息:"+record);
                latch.countDown();
            }
        });
        container.setBeanName("test");
        container.start();
        Thread.sleep(1000);
        KafkaTemplate<String, String> template = manager.createTemplate();
        template.flush();
        container.stop();
        System.out.println("---------stop");
    }
}
