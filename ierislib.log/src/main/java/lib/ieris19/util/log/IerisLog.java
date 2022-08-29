package lib.ieris19.util.log;

import lib.ieris19.util.cli.TextColor;

import java.io.File;

/**
 * A class that provides an instantiable object that internally shares an instance of the logger
 */
public class IerisLog {
	/**
	 * Changes the directory of the log files
	 * @param logFile File where the logs should be saved to from now on
	 */
	public synchronized void changeLogDirectory(File logFile) {
		Log.getInstance().changeLogDirectory(logFile);
	}

	/**
	 * Set the name of the application being logged that will be used in the log file
	 * @param name name of the application being used
	 */
	public synchronized void setName(String name) {
		Log.getInstance().setName(name);
	}

	/**
	 * Log a completely custom message by specifying the message, reason and the color to be used
	 *
	 * @param message Description of the event
	 * @param logMessage Nature/Reason of the event
	 * @param color Color to be printed in the console
	 */
	public void customLog(String message, String logMessage, TextColor color) {
		Log.getInstance().log(message, logMessage, color);
	}

	/**
	 * Logs an action message
	 *
	 * @param message message to be logged
	 */
	public void log(String message) {
		Log.getInstance().log(message);
	}

	/**
	 * Logs a success message
	 *
	 * @param message message to be logged
	 */
	public void success(String message) {
		Log.getInstance().success(message);
	}

	/**
	 * Logs an informational message
	 *
	 * @param message message to be logged
	 */
	public void info(String message) {
		Log.getInstance().info(message);
	}

	/**
	 * Logs a warning message
	 *
	 * @param message messaged to be logged
	 */
	public void warning(String message) {
		Log.getInstance().warning(message);
	}

	/**
	 * Logs an error that doesn't interrupt the functioning of the program
	 *
	 * @param message messaged to be logged
	 */
	public void error(String message) {
		Log.getInstance().error(message);
	}

	/**
	 * Logs an error that interrupts the functioning of the program
	 *
	 * @param message messaged to be logged
	 */
	public void fatal(String message) {
		Log.getInstance().fatal(message);
	}
}
