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

package com.ieris19.lib.math.archimedes.base;

/**
 * A class that helps with binary numbers and operations
 */
public class Binary {
	/**
	 * Private constructor to prevent instantiation
	 */
	private Binary() {
	}

	/**
	 * Converts a binary number to a decimal number
	 *
	 * @param n the binary number to convert
	 * @return the decimal number
	 */
	public static String printBinary(int n) {
		return Integer.toBinaryString(n);
	}

	/**
	 * Converts a decimal number to a binary number
	 * @param n the decimal number to convert
	 * @return the binary number
	 */
	public static String convertDecimal(int n) {
		StringBuilder binaryNum = new StringBuilder();
		if (n < 0)
			binaryNum.append("-");
		else
			binaryNum.append("+");
		while (n > 0) {
			binaryNum.insert(1, n % 2);
			n = n / 2;
		}
		return binaryNum.toString();
	}

	/**
	 * Converts a binary number to a decimal number
	 *
	 * @param n the binary number to convert
	 * @return the decimal number
	 */
	public static int to10(int n) {
		return n;
	}
}
