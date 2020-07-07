package com.colin.multithreading.race1;

public class Turtoise1 extends AbstractAnimal {

	private final int speed = 2;

	@Override
	void running() {
		runwayLength -= speed;
		System.out.println("乌龟跑了" + (500 - runwayLength) + "米，距终点还有" + runwayLength + "米");
		if (runwayLength <= 0) {
			runwayLength = 0;
			return;
		}

		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
