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

package com.ieris19.lib.util.log.ieris;

import com.ieris19.lib.common.cli.TextColor;
import com.ieris19.lib.util.log.Level;
import com.ieris19.lib.util.log.Log;
import com.ieris19.lib.util.log.TimestampHandler;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.locks.ReentrantLock;

import static com.ieris19.lib.util.log.Level.*;

/**
 * A class that provides an instantiable object that internally shares an instance of the logger
 */
public class IerisLog implements Log {
	/**
	 * Map that contains the loggers for each application
	 */
	private static HashMap<String, IerisLog> instances;
	/**
	 * The last instance of the logger that was used. Used to avoid unnecessary calls to {@link #getInstance(String)} when
	 * a library wants to log to the same application as the one that called it
	 */
	private static Log latestUsedInstance;

	/**
	 * Lock used to ensure that a single thread has access to the log file at a time
	 */
	private final ReentrantLock synchronizedLock;

	/**
	 * Sets whether the log should be printed in the console using ANSI escape codes for coloring. Disable if your
	 * terminal does not support ANSI escape codes
	 */
	private boolean enabledANSI;
	/**
	 * Directory of the current log file
	 */
	private File logDirectory;
	/**
	 * Stores the name of the program being logged, if not set, it will default to "Log".
	 */
	private String name;
	/**
	 * Stores the level of alerts that should be printed. This will affect the level of alerts that will be printed in the
	 * console and in the log file
	 */
	private int logLevel;

	/**
	 * Constructor for the log. It will set the log level to {@link Level#INFO}, the name to "Log" and the log
	 * directory to the default new <code>logs/</code> folder in the running directory
	 *
	 * @throws IllegalArgumentException if a file with name "logs" which is not a directory already exists in the running
	 *                                  directory. This is because the log folder will be named "logs" by default, and it
	 *                                  needs to find a file that is a directory. Any extension after "logs" will allow
	 *                                  the log to be created
	 */
	public IerisLog(String appName) throws IllegalArgumentException {
		synchronizedLock = new ReentrantLock(true);
		this.name = appName;
		setLogLevel(INFO.level());
		useANSI(true);
		changeLogDirectory(new File("logs"));
	}

	/**
	 * Returns the singleton instance of the logger
	 *
	 * @return the only instance of the logger that can exist
	 */
	public static synchronized Log getInstance(String appName) {
		if (instances == null) {
			instances = new HashMap<>();
		}
		Log instance = instances.get(appName);
		if (instance == null) {
			instance = new IerisLog(appName);
		}
		latestUsedInstance = instance;
		return latestUsedInstance;
	}

	/**
	 * Used to utilize an existing instance of the logger instead of creating a new one, this is useful for libraries that
	 * want to use the logger but do not want to create a new instance of it
	 *
	 * @return the instance of the last used logger
	 */
	public static synchronized Log getInstance() {
		return latestUsedInstance;
	}

	/**
	 * Changes the directory of the log files
	 *
	 * @param newDirectory File where the logs should be saved to from now on
	 *
	 * @throws IllegalArgumentException if the directory is not a directory or if it is not writable
	 */
	@Override public void changeLogDirectory(File newDirectory) throws IllegalArgumentException {
		synchronizedLock.lock();
		try {
			if (!newDirectory.exists())
				newDirectory.mkdir();
			if (!newDirectory.isDirectory())
				throw new IllegalArgumentException("The file provided is not a directory");
			this.logDirectory = newDirectory;
		} finally {
			synchronizedLock.unlock();
		}
	}

	/**
	 * Set whether the log should print ANSI characters in the console
	 *
	 * @param isEnabled set to true to enable ANSI characters false to disable them
	 */
	@Override public void useANSI(boolean isEnabled) {
		synchronizedLock.lock();
		this.enabledANSI = isEnabled;
		synchronizedLock.unlock();
	}

	/**
	 * Verifies if the ANSI escape codes are enabled or not
	 *
	 * @return true if the ANSI escape codes are enabled, false otherwise
	 */
	@Override public boolean isANSIEnabled() {
		synchronizedLock.lock();
		try {
			return enabledANSI;
		} finally {
			synchronizedLock.unlock();
		}
	}

	/**
	 * Set the level of alerts the logger should print. This will affect the level of alerts that will be printed in the
	 * console and in the log file. Any number lower or higher than 6 will be set to 4 (INFO)
	 *
	 * @param level The level of alerts that should be printed
	 */
	@Override public void setLogLevel(int level) {
		synchronizedLock.lock();
		if (logLevel >= FATAL.level() && logLevel <= TRACE.level())
			this.logLevel = level;
		else
			setLogLevel(INFO.level());
		synchronizedLock.unlock();
	}

	@Override public int getLogLevel() {
		synchronizedLock.lock();
		try {
			return logLevel;
		} finally {
			synchronizedLock.unlock();
		}
	}

	@Override public boolean isLevel(Level severity) {
		return severity.level() <= this.logLevel;
	}

	public String getName() {
		return name;
	}

	/**
	 * Log a completely custom message by specifying the message, reason and the color to be used
	 *
	 * @param message  Description of the event
	 * @param severity Nature/Reason of the event
	 * @param color    Color to be printed in the console
	 */
	@Override public void log(String message, String severity, TextColor color) {
		synchronizedLock.lock();
		Log.super.log(message, severity, color);
		synchronizedLock.unlock();
	}

	/**
	 * Log a custom message by specifying the message, severity and the color to be used
	 *
	 * @param message Description of the event
	 * @param level   {@link Level Level level} of the event
	 * @param color   Color to be printed in the console
	 */
	private void log(String message, Level level, TextColor color) {
		synchronizedLock.lock();
		if (isLevel(level)) {
			Log.super.log(message, level.name(), color);
		}
		synchronizedLock.unlock();
	}

	/**
	 * Logs a trace message
	 *
	 * @param message message to be logged
	 */
	public void trace(String message) {
		log(message, TRACE, TextColor.CYAN);
	}

	/**
	 * Logs a success message
	 *
	 * @param message message to be logged
	 */
	public void success(String message) {
		log(message, SUCCESS, TextColor.GREEN);
	}

	/**
	 * Logs an informational message
	 *
	 * @param message message to be logged
	 */
	public void info(String message) {
		log(message, INFO, TextColor.BLUE);
	}

	/**
	 * Logs a warning message
	 *
	 * @param message messaged to be logged
	 */
	public void warning(String message) {
		log(message, WARNING, TextColor.YELLOW);
	}

	/**
	 * Logs an error that doesn't interrupt the functioning of the program
	 *
	 * @param message messaged to be logged
	 */
	public void error(String message) {
		log(message, ERROR, TextColor.RED);
	}

	/**
	 * Logs an error that interrupts the functioning of the program
	 *
	 * @param message messaged to be logged
	 */
	public void fatal(String message) {
		log(message, FATAL, TextColor.RED);
	}

	/**
	 * Logs a debug message. This message will only be printed if the logger is in debug mode. Use this method to log
	 * messages that are only useful for debugging purposes and should not be printed in production
	 *
	 * @param message messaged to be logged
	 */
	public void debug(String message) {
		log(message, DEBUG, TextColor.WHITE);
	}

	/**
	 * Creates or returns the file where the logged lines will go to. There will be one for each application for each
	 * day.
	 *
	 * @return a Log file with an appropriate filename
	 */
	@Override public File getLogFile() throws IOException {
		String fileName = name + " - " + TimestampHandler.getInstance().getFormattedDate() + ".log";
		File logFile = new File(logDirectory, fileName);
		logFile.createNewFile();
		return logFile;
	}

	/**
	 * Composes a header that will be added in front of all log lines. This header includes the timestamp, the Thread it's
	 * running on and the type of action logged
	 *
	 * @param logType type of action logged
	 *
	 * @return A fully formed header for the line to be logged
	 */
	@Override public String logHeader(String logType) {
		return timestamp() + "[" + Thread.currentThread().getName() + "/" + logType + "] ";
	}

	/**
	 * Logs to a file using a printer, every call to this method will "create" a new file, however, if a previous file
	 * fits the criteria it will then be logged at the bottom of the file
	 *
	 * @param logLine the line to be logged in the file
	 *
	 * @throws IOException when the file doesn't exist cannot be created
	 */
	@Override public void writeToFile(String logLine) throws IOException {
		Log.super.writeToFile(logLine);
	}

	/**
	 * Formats the current time into a timestamp ready to be printed out
	 *
	 * @return fully printed
	 */
	@Override public String timestamp() {
		return Log.super.timestamp();
	}
}
