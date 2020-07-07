package com.colin.multithreading.bank;

public class Personal implements Runnable {

	private final SimulationBank bank;

	private final String mode;

	public Personal(SimulationBank bank, String mode) {
		this.bank = bank;
		this.mode = mode;
	}

	@Override
	public void run() {
		while (bank.getMoney() >= 200) {
			try {
				bank.outMoney(200, mode);
				Thread.sleep(100);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
