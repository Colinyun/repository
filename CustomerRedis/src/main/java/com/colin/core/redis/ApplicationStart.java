package com.colin.core.redis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 
 * @auth c-chenyun
 * @date 2020-06-30
 * @describe  redis的集群及相关特点
 * 1、如何使用redis的分布式锁
 *		· 可用redis的setNx来实现锁机制，我们可以通过setNx某资源代表占用，用完后删除则以来处理资源的同步使用
 * 
 *
 */
@SpringBootApplication
public class ApplicationStart {

	public static void main(String[] args) {
		SpringApplication.run(ApplicationStart.class, args);
	}
}
