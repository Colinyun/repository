package com.colin.multithreading.demo.test;

import java.util.concurrent.CountDownLatch;

import org.junit.Test;

import com.colin.multithreading.bank.Personal;
import com.colin.multithreading.bank.SimulationBank;

public class SimulationBankTest {

	@Test
	public void testWithDrawMoney() throws InterruptedException {
		CountDownLatch countDownLatch = new CountDownLatch(6);
		SimulationBank simulationBank = new SimulationBank();
		for (int i = 0; i < 6; i++) {
			new Thread(new Personal(simulationBank, i % 2 == 0 ? "Countet" : "ATM")).start();
			countDownLatch.countDown();
		}
		countDownLatch.await();
	}
}
