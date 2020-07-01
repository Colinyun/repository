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
 * 			1、加锁排队
 * 				mutex互斥锁解决，Redis的setNx一个mutex key，当操作返回成功时，在进行加载数据库的操作并回社缓存，否则，就重试是整个get缓存的方法
 *			2、数据预热
 *				缓存预热就是系统上线后，将相关的缓存数据直接加载到缓存系统。这样就可以避免在用户请求的时候，先查数据库，然后再将数据缓存的问题。
 *				用户直接查询事先被预热的缓存数据，可以通过缓存的reload机制，预先去更新缓存，在即将发生大并发访问前手动触发加载缓存的不同key
 *			3、双层缓存策略
 *				C1为原始缓存，C2为拷贝缓存，C1失效时，可以访问C2，C1缓存失效时间设置为短期，C2设置为长期
 *			4、定时更新缓存策略
 *				实效性要求不高的缓存，容器启动初始化加载，采用定时任务更新或溢出缓存
 *			5、设置不同的过期时间，让缓存失效的时间点尽量均匀
 */
@SpringBootApplication
public class ApplicationStart {

	public static void main(String[] args) {
		SpringApplication.run(ApplicationStart.class, args);
	}
}
