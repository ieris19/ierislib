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

package com.ieris19.lib.util.log.core;

import com.ieris19.lib.common.cli.TextColor;
import com.ieris19.lib.util.log.common.Level;
import com.ieris19.lib.util.log.common.TimeFormatter;
import com.ieris19.lib.util.log.common.TimestampHandler;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * An interface that provides all the public operations that Ierislog is capable of.
 * <p>
 * Additionally, it provides two default methods implementing the printing of messages, so that any class
 * implementing this interface can make sure it is following the exact same procedure to print messages.
 * Classes can override this method, or preprocess its parameters before calling this implementation.
 * </p>
 */
public interface IerisLogger {
	/**
	 * Log a custom message by specifying the message, severity and the color to be used
	 *
	 * @param message Description of the event
	 * @param severity   {@link Level Level level} of the event
	 * @param color   Color to be printed in the console
	 */
	default void log(String message, Level severity, TextColor color) {
		String line = logHeader(severity.name()) + message;
		if (isANSIEnabled())
			TextColor.println(line, color);
		else
			System.out.println(line);
		try {
			this.writeToFile(line);
		} catch (IOException e) {
			System.err.println("Failed to writeToFile to file");
		}
	}

	/**
	 * Formats the current time into a timestamp ready to be printed out
	 *
	 * @return fully printed
	 */
	default String timestamp() {
		return "[" + TimestampHandler.getInstance(TimeFormatter.TIME_ONLY).getFormatted() + "] ";
	}

	/**
	 * Logs to a file using a printer, every call to this method will "create" a new file, however, if a previous file
	 * fits the criteria it will then be logged at the bottom of the file
	 *
	 * @param logLine the line to be logged in the file
	 *
	 * @throws IOException when the file doesn't exist cannot be created
	 */
	default void writeToFile(String logLine) throws IOException {
		try (PrintWriter writer = new PrintWriter(new FileWriter(getLogFile(), true))) {
			writer.println(logLine);
			writer.flush();
		}
	}

	/**
	 * Changes the directory of the log files
	 *
	 * @param newDirectory File where the logs should be saved to from now on
	 *
	 * @throws IllegalArgumentException if the directory is not a directory or if it is not writable
	 */
	void changeLogDirectory(File newDirectory) throws IllegalArgumentException;

	/**
	 * Set whether the log should print ANSI characters in the console
	 *
	 * @param isEnabled set to true to enable ANSI characters false to disable them
	 */
	void useANSI(boolean isEnabled);

	/**
	 * Verifies if the ANSI escape codes are enabled or not
	 *
	 * @return true if the ANSI escape codes are enabled, false otherwise
	 */
	boolean isANSIEnabled();

	/**
	 * Sets the level of alerts that should be printed. This will affect the level of alerts that will be printed in the
	 * console and in the log file
	 *
	 * @param level the level of alerts that should be printed
	 */
	void setLogLevel(Level level);

	/**
	 * Set the level of alerts the logger should print. This will affect the level of alerts that will be printed in the
	 * console and in the log file. Any number lower or higher than 6 will be set to 4 (INFO)
	 *
	 * @param level The level of alerts that should be printed
	 */
 	void setLogLevel(int level);

	int getLogLevel();

	/**
	 * Verifies if the log level is set to print the specified level of alerts
	 *
	 * @param severity the level of alert to verify
	 *
	 * @return true if the log level is set to print the specified level of alerts, false otherwise
	 */
	boolean isLevel(Level severity);

	/**
	 * A method to access the name of the program being logged
	 *
	 * @return the name of the program being logged
	 */
	String getName();

	/**
	 * Logs a trace message
	 *
	 * @param message message to be logged
	 */
	void trace(String message);

	/**
	 * Logs a success message
	 *
	 * @param message message to be logged
	 */
	void success(String message);

	/**
	 * Logs an informational message
	 *
	 * @param message message to be logged
	 */
	void info(String message);

	/**
	 * Logs a warning message
	 *
	 * @param message messaged to be logged
	 */
	void warning(String message);

	/**
	 * Logs an error that doesn't interrupt the functioning of the program
	 *
	 * @param message messaged to be logged
	 */
	void error(String message);

	/**
	 * Logs an error that interrupts the functioning of the program
	 *
	 * @param message messaged to be logged
	 */
	void fatal(String message);

	/**
	 * Logs a debug message.
	 *
	 * @param message messaged to be logged
	 */
	void debug(String message);

	/**
	 * Creates or returns the file where the logged lines will go to. There will be one for each application for each
	 * day.
	 *
	 * @return a Log file with an appropriate filename
	 */
	File getLogFile() throws IOException;

	/**
	 * Composes a header that will be added in front of all log lines. This header includes the timestamp, the Thread it's
	 * running on and the type of action logged
	 *
	 * @param logType type of action logged
	 *
	 * @return A fully formed header for the line to be logged
	 */
	String logHeader(String logType);
}
