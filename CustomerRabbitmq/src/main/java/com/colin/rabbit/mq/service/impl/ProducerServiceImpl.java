package com.colin.rabbit.mq.service.impl;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.colin.rabbit.mq.commons.SysConstants;
import com.colin.rabbit.mq.model.ResponseEntity;
import com.colin.rabbit.mq.model.ResultMessage;
import com.colin.rabbit.mq.service.IProducerService;
import com.colin.rabbit.mq.utils.JsonUtils;

@Service
public class ProducerServiceImpl implements IProducerService {

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@Override
	public void senderMessage(ResponseEntity<String> responseEntity, Map<String, Object> properties) {
		String id = UUID.randomUUID().toString().replace("-", "");
		MessageProperties messageProperties = getMessageProperties(properties);
		messageProperties.setHeader("error", "this this errorRabbitmq message!");
		Message message = new Message(JsonUtils.toBytes(responseEntity), messageProperties);
		rabbitTemplate.convertAndSend(SysConstants.COLIN_TOPIC_EXHANGE, SysConstants.COLIN_TOPIC_ROUTE_KEY, message,
				new CorrelationData(id));
		System.out.println("-----------生产者模式----------");
		System.out.println("开始发送消息...");
	}

	@Override
	public ResultMessage sendDeadLetterMessage() {
		CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
		MessageProperties messageProperties = getMessageProperties(null);
		messageProperties.setExpiration("10000");
		ResponseEntity<Object> response = ResponseEntity.build().add("this is dead letter rabbit...");
//		Message message = new Message(JsonUtils.toBytes(response), messageProperties);
		MessagePostProcessor mPostProcessor = (message) -> {
			MessageProperties properties = message.getMessageProperties();
			properties.setContentEncoding("utf-8");
			properties.setContentType("application/json");
			properties.setExpiration("10000");
			return message;
		};
		rabbitTemplate.convertAndSend(SysConstants.COLIN_DEAD_LETTER_EXCHANGE, SysConstants.COLIN_DEAD_LETTER_ROUTE_KEY,
				response, mPostProcessor, correlationData);
		System.out.println(LocalTime.now().toString());
		return ResultMessage.success().setMsg("生产者生产消息成功....");
	}

	private MessageProperties getMessageProperties(Map<String, Object> properties) {
		MessageProperties messageProperties = new MessageProperties();
		messageProperties.setContentType("application/json");
		Optional.ofNullable(properties).orElse(new HashMap<String, Object>()).forEach((k, v) -> {
			messageProperties.setHeader(k, v);
		});
		return messageProperties;
	}

	@Override
	public ResultMessage sendDelayMessage(String content) {
		ResponseEntity<Object> response = ResponseEntity.build().add(content);
		String msgId = UUID.randomUUID().toString();
//		MessagePostProcessor mPostProcessor = (message) -> {
//			MessageProperties properties = message.getMessageProperties();
//			properties.setContentEncoding("utf-8");
//			properties.setContentType(MessageProperties.CONTENT_TYPE_JSON);
//			properties.setMessageId(msgId);
//			return message;
//		};
		Message message = MessageBuilder.withBody(JsonUtils.toJson(response).getBytes()).setContentEncoding("utf-8")
				.setContentType(MessageProperties.CONTENT_TYPE_JSON).setMessageId(msgId).setExpiration(10000 + "")
				.build();
		rabbitTemplate.convertAndSend(SysConstants.COLIN_DELAY_FORWARDING_EXCHANGE,
				SysConstants.COlin_DELAY_FORWARDING_ROUTE_KEY, message, new CorrelationData(msgId));
		System.out.println("生产者生产消息...." + LocalTime.now());
		return ResultMessage.success().setMsg("已成功发送......");
	}
}
