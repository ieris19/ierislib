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

package lib.ieris19.util.log;

/**
 * An enum that contains all the possible severity levels for a log message
 */
public enum Severity {
	/**
	 * Fatal error that interrupts the functioning of the program
	 */
	FATAL(0),
	/**
	 * Error that doesn't interrupt the functioning of the program
	 */
	ERROR(1),
	/**
	 * Warning that something went wrong but doesn't affect the functioning of the program
	 */
	WARNING(2),
	/**
	 * Success message that indicates that something went right
	 */
	SUCCESS(3),
	/**
	 * Informational message that indicates something that happened
	 */
	INFO(4),
	/**
	 * Debug message that indicates actions that are being performed step by step
	 */
	DEBUG(5),
	/**
	 * Trace message that indicates the flow of the program
	 */
	TRACE(6);

	/**
	 * The level of the severity
	 */
	private int logLevel;

	/**
	 * Creates a new Severity level with the specified index
	 *
	 * @param logLevel The index of the severity as an integer
	 */
	Severity(int logLevel) {
		this.logLevel = logLevel;
	}

	/**
	 * Returns the severity as an integer for comparison purposes
	 *
	 * @return The severity level as an integer
	 */
	public int level() {
		return logLevel;
	}
}
