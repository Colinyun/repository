package com.colin.rabbit.mq.service;

import java.util.Map;

import com.colin.rabbit.mq.model.ResponseEntity;
import com.colin.rabbit.mq.model.ResultMessage;

public interface IProducerService {

	void senderMessage(ResponseEntity<String> responseEntity, Map<String, Object> properties);

	ResultMessage sendDeadLetterMessage();
	
	/**
	 * 
	 * @auth c-chenyun
	 * @date 2020-06-24
	 * @describe 延迟队列的生产者
	 *
	 */
	ResultMessage sendDelayMessage(String message);

}
