package com.colin.multithreading.race;

import org.junit.Test;

public class TortoiseTest {

	@Test
	public void race() throws InterruptedException {
		Rabbit rabbit = new Rabbit();
		Tortoise tortoise = new Tortoise();
		rabbit.callback = new VectoryRace(rabbit);
		tortoise.callback = new VectoryRace(tortoise);
		rabbit.start();
		tortoise.start();
	}
}
