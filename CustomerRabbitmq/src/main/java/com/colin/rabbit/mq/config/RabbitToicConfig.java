package com.colin.rabbit.mq.config;

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

@Configuration
public class RabbitToicConfig {

	@Bean(SysConstants.COLIN_TOPIC_EXHANGE)
	public TopicExchange topicExchange() {
		return ExchangeBuilder.topicExchange(SysConstants.COLIN_TOPIC_EXHANGE).durable(true).build();
	}

	@Bean(SysConstants.COLIN_TOPIC_QUEUE)
	public Queue topicQueue() {
		return QueueBuilder.durable(SysConstants.COLIN_TOPIC_QUEUE).build();
	}

	@Bean
	public Binding topicBind(@Qualifier(SysConstants.COLIN_TOPIC_EXHANGE) TopicExchange topicExchange,
			@Qualifier(SysConstants.COLIN_TOPIC_QUEUE) Queue topicQueue) {
		Binding binding = BindingBuilder.bind(topicQueue).to(topicExchange).with(SysConstants.COLIN_TOPIC_ROUTE_REGIX);
		binding.setIgnoreDeclarationExceptions(true);
		return binding;
	}
}
