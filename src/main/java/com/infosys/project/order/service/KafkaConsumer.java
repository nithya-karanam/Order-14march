package com.infosys.project.order.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {
	String msg;

	
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	@KafkaListener(topics="TestTopic",groupId="group_id",containerFactory = "concurrentKafkaListenerContainerFactory")
	public void consume(String message) {
		msg=message;
		System.out.println(message);
	}
	
}
