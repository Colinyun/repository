package com.colin.rabbit.mq.service.impl;

import java.io.IOException;
import java.time.LocalTime;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.colin.rabbit.mq.commons.SysConstants;
import com.colin.rabbit.mq.model.ResponseEntity;
import com.colin.rabbit.mq.service.ICustomerService;
import com.rabbitmq.client.Channel;

@Service
@Component
public class CustomerServiceImpl implements ICustomerService {

	@RabbitListener(queues = { SysConstants.COLIN_TOPIC_QUEUE })
	public void readerTopicExchangeMessage(Message<ResponseEntity<String>> message, Channel channel)
			throws IOException {
		StringBuffer sb = new StringBuffer("-----------消费者模式----------\r\n");
		sb.append("message Payload:").append(message.getPayload()).append("\r\n");
		sb.append(message.getHeaders());
		ResponseEntity<String> response = message.getPayload();
		int totalCount = (int) message.getHeaders().get("totalCount");
		if (totalCount != response.getList().size()) {
			channel.basicReject((long) message.getHeaders().get(AmqpHeaders.DELIVERY_TAG), false);
		}
		channel.basicAck((long) message.getHeaders().get(AmqpHeaders.DELIVERY_TAG), false);
		System.out.println(sb);
	}

//	@RabbitListener(queues = { SysConstants.COLIN_DEAD_LETTER_QUEUE })
//	public void readDeadLetterExchangeMessage(Message<ResponseEntity<String>> message, Channel channel)
//			throws IOException {
//		StringBuffer sb = new StringBuffer("-----------消费者模式----------\r\n");
//		sb.append("message Payload:").append(message.getPayload()).append("\r\n");
//		sb.append(message.getHeaders());
//		channel.basicAck((long) message.getHeaders().get(AmqpHeaders.DELIVERY_TAG), false);
//		System.out.println(sb);
//		System.out.println(LocalTime.now().toString());
//	}
	
	@RabbitListener(queues = { SysConstants.COLIN_DELAY_QUEUE })
	public void readerDelayMessage(Message<ResponseEntity<String>> message, Channel channel) throws IOException {
		StringBuffer sb = new StringBuffer("-----------消费者模式----------\r\n");
		sb.append("message Payload:").append(message.getPayload()).append("\r\n");
		sb.append(message.getHeaders());
		channel.basicAck((long) message.getHeaders().get(AmqpHeaders.DELIVERY_TAG), false);
		System.out.println(sb);
		System.out.println(LocalTime.now().toString());
	}
}
