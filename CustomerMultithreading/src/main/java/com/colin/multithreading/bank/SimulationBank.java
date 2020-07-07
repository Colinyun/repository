package com.colin.multithreading.bank;

import java.util.Objects;

import org.junit.jupiter.params.aggregator.ArgumentAccessException;

/**
 * 
 * @auth c-chenyun
 * @date 2020-07-06
 * @describe  模拟统一账户多人取钱
 *
 */
public class SimulationBank {

	/**
	 * 假设一个账户有可用资金1000
	 */
	private static double money = 1000;

	// 柜台取钱
	private void counter(double money) {
		SimulationBank.money -= money;
		System.out.println("柜台取钱" + money + "元，还剩" + SimulationBank.money + "元！");
	}

	// ATM取钱
	private void ATM(double money) {
		SimulationBank.money -= money;
		System.out.println("ATM取钱" + money + "元，还剩" + SimulationBank.money + "元！");
	}

	public synchronized void outMoney(double money, String mode) throws Exception {
		if (money > SimulationBank.money) {
			throw new ArgumentAccessException("取款金额" + money + ",余额只剩" + SimulationBank.money + ",取款失败");
		}

		if (Objects.equals(mode, "ATM")) {
			ATM(money);
		} else
			counter(money);
	}

	public double getMoney() {
		return SimulationBank.money;
	}
}
