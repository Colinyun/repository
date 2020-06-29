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
 * @auth c-chenyun
 * @date 2020-06-24
 * @desc 延迟队列配置类
 * 使用场景：
 * 1、用户下单，但未支付，可通过该延迟队列设置延迟消费，判断具体时间内是否消费生信息
 * 
 * 
 * 注意点：
 * 延迟队列主要时用监听死信队列来完成的延迟消费的，消息在设定的过期时间内如果未消费就过期了
 * 参数：
 * x-dead-letter-exchange：死信队列绑定的交换机（跟交换机类型无关，可以是topic，也可以是默认的direct）
 * x-dead-letter-routing-key：死信队列绑定的key
 */
@Configuration
public class RabbitDelayConfig {

	/**
	 * 
	 * @auth c-chenyun
	 * @date 2020-06-29
	 * @describe  普通交换机
	 *
	 */
	@Bean(SysConstants.COLIN_DELAY_EXCHANGE)
	public TopicExchange delayExchange() {
		return ExchangeBuilder.topicExchange(SysConstants.COLIN_DELAY_EXCHANGE).durable(true).build();
	}

	/**
	 * 
	 * @auth c-chenyun
	 * @date 2020-06-29
	 * @describe  延迟交换机
	 *
	 */
	@Bean(SysConstants.COLIN_DELAY_FORWARDING_EXCHANGE)
	public TopicExchange delayForwardingExchange() {
		return ExchangeBuilder.topicExchange(SysConstants.COLIN_DELAY_FORWARDING_EXCHANGE).durable(true).build();
	}

	/**
	 * 
	 * @auth c-chenyun
	 * @date 2020-06-29
	 * @describe  普通队列
	 *
	 */
	@Bean(SysConstants.COLIN_DELAY_QUEUE)
	public Queue delayQueue() {
		return QueueBuilder.durable(SysConstants.COLIN_DELAY_QUEUE).build();
	}

	/**
	 * 
	 * @auth c-chenyun
	 * @date 2020-06-29
	 * @describe  延迟队列
	 *
	 */
	@Bean(SysConstants.COLIN_DELAY_FORWARDING_QUEUE)
	public Queue delayForwadingQueue() {
		Map<String, Object> args = new HashMap<String, Object>(3);
		args.put("x-dead-letter-exchange", SysConstants.COLIN_DELAY_EXCHANGE);
		args.put("x-dead-letter-routing-key", SysConstants.COLIN_DELAY_ROUTE_REGIX);
		return QueueBuilder.durable(SysConstants.COLIN_DELAY_FORWARDING_QUEUE).withArguments(args).build();
	}

	/**
	 * 
	 * @auth c-chenyun
	 * @date 2020-06-29
	 * @describe  普通队列绑定route key
	 *
	 */
	@Bean
	public Binding delayBinding(@Qualifier(SysConstants.COLIN_DELAY_EXCHANGE) TopicExchange exchange,
			@Qualifier(SysConstants.COLIN_DELAY_QUEUE) Queue queue) {
		return BindingBuilder.bind(queue).to(exchange).with(SysConstants.COLIN_DELAY_ROUTE_REGIX);
	}

	/**
	 * 
	 * @auth c-chenyun
	 * @date 2020-06-29
	 * @describe  普通队列绑定route key
	 *
	 */
	@Bean
	public Binding delayForwardingBinding(
			@Qualifier(SysConstants.COLIN_DELAY_FORWARDING_EXCHANGE) TopicExchange exchange,
			@Qualifier(SysConstants.COLIN_DELAY_FORWARDING_QUEUE) Queue queue) {
		return BindingBuilder.bind(queue).to(exchange).with(SysConstants.COLIN_DELAY_FORWARDING_ROUTE_REGIX);
	}

}
