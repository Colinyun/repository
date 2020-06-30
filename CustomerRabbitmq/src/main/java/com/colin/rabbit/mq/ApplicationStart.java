package com.colin.rabbit.mq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 
 * @auth c-chenyun
 * @date 2020-06-29
 * @describe  RabbitMQ
 * 注意点：
 * 	1、 如何保证数据不丢失？
 * 		1、消息持久化，定义数据deliveryMode=2
 * 		2、Confirm消息机制
 * 			· 配置publisher_confirms:true，利用confirmCallback来确认消息是否到达交换机
 * 			· 配置publisher_returns:true，利用returnCallback来确认消息是否到达队列
 *  	3、ACK确认机制
 *  		· 配置acknowledge-mode配置，确定消费的确认机制（不确定：none；自动确认：auto；手动确认：manual）
 *  		     通过配置ACK手动确认机制，来确定消费是否正常消费
 *  	4、消息补偿机制
 *  		· 消息发出之前，将该信息保存到一个数据库中，为该信息添加一个status字段，并设为0
 *  		· 当收到发布确认信息时，将上述的字段设为1
 *  		· 同时对于长时间状态为0的消息进行
 *  2、如何保证消费不重复？
 *  	1、利用数据库的来记录消费
 *  	2、利用redis的setnx来实现数据的重复消费
 */
@SpringBootApplication
public class ApplicationStart {

	public static void main(String[] args) {
		SpringApplication.run(ApplicationStart.class, args);
	}
}
