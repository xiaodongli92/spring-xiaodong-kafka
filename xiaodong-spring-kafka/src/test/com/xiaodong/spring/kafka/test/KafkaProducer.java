package com.xiaodong.spring.kafka.test;

import com.xiaodong.spring.kafka.KafkaManager;
import org.springframework.kafka.core.KafkaTemplate;

public class KafkaProducer {

   public static void main(String[] args) {
       KafkaManager manager = new KafkaManager();
       KafkaTemplate<String, String> template = manager.createTemplate();
       template.send("topic1", "key1", "this is 110 message");
       template.flush();
   }
}
