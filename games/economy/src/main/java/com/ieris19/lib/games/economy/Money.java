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

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * A class that implements the most basic aspects of Money, an account for storing a money balance between negative one
 * billion and positive one billion.
 *
 * It also contains a currency String for aesthetic purposes
 */
public class Money {

	/**
	 * The amount of money in this instance, it is declared volatile to insure the visibility of this variable across
	 * multiple threads
	 */
	protected volatile double amount;

	/**
	 * Constant containing the maximum amount of money allowed in the system, changing this will change it everywhere in
	 * the program. It is arbitrarily one billion
	 */
	public final static double MAX_MONEY = 1000000000;

	/**
	 * Constant containing the maximum amount of debt allowed in the system, changing this will change it everywhere in
	 * the program. It is arbitrarily one billion
	 */
	public final static double MIN_MONEY = -1000000000;

	/**
	 * Support for objects listening to this object property changes
	 */
	private PropertyChangeSupport support;

	/**
	 * Creates an instance with default values
	 */
	public Money() {
		this(0);
	}

	/**
	 * Creates an instance with a certain amount of money and sets the currency to the default
	 *
	 * @param balance The amount of money to start with
	 */
	public Money(double balance) {
		this.amount = fix(balance);
		this.support = new PropertyChangeSupport(this);
	}

	/**
	 * Checks the validity of the amount and changes the current balance to the new one
	 *
	 * @param value the balance that will replace the current one
	 *
	 * @throws IllegalArgumentException if the value is not between the maximum and minimum allowed
	 */
	public void setAmount(double value) throws IllegalArgumentException {
		value = fix(value);
		double oldValue = this.amount;
		if (value <= MAX_MONEY && value >= MIN_MONEY) {
			this.amount = value;
		} else {
			throw new IllegalArgumentException("Balance Exceeds Limits");
		}
		support.firePropertyChange("Amount of Money", oldValue, getAmount());
	}

	/**
	 * The current balance of this instance
	 *
	 * @return The fixed double precision floating point number representing the balance of this instance after the last
	 * operation
	 */
	public double getAmount() {
		return amount;
	}

	/**
	 * Adds the desired amount of money, it has to be a positive number
	 *
	 * @param addend the amount of money that will be added
	 *
	 * @throws IllegalArgumentException if the value is negative or would be rounded to zero
	 */
	public void addMoney(double addend) throws IllegalArgumentException {
		addend = fix(addend);
		double oldValue = this.amount;
		if (addend >= 0.01) {
			this.amount = Math.min(this.amount + addend, MAX_MONEY);
		} else {
			throw new IllegalArgumentException("Amount to add can't be negative or too small");
		}
		support.firePropertyChange("Amount of Money", oldValue, getAmount());
	}

	/**
	 * Subtracts the desired amount of money, it has to be a positive number
	 *
	 * @param subtrahend the amount of money that will be subtracted
	 * @param canOwe     Sets whether going into negative values should be allowed or if a lower limit of 0 should be
	 *                   enforced
	 *
	 * @throws IllegalArgumentException if the value is negative or would be rounded to zero
	 */
	public void subtractMoney(double subtrahend, boolean canOwe) throws IllegalArgumentException {
		subtrahend = fix(subtrahend);
		double oldValue = this.amount;
		if (subtrahend < 0.01)
			throw new IllegalArgumentException("Amount to subtract can't be negative or too small");
		if (!canOwe) {
			if (this.amount - subtrahend >= 0)
				this.amount = this.amount - subtrahend;
			else
				throw new IllegalArgumentException("Insufficient funds");
		} else {
			this.amount = Math.max(this.amount - subtrahend, MIN_MONEY);
		}
		support.firePropertyChange("Amount of Money", oldValue, getAmount());
	}

	/**
	 * Removes the desired amount from the sender and adds it to the recipient
	 *
	 * @param sender    that will have the amount subtracted
	 * @param recipient that will have the amount added
	 * @param amount    the desired amount to transfer
	 */
	public static void transfer(Money sender, Money recipient, double amount) {
		amount = fix(amount);
		sender.subtractMoney(amount, false);
		recipient.addMoney(amount);
	}

	/**
	 * "Fixes" the value of the balance to round it to 2 decimal places
	 *
	 * @param amount the value to be fixed
	 *
	 * @return the fixed value
	 */
	private static double fix(double amount) {
		BigDecimal formatter = BigDecimal.valueOf(amount);
		return formatter.setScale(2, RoundingMode.HALF_UP).doubleValue();
	}

	/**
	 * Indicates whether some other object is "equal to" this one.
	 *
	 * @param obj the reference object with which to compare.
	 *
	 * @return {@code true} if this object is the same as the obj argument; {@code false} otherwise.
	 */
	@Override public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		Money other = (Money) obj;
		return this.amount == other.amount;
	}

	/**
	 * Returns a string representation of the object.
	 *
	 * @return a string representation of the object.
	 */
	@Override public String toString() {
		String temp = String.valueOf(getAmount());
		boolean formatted;
		do {
			char last = temp.charAt(temp.length() - 1);
			if (last == '0') {
				temp = temp.substring(0, temp.length() - 1);
				formatted = false;
			} else if (last == '.') {
				temp = temp.substring(0, temp.length() - 1);
				formatted = true;
			} else
				formatted = true;
		} while (formatted);
		return temp;
	}

	/**
	 * Adds a listener to the properties of this class
	 *
	 * @param listener object that will listen to the properties of this class
	 */
	public void addListener(PropertyChangeListener listener) {
		this.support.addPropertyChangeListener(listener);
	}

	/**
	 * Removes an object that was listening to this class
	 *
	 * @param listener object that will no longer listen to the values of this class
	 */
	public void removeListener(PropertyChangeListener listener) {
		this.support.removePropertyChangeListener(listener);
	}
}
