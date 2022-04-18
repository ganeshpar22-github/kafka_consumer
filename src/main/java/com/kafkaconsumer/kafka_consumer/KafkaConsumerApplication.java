package com.kafkaconsumer.kafka_consumer;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class KafkaConsumerApplication {
	
	/*Here we will try to consume those msg which were published by producer
	 * 
	 * we will consume using kafka listner/consumer */
	
	List<String> messages = new ArrayList<>();
	
	//User object
	User obj = null;
	
	/*Actually in kafka we doesn't write endpoints, this is only for ui purpose
	 * all things are take care by listners mentioned below endpoints
	 */
	
	
	/*	rest-end point where we are returning list of msg 
	 * for the publisher where we published
	 * string msg*/
	
	@GetMapping("/consumeStringMessage")
	public List<String> consumeMsg(){
		return messages;
	}
	

	/*	rest-end point where we are returning list of json/object msg 
	 * for the publisher where we published
	 * json/object msg*/
	@GetMapping("/consumeJsonMessage")
	public User consumeJsonMsg() {
		return obj;
	}
	
	
	
	//we will list of msg from our topic, string msg
	@KafkaListener(groupId="group-1",topics="kafkaspringboot",containerFactory="kafkaListenerContainerFactory")
	public List<String> getMsgFromTopic(String data){
		messages.add(data);
		return messages;
	}
	
	
	//we will list of msg from our topic, json/object msg
		@KafkaListener(groupId="group-2",topics="kafkaspringboot",containerFactory="userKafkaListenerContainerFactory")
		public User getJsonMsgFromTopic(User user){
			obj = user;
			return obj;
		}
	
	

	public static void main(String[] args) {
		SpringApplication.run(KafkaConsumerApplication.class, args);
	}

}
