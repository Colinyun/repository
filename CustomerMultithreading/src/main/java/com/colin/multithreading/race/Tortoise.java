package com.colin.multithreading.race;

import java.util.Optional;

public class Tortoise extends Animal {

	private final int speed = 2;

	@Override
	void running() {
		length -= speed;
		System.out.println("乌龟跑了" + speed + "米，离终点还有" + length + "米");
		if (length <= 0) {
			length = 0;
			System.out.println("乌龟获得了胜利");
			if (Optional.ofNullable(callback).isPresent()) {
				callback.win();
				return;
			}
		}

		try {
			sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
