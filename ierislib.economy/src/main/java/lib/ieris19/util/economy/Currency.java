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

package lib.ieris19.util.economy;

//TODO: Finish implementation of the currency class, as it was never properly implemented

/**
 * A currency is a more specific version of Money, it includes everything from the
 */
public class Currency extends Money {
	/**
	 * The symbol that the balance should be displayed along with, in order to differentiate different currencies
	 */
	protected String currencySymbol;

	/**
	 * Creates a new account that will track money in this particular currency
	 *
	 * @param balance        original balance of the account
	 * @param currencySymbol symbol attached to this currency
	 */
	public Currency(double balance, String currencySymbol) {
		super(balance);
		setCurrencySymbol(currencySymbol);
	}

	/**
	 * Changes the currency symbol
	 *
	 * @param currencySymbol The string that will be used as a currency symbol
	 */
	public void setCurrencySymbol(String currencySymbol) {
		this.currencySymbol = currencySymbol;
	}

	/**
	 * Returns the currency symbol
	 */
	public String getCurrencySymbol() {
		return currencySymbol;
	}
}
