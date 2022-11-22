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

package com.ieris19.lib.util.log;

import com.ieris19.lib.common.cli.TextColor;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * A class that can create a registry of messages both in the console and in a separate file concerning the functioning
 * of an application. Information contained in these messages includes date (through the named files created for all
 * logged activity during a day), time (as a timestamp before the rest of the message), execution thread (meaning, the
 * thread executing the action being logged), type of message (ACTION, SUCCESS, INFO, etc..) and a custom message to
 * better provide information
 */
public interface Log {
	/**
	 * Changes the directory of the log files
	 *
	 * @param newDirectory File where the logs should be saved to from now on
	 */
	void changeLogDirectory(File newDirectory);

	/**
	 * Set whether the console print should use ANSI escape codes for coloring or print without any sort of formatting
	 *
	 * @param isEnabled whether the ANSI escape codes should be used or not
	 */
	void useANSI(boolean isEnabled);
	/**
	 * Verifies if the ANSI escape codes are enabled or not
	 *
	 * @return true if the ANSI escape codes are enabled, false otherwise
	 */
	boolean isANSIEnabled();

	/**
	 * Sets the log level of the log. The log level is a number that determines the severity of the messages that will be
	 * logged. The level of the log messages is defined by the {@link Level} class. The log level needs to be set to an
	 * integer value that is equal to or greater than the level of the messages that should be logged. While you can set
	 * the log level to an integer value between 0 and 6, it is recommended to use the values defined in the
	 * {@link Level} class.
	 *
	 * @param logLevel the new log level
	 */
	void setLogLevel(int logLevel);

	/**
	 * Gets the current log level
	 * @return a number between 0 and 6 that represents the current log level
	 */
	int getLogLevel();
	boolean isLevel(Level severity);
	/**
	 * Logs a custom message into a file and prints to the console in the selected color. It will mark the category
	 *
	 * @param message  message to be logged
	 * @param severity type of element being logged, it can be SUCCESS, ERROR...
	 * @param color    TextColor object corresponding to the desired
	 */
	default void log(String message, String severity, TextColor color) {
		String line = logHeader(severity) + message;
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
	 * Logs an informational message
	 *
	 * @param message message to be logged
	 */
	void info(String message);

	/**
	 * Logs a generic message
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
	 * Logs a warning
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
	 * Logs a debug message. This message will only be printed if the logger is in debug mode. Use this method to log
	 * messages that are only useful for debugging purposes and should not be printed in production
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

	/**
	 * Formats the current time into a timestamp ready to be printed out
	 *
	 * @return fully printed
	 */
	default String timestamp() {
		return "[" + TimestampHandler.getInstance().getFormattedTime() + "] ";
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
}
