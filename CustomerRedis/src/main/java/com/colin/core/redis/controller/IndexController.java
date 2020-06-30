package com.colin.core.redis.controller;

import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.colin.core.redis.model.Responsebody;

import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.params.SetParams;

@RestController
@RequestMapping("/")
public class IndexController {

	@Autowired
	private JedisCluster jedisCluster;

	@GetMapping("/index.html")
	public Responsebody index() throws InterruptedException, ExecutionException {
		jedisCluster.del("name:1");
		CountDownLatch downLatch = new CountDownLatch(10);
		for (int i = 0; i < 10; i++) {
			Runnable a = () -> {
				SetParams params = SetParams.setParams().nx().px(10000);
				String value = jedisCluster.set("name:1", "张三", params);
				if ("OK".equals(value)) {
					System.out.println("线程：" + Thread.currentThread().getName() + "返回值：" + value);
				} else {
					 value = jedisCluster.set("name:1", "张三", params);
					 System.out.println("线程：" + Thread.currentThread().getName() + "返回值：" + value);
				}

				downLatch.countDown();
			};
			new Thread(a).start();
		}
		downLatch.await();
		return Responsebody.success(jedisCluster.get("name:1"));
	}

}
