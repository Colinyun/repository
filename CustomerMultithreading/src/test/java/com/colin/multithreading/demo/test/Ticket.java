package com.colin.multithreading.demo.test;

import java.util.concurrent.CountDownLatch;

import org.junit.Test;

public class Ticket {

	private static int thread_num = 25;

	/**
	 * 
	 * @throws InterruptedException 
	 * @auth c-chenyun
	 * @date 2020-07-06
	 * @describe  模拟窗口售票
	 * 1、创建25个线程，相当于25窗口开始售票
	 *
	 */
	@Test
	public void simulationTicket() throws InterruptedException {
		CountDownLatch downLatch = new CountDownLatch(thread_num);
		for (int i = 0; i < thread_num; i++) {
			new Thread(new com.colin.multithreading.demo.Ticket("窗口" + i)).start();
			downLatch.countDown();
		}
		downLatch.await();
	}
}
