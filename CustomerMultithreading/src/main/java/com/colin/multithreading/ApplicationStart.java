package com.colin.multithreading;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

import com.colin.multithreading.race1.Rabbit1;
import com.colin.multithreading.race1.Turtoise1;

/**
 * 
 * @auth c-chenyun
 * @date 2020-07-06
 * @describe  此项目乃是多线程项目
 *
 */
public class ApplicationStart {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
//		Rabbit rabbit = new Rabbit();
//		Tortoise tortoise = new Tortoise();
//		rabbit.callback = new VectoryRace(rabbit);
//		tortoise.callback = new VectoryRace(tortoise);
//		rabbit.start();
//		tortoise.start();

		Rabbit1 rabbit1 = new Rabbit1();
		Turtoise1 tortoise1 = new Turtoise1();
		FutureTask<Boolean> ft1 = new FutureTask<Boolean>(rabbit1);
		FutureTask<Boolean> ft2 = new FutureTask<Boolean>(tortoise1);
		
		Thread thread = new Thread(ft1);
		Thread thread2 = new Thread(ft2);
		thread.start();
		thread2.start();
		
		if(ft1.get() || ft2.get()) {
			thread.interrupt();
			thread2.interrupt();
		}
	}
}
