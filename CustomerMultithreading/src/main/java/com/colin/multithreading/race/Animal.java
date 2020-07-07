package com.colin.multithreading.race;

/**
 * 
 * @auth c-chenyun
 * @date 2020-07-07
 * @describe  龟兔赛跑
 * 要求：
 * 	1、兔子每秒0.1秒5米，每跑20米休息1秒
 *  2、乌龟每0.1秒跑2米，不休息
 *  3、其中一个跑到终点后另一个不跑了
 * 程序涉及思路：
 * 	1、创建一个Animal动物类，集成Runnable，编写一个running抽象方法，重写run方法，把running方法在run方法里面调用
 *  2、创建Rabbit兔子类和Tortoise乌龟类，继承动物类
 *  3、两个字类重写running方法
 *  4、本题得3个要求涉及到线程回归，需要动物类创建一个回调接口，创建一个回调对象
 * 注意点：
 * 	此方法不支持junit测试，请用main方法来测试龟兔赛跑
 */
public abstract class Animal extends Thread {

	int length = 2000;

	@Override
	public void run() {
		super.run();
		while (length > 0) {
			if(Thread.currentThread().isInterrupted()) {
				break;
			}
			running();
		}
	}

	abstract void running();

	public static interface Callback {
		public void win();
	}

	public Callback callback;
}
