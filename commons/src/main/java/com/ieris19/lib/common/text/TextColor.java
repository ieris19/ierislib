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

package com.ieris19.lib.common.text;

import java.io.PrintStream;

/**
 * Enum containing the ANSI character strings that format the color of the logs to the console
 */
public enum TextColor {
	/**
	 * ANSI code that changes the color back to default
	 */
	RESET("\u001B[0m"),
	/**
	 * ANSI code that changes the text color to red
	 */
	RED("\u001B[31m"),
	/**
	 * ANSI code that changes text color to green
	 */
	GREEN("\u001B[32m"),
	/**
	 * ANSI code that changes text color to yellow
	 */
	YELLOW("\u001B[33m"),
	/**
	 * ANSI code that changes text color to blue
	 */
	BLUE("\u001B[34m"),
	/**
	 * ANSI code that changes text color to magenta
	 */
	MAGENTA("\u001b[35m"),
	/**
	 * ANSI code that changes text color to cyan
	 */
	CYAN("\u001b[36m"),
	/**
	 * ANSI code that changes text color to white
	 */
	WHITE("\u001b[37m"),
	/**
	 * ANSI code that changes text color to black
	 */
	BLACK("\u001b[30m");

	/**
	 * The ANSI code corresponding to the text color {@link Enum#name() name}
	 */
	private final String ANSICode;

	/**
	 * Constructs a text color object by identifying through a human-readable color {@link Enum#name() name} to the
	 * corresponding ANSI code that will format text in the desired color
	 *
	 * @param code the ANSI code corresponding to the <code>name</code>
	 */
	TextColor(String code) {
		this.ANSICode = code;
	}

	/**
	 * A {@link TextColor TextColor} turned string corresponds to the ANSI code. Thus, the method
	 * <code>toString()</code> is overriding in order to return the ANSI code instead of the
	 * {@link Enum#name() name} of the color
	 *
	 * @return the ANSI code of this text color
	 */
	@Override public String toString() {
		return ANSICode;
	}

	/**
	 * Appends the ANSI code required to color the provided string, many shells will interpret this by coloring
	 * the text, many will ignore it, and many will simply add the code as if it was plain-text.
	 *
	 * @param string Text to be colored
	 * @param color  Desired Text color
	 *
	 * @return the colored string
	 */
	public static String format(String string, TextColor color) {
		return color + string + RESET;
	}

	public static String clear(String string) {
		return string.replaceAll("\\u001b\\[\\d{1,2}m", "");
	}
}
