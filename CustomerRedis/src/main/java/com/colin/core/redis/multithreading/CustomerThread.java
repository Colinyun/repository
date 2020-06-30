package com.colin.core.redis.multithreading;

public class CustomerThread {
	
	public void testThreadDemo() throws InterruptedException {
		Runnable a = ()->{
			
		};
		Thread thread = new Thread(a);
		thread.join(10000);
	}
}
