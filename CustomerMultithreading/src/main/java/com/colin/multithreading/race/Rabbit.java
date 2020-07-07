package com.colin.multithreading.race;

import java.util.Optional;

public class Rabbit extends Animal {
	private final int speed = 5;

	@Override
	void running(){
		length -= speed;
		System.out.println("兔子跑了" + speed + "米，离终点还有" + length + "米");
		if (length <= 0) {
			length = 0;
			System.out.println("兔子获得了胜利");
			if (Optional.ofNullable(callback).isPresent()) {
				callback.win();
				return;
			}
		}
		try {
			sleep((2000 - length) % 20 == 0 ? 1000 : 100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
