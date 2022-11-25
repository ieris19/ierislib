/*
 * Copyright 2021 Ieris19
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 *
 */

package com.ieris19.lib.games.economy;

import com.ieris19.lib.games.economy.Money;

public class Manual {

	public static void main(String[] args) {
		testPrecision();
		basicTesting();
		testSet();
		System.out.println(5D);
		Money money = new Money(50.50);
		System.out.println(money.getAmount());
		System.out.println(money);
		money.subtractMoney(0.50, true);
		System.out.println(money.getAmount());
		System.out.println(money);
	}

	public static void testSet() {
		Money wallet = new Money(5);
		System.out.println(wallet);
		wallet.setAmount(5.4);
		System.out.println(wallet);
		wallet.setAmount(5.00521);
		System.out.println(wallet);
		System.out.println("True value: " + wallet.getAmount());
	}

	public static void testPrecision() {
		Money account = new Money(0);
		for (int i = 0; i < 10000; i++) {
			System.out.println(account.getAmount());
			account.addMoney(0.2545);
		}
	}

	public static void basicTesting() {
		Money wallet = new Money(100);
		Money account = new Money(300);
		System.out.println("Account: " + account);
		System.out.println("Wallet: " + wallet);
		Money.transfer(wallet, account, 50.2512);
		System.out.println(wallet);
		System.out.println(account);
		wallet.addMoney(100);
		System.out.println(wallet);
		System.out.println(account);
	}
}
/*
	System.out.println(wallet);
			try {
				wallet.setBalance(Integer.MAX_VALUE);
			} catch (Exception exception) {
				exception.printStackTrace(System.err);
			}
			System.out.println("Why is the stack trace later?");
			int max = Integer.MAX_VALUE;
			max = max + 1000000000;
			System.out.println(max);
*/