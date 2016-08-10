package com.xiaodong.spring.kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.kafka.core.*;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.listener.config.ContainerProperties;

import java.util.HashMap;
import java.util.Map;

public class KafkaManager {

    public KafkaManager() {

    }

    /**
     * 消费者
     * @param containerProperties
     * @return
     */
    public KafkaMessageListenerContainer<String, String> createContainer(
            ContainerProperties containerProperties) {
        Map<String, Object> props = consumerProps();
        //消费工厂
        ConsumerFactory<String, String> consumerFactory =
                new DefaultKafkaConsumerFactory<>(props);
        return new KafkaMessageListenerContainer<>(consumerFactory, containerProperties);
    }

    /**
     * 生产者
     * @return
     */
    public KafkaTemplate<String, String> createTemplate() {
        Map<String, Object> producerProps = producerProps();
        ProducerFactory<String, String> producerFactory =
                new DefaultKafkaProducerFactory<>(producerProps);
        return new KafkaTemplate<>(producerFactory);
    }

    /**
     * 消费者配置相关
     * @return
     */
    private Map<String, Object> consumerProps() {
        Map<String,Object> props = new HashMap<>();
        //监听的kafka集群,多个用逗号隔开
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        //consumer要消费的group id
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "test-consumer-group");
        //支持自动提交
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true);
        //自动提交时间 100ms
        props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "100");
        //检测kafka的组管理设施失败的超时时间
        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "15000");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        return props;
    }

    /**
     * 生产者配置相关
     * @return
     */
    private Map<String, Object> producerProps() {
        Map<String,Object> props = new HashMap<>();
        //kafka集群地址
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ProducerConfig.RETRIES_CONFIG, 0);
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, 16384);
        props.put(ProducerConfig.LINGER_MS_CONFIG, 1);
        props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        return props;
    }
}
