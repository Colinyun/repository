package com.colin.rabbit.mq.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.colin.rabbit.mq.commons.SysConstants;

/**
 * 
 * @author c-chenyun
 * @date 2020-06-24
 * @describe 
 * 1、死信队列，当正常队列消费失败或者返回消费时，可进入死信队列;
 * 2、死信队列可设置过期时间，当一段时间后未消费后，如果设置过期时间了，则消息过期
 * 
 */
@Configuration
public class RabbitDeadLetterConfig {

	/********************************** 死信队列 ************************************/
	@Bean(SysConstants.COLIN_DEAD_LETTER_EXCHANGE)
	public TopicExchange getTopicExchange() {
		return ExchangeBuilder.topicExchange(SysConstants.COLIN_DEAD_LETTER_EXCHANGE).durable(true).build();
	}

	@Bean(SysConstants.COLIN_DEAD_LETTER_QUEUE)
	public Queue getDeadLetterQueue() {
		Map<String, Object> args = new HashMap<String, Object>();
		args.put("x-dead-letter-exchange", SysConstants.COLIN_DEAD_LETTER_EXCHANGE);
		args.put("x-dead-letter-routing-key", SysConstants.COLIN_DEAD_LETTER_ROUTE_REGIX);
		return QueueBuilder.durable(SysConstants.COLIN_DEAD_LETTER_QUEUE).withArguments(args).build();
	}

	@Bean
	public Binding doDeadLetterBinding(@Qualifier(SysConstants.COLIN_DEAD_LETTER_EXCHANGE) TopicExchange exchange,
			@Qualifier(SysConstants.COLIN_DEAD_LETTER_QUEUE) Queue queue) {
		return BindingBuilder.bind(queue).to(exchange).with(SysConstants.COLIN_DEAD_LETTER_ROUTE_REGIX);
	}
	
	/*************************** 死信队列后的新队列存储 ********************************/
	@Bean(SysConstants.COLIN_DEAD_REDIRECT_QUEUE)
	public Queue getDeadRedirectQueue() {
		return QueueBuilder.durable(SysConstants.COLIN_DEAD_REDIRECT_QUEUE).build();
	}

	@Bean
	public Binding doDeadRedirectBinding(@Qualifier(SysConstants.COLIN_DEAD_LETTER_EXCHANGE) TopicExchange exchange,
			@Qualifier(SysConstants.COLIN_DEAD_REDIRECT_QUEUE) Queue queue) {
		return BindingBuilder.bind(queue).to(exchange).with(SysConstants.COLIN_DEAD_REDIRECT_ROUTE_REGIX);
	}
}
