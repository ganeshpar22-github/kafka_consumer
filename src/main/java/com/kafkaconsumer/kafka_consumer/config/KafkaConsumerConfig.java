package com.kafkaconsumer.kafka_consumer.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import com.kafkaconsumer.kafka_consumer.User;

@Configuration
@EnableKafka
public class KafkaConsumerConfig {
	
	//configuration for string data
	
	@Bean
	public ConsumerFactory<String, String> consumerFactory(){
		
		Map<String,Object> configs = new HashMap<>();
		configs.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		configs.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		configs.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		
		//mentioning here group
		//may be in producer there me chance of creating multiple object or media type
		//for different publish msg we need different group id
		//this is same like group in kafka consumer (maybe)
		configs.put(ConsumerConfig.GROUP_ID_CONFIG, "group-1");
		
		return new DefaultKafkaConsumerFactory<>(configs);
	}
	
	//The above consumerFactory we have to inject with kafka container listner
	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory(){
		
		ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<String,String>();
		factory.setConsumerFactory(consumerFactory());
		return factory;
	}
	
	
	
	
	//configuration for json/user object response
	/*Only change is of groupId and replace string deserializer with jsondesrializer*/
	
	@Bean
	public ConsumerFactory<String, User> userConsumerFactory(){
		
		Map<String,Object> configs = new HashMap<>();
		configs.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		configs.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
		configs.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
		
		//mentioning here group
		//may be in producer there me chance of creating multiple object or media type
		//for different publish msg we need different group id
		//this is same like group in kafka consumer (maybe)
		configs.put(ConsumerConfig.GROUP_ID_CONFIG, "group-2");
		
		return new DefaultKafkaConsumerFactory<>(configs, new StringDeserializer(),new JsonDeserializer<>(User.class));
	}
	
	//The above consumerFactory we have to inject with kafka container listner
	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, User> userKafkaListenerContainerFactory(){
		
		ConcurrentKafkaListenerContainerFactory<String, User> factory = new ConcurrentKafkaListenerContainerFactory<String,User>();
		factory.setConsumerFactory(userConsumerFactory());
		return factory;
	}


}
