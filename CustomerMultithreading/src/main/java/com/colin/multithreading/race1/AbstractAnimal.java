package com.colin.multithreading.race1;

import java.util.concurrent.Callable;

public abstract class AbstractAnimal implements Callable<Boolean> {

	int runwayLength = 500;

	@Override
	public Boolean call() throws Exception {
		while (runwayLength > 0) {
			if(Thread.currentThread().isInterrupted()) {
				break;
			}
			running();
		}
		return Boolean.valueOf(true);
	}

	abstract void running();
}
