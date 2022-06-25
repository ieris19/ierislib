package lib.ieris19.util.log;

import lib.ieris19.util.cli.TextColor;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import static lib.ieris19.util.cli.TextColor.*;

/**
 * A class that can create a registry of messages both in the console and in a separate file
 * concerning the functioning of an application. Information contained in these messages includes
 * date (through the named files created for all logged activity during a day), time (as a timestamp
 * before the rest of the message), execution thread (meaning, the thread executing the action being
 * logged), type of message (ACTION, SUCCESS, INFO, etc..) and a custom message to better provide
 * information
 */
public class Log {
	/**
	 * Singleton instance of the log object to be referenced
	 */
	private static Log log;

	/**
	 * Timestamp handler for the log
	 */
	private final TimestampHandler timestamp;
	/**
	 * Directory of the current log file
	 */
	private File logDirectory;

	/**
	 * Stores the name of the program being logged, if not set, it will default to "Log".
	 */
	private String name;

	/**
	 * Private constructor to avoid accidentally creating multiple instances of
	 * {@link Log}. Please refer to {@link #getInstance()} for more information
	 */
	private Log() {
		logDirectory = new File("logs");
		logDirectory.mkdir();
		timestamp = TimestampHandler.getInstance();
		name = "Log";
	}

	/**
	 * Returns the only instance of the logger that can exist during runtime. The first time it's
	 * called, it will create said instance, but any subsequent call will return the existing
	 * instance
	 *
	 * @return The only existing instance of this class
	 */
	public synchronized static Log getInstance() {
		if (log == null) {
			log = new Log();
		}
		return log;
	}

	/**
	 * Sets the log folder to the provided one
	 *
	 * @param logFile the new file where the log will go to
	 */
	public synchronized void changeLogDirectory(File logFile) {
		this.logDirectory = logFile;
	}

	/**
	 * Sets the name of the program running the log
	 *
	 * @param name name of the program to be stored and used for logging
	 */
	public synchronized void setName(String name) {
		this.name = name;
	}

	/**
	 * Creates or returns the file where the logged lines will go to. There will be one for each
	 * application for each day.
	 *
	 * @return a Log file with an appropriate filename
	 */
	private File getLogFile() throws IOException {
		String fileName = name + " - " + timestamp.getFormattedDate() + ".log";
		File logFile = new File(logDirectory, fileName);
		logFile.createNewFile();
		return logFile;
	}

	/**
	 * Composes a header that will be added in front of all log lines. This header includes the
	 * timestamp, the Thread it's running on and the type of action logged
	 *
	 * @param logType type of action logged
	 *
	 * @return A fully formed header for the line to be logged
	 */
	private String logHeader(String logType) {
		return timestamp() + "[" + Thread.currentThread().getName() + "/" + logType + "] ";
	}

	/**
	 * Formats the current time into a timestamp ready to be printed out
	 *
	 * @return fully printed
	 */
	private String timestamp() {
		return "[" + timestamp.getFormattedTime() + "] ";
	}

	/**
	 * Logs to a file using a printer, every call to this method will "create" a new file, however, if
	 * a previous file fits the criteria it will then be logged at the bottom of the file
	 *
	 * @param logLine the line to be logged in the file
	 *
	 * @throws IOException when the file doesn't exist cannot be created
	 */
	private synchronized void writeToFile(String logLine) throws IOException {
		try (FileWriter fileWriter = new FileWriter(getLogFile(), true);
				 PrintWriter writer = new PrintWriter(fileWriter)) {
			writer.println(logLine);
		}
	}

	/**
	 * Logs a custom message into a file and prints to the console in the selected color. It will mark
	 * the category
	 *
	 * @param message    message to be logged
	 * @param logMessage type of element being logged, it can be SUCCESS, ERROR...
	 * @param color      TextColor object corresponding to the desired
	 */
	private void log(String message, String logMessage, TextColor color) {
		String line = logHeader(logMessage) + message;
		TextColor.print(line, color, System.out);
		try {
			writeToFile(line);
		} catch (IOException e) {
			System.err.println("Failed to writeToFile to file");
		}
	}

	/**
	 * Logs a generic message
	 *
	 * @param message message to be logged
	 */
	public void log(String message) {
		log(message, "ACTION", CYAN);
	}

	/**
	 * Logs a success message
	 *
	 * @param message message to be logged
	 */
	public void success(String message) {
		log(message, "SUCCESS", GREEN);
	}

	/**
	 * Logs an informational message
	 *
	 * @param message message to be logged
	 */
	public void info(String message) {
		log(message, "INFO", BLUE);
	}

	/**
	 * Logs a warning
	 *
	 * @param message messaged to be logged
	 */
	public void warning(String message) {
		log(message, "WARN", YELLOW);
	}

	/**
	 * Logs an error that doesn't interrupt the functioning of the program
	 *
	 * @param message messaged to be logged
	 */
	public void error(String message) {
		log(message, "ERROR", RED);
	}

	/**
	 * Logs an error that interrupts the functioning of the program
	 *
	 * @param message messaged to be logged
	 */
	public void fatal(String message) {
		log(message, "FATAL", MAGENTA);
	}
}
