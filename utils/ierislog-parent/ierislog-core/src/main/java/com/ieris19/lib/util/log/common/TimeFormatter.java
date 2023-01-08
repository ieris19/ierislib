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

package com.ieris19.lib.util.log.common;

import java.time.format.DateTimeFormatter;

/**
 * Enumeration containing the {@link DateTimeFormatter} to be used in timestamping
 */
public class TimeFormatter {
	/**
	 * Formatter for the European standard of Date and Time (dd/MM/yyyy HH:mm:ss)
	 */
	public static TimeFormatter EUROPEAN = new TimeFormatter("dd/MM/yyyy HH:mm:ss");
	/**
	 * Formatter for the ISO standard of Date and Time (yyyy-MM-dd'T'HH:mm:ss)
	 */
	public static TimeFormatter ISO = new TimeFormatter("yyyy-MM-dd'T'HH:mm:ss");
	/**
	 * Formatter for the ISO standard of Date only (yyyy-MM-dd)
	 */
	public static TimeFormatter DATE_ONLY = new TimeFormatter("yyyy-MM-dd");
	/**
	 * Formatter for the ISO standard of Time only (HH:mm:ss)
	 */
	public static TimeFormatter TIME_ONLY = new TimeFormatter("HH:mm:ss");

	/**
	 * The time formatter for this specific {@link TimeFormatter TimeFormatter}
	 */
	private final DateTimeFormatter formatter;

	/**
	 * Constructs a formatter from the provided pattern
	 *
	 * @param pattern the Pattern for the {@link DateTimeFormatter}
	 */
	private TimeFormatter(String pattern) {
		this.formatter = DateTimeFormatter.ofPattern(pattern);
	}

	/**
	 * The formatter corresponding to the selected {@link Enum#name()}
	 *
	 * @return {@link DateTimeFormatter} of the pattern corresponding to the instance's name
	 */
	public DateTimeFormatter get() {
		return this.formatter;
	}

	public static TimeFormatter custom(String pattern) {
		return new TimeFormatter(pattern);
	}
}