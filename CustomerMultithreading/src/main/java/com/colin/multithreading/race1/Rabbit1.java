package com.colin.multithreading.race1;

public class Rabbit1 extends AbstractAnimal {

	private final int speed = 5;

	@Override
	void running() {
		runwayLength -= speed;
		System.out.println("兔子跑了" + (500 - runwayLength) + "米，距终点还有" + runwayLength + "米");
		if (runwayLength <= 0) {
			runwayLength = 0;
			return;
		}

		try {
			Thread.sleep((500 - runwayLength) % 10 == 0 ? 1000 : 100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
