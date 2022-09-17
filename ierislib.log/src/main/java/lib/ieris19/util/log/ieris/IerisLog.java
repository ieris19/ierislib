package lib.ieris19.util.log.ieris;

import lib.ieris19.util.cli.TextColor;
import lib.ieris19.util.log.Log;
import lib.ieris19.util.log.LogLevel;
import lib.ieris19.util.log.TimestampHandler;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.locks.ReentrantLock;

import static lib.ieris19.util.log.LogLevel.*;

/**
 * A class that provides an instantiable object that internally shares an instance of the logger
 */
public class IerisLog implements Log {
	/**
	 * Map that contains the loggers for each application
	 */
	private static HashMap<String, IerisLog> instances;

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
	 * Constructor for the log. It will set the log level to {@link LogLevel#INFO}, the name to "Log" and the log
	 * directory to the default new <code>logs/</code> folder in the running directory
	 */
	public IerisLog(String appName) {
		synchronizedLock = new ReentrantLock();
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
		IerisLog instance = instances.get(appName);
		if (instance == null) {
			instance = new IerisLog(appName);
			instances.put(appName, instance);
		}
		return instance;
	}

	/**
	 * Changes the directory of the log files
	 *
	 * @param newDirectory File where the logs should be saved to from now on
	 */
	@Override public void changeLogDirectory(File newDirectory) {
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
		try {
			synchronizedLock.lock();
			return enabledANSI;
		} finally {
			synchronizedLock.unlock();
		}
	}

	/**
	 * Set the level of alerts the logger should print. This will affect the level of alerts that will be printed in the
	 * console and in the log file
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

	/**
	 * Log a completely custom message by specifying the message, reason and the color to be used
	 *
	 * @param message    Description of the event
	 * @param logMessage Nature/Reason of the event
	 * @param color      Color to be printed in the console
	 */
	@Override public void log(String message, String logMessage, TextColor color) {
		synchronizedLock.lock();
		Log.super.log(message, logMessage, color);
		synchronizedLock.unlock();
	}

	private void log(String message, LogLevel level, TextColor color) {
		synchronizedLock.lock();
		if (level.level() <= this.logLevel) {
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
		return Log.super.logHeader(logType);
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
