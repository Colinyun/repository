package com.colin.multithreading.demo;

/**
 * 
 * @auth c-chenyun
 * @date 2020-07-06
 * @describe  模拟窗口售票
 * 1、创建密钥，用来保证票数一致，票数要静态得
 *
 */
public class Ticket implements Runnable {

	private String name;

	private static int ticket = 20;

	private final static Object SECRET_KEY = "colin_secret_key";

	public Ticket(String name) {
		this.name = name;
	}

	@Override
	public void run() {
		while (ticket > 0) {
			synchronized (SECRET_KEY) {
				if (ticket > 0) {
					System.out.println(name + "：卖出了第" + ticket + "张票");
					ticket--;
				} else {
					System.out.println(name + "：票已售完！");
				}
			}

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
