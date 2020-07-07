package com.colin.multithreading.race;

import com.colin.multithreading.race.Animal.Callback;

public class VectoryRace implements Callback {

	Animal animal;

	public VectoryRace(Animal animal) {
		this.animal = animal;
	}

	@Override
	public void win() {
		animal.interrupt();
	}

}
