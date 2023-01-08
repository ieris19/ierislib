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

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

/**
 * A class that can return the current date and time appropriately formatted for the log console
 */
public class TimestampHandler implements AutoCloseable {
	/**
	 * Singleton instance of the timestamp handler
	 */
	private static HashMap<TimeFormatter, TimestampHandler> instances;
	private TimeFormatter defaultFormatter;

	/**
	 * Private constructor to avoid accidentally creating multiple instances of {@link TimestampHandler}. Please refer to
	 * {@link #getInstance(TimeFormatter)} for more information
	 */
	private TimestampHandler(TimeFormatter defaultFormatter) {
		this.defaultFormatter = defaultFormatter;
	}

	/**
	 * Method to obtain the {@link TimestampHandler} instance, it will only create a new instance on its first call for
	 * each {@link TimeFormatter} and return the already created instance every call after that. This means there will
	 * always be one single instance of the <code>TimestampHandler</code> class for each {@link TimeFormatter}
	 *
	 * @return The instance of <code>TimestampHandler</code> for the given {@link TimeFormatter}, regardless of if it's
	 * new, or it had been previously created
	 */
	public synchronized static TimestampHandler getInstance(TimeFormatter formatter) {
		TimestampHandler instance = instances.get(formatter);
		if (instance == null) {
			instance = new TimestampHandler(formatter);
			instances.put(formatter, instance);
		}
		return instance;
	}

	/**
	 * Returns a formatted date and/or time string according to the pattern in the parameter object
	 *
	 * @param formatter Pattern for the String's format
	 *
	 * @return A formatted string
	 */
	public String getFormatted() {
		return getFormatted(defaultFormatter);
	}

	/**
	 * Returns a formatted date and/or time string according to the pattern in the parameter object
	 *
	 * @param formatter {@link DateTimeFormatter} for the String's format
	 *
	 * @return A formatted string
	 */
	public String getFormatted(TimeFormatter formatter) {
		return getTime().format(formatter.get());
	}

	/**
	 * Gets the current UTC time using the system clock
	 *
	 * @return UTC date and time at the moment of the call
	 */
	public LocalDateTime getTime() {
		return LocalDateTime.now(Clock.systemUTC());
	}

	@Override public void close() {
		instances.remove(defaultFormatter);
		this.defaultFormatter = null;
	}
}
