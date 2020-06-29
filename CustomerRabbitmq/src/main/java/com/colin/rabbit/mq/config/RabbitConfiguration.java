package com.colin.rabbit.mq.config;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfiguration {

	@Autowired
	private RabbitTemplate rabbitTemplate;

	private final RabbitTemplate.ConfirmCallback confirmCallback = (correlationData, ack, cause) -> {
		System.out.println("correlationData..." + correlationData);
		System.out.println("ack..." + ack);
		System.out.println("cause..." + cause);
		if (!ack) {
			// 补偿措施
			System.out.println("异常处理");
		}
	};

	private final RabbitTemplate.ReturnCallback returnCallbakc = (message, replyCode, replyText, exchange,
			routingKey) -> {
		System.out.println("return exchange:" + exchange + ",routingKey:" + routingKey + ",replyCode:" + replyCode
				+ ",replyText:" + replyText);
	};

	@Bean
	public AmqpTemplate getRabbitTemplate() {
		rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
		rabbitTemplate.setEncoding("UTF-8");
		rabbitTemplate.setMandatory(true);
		rabbitTemplate.setConfirmCallback(confirmCallback);
		rabbitTemplate.setReturnCallback(returnCallbakc);
		return rabbitTemplate;
	}
	
	@Bean
	public RabbitListenerContainerFactory<?> rabbitListenerContainerFactory(ConnectionFactory connectionFactory){
		SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
		factory.setConnectionFactory(connectionFactory);
		factory.setMessageConverter(new Jackson2JsonMessageConverter());
		factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
		return factory;
	}
}
