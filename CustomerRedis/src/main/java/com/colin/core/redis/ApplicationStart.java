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
 * 2、如何避免redis的缓存击穿
 * 		· 通过布隆拦截器拦截过来的key，把一些热门或者进场用到的key放在布隆拦截器中，当请求过来的key通过布隆拦截器拦截时，说明key不存在，那么我们就不然访问数据库或者redis，当key存在时
 * 		     我们可以放行，让你进行数据库访问
 * 		· 对Db到的空数据进行短暂的缓存，不然仍然会有大量的请求去请求DB
 * 3、如果避免redis雪崩？
 * 		· 所谓的redis雪崩，就是大量的热key在同一段时间内失效，导致请求都去DB服务器请求。
 * 		     为了避免数据雪崩的情况：
 * 			1、可以通过设置过期时间加时间戳来实现不同时间的过期
 * 			2、给某些key设置永久不过期性
 *
 */
@SpringBootApplication
public class ApplicationStart {

	public static void main(String[] args) {
		SpringApplication.run(ApplicationStart.class, args);
	}
}
