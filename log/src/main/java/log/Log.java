package log;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import static log.ChronoFormat.*;
import static log.TextColor.*;

/**
 * A class that can writeToFile messages to a file with a UTC Timestamp
 */
public class Log {
	/**
	 * Singleton instance variable
	 */
	private static Log log;

	/**
	 * Class to properly timestamp writeToFile instances
	 */
	private final CurrentTime currentTime;
	/**
	 * Directory of the writeToFile file
	 */
	private File logDirectory;

	/**
	 * Class that allows logging of activity into a writeToFile file
	 */
	private Log() {
		String homePath = System.getProperty("user.home");
		logDirectory = new File(homePath, "Documents");
		currentTime = CurrentTime.getInstance();
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
	 * Sets the log file to the provided one
	 * @param logFile the new file where the log will go to
	 */
	public synchronized void changeLogDirectory(File logFile) {
		this.logDirectory = logFile;
	}

	/**
	 * Creates the file where the logged lines will go to
	 * @return a Log file with an appropriate filename
	 */
	private File getLogFile() {
		String fileName = "Log -" + currentTime.getFormattedDate() + ".txt";
		return new File(logDirectory, fileName);
	}

	/**
	 * Formats the logged message into a writeToFile ready line by adding a timestamp
	 * @param message
	 * @return
	 */
	private String logLine(String message) {
		return currentTime.getFormattedTimeStamp(ISO) + " - " + message;
	}

	private synchronized void writeToFile(String logLine) throws IOException {
		try (FileWriter fileWriter = new FileWriter(getLogFile(), true);
				 PrintWriter writer = new PrintWriter(fileWriter)) {
			writer.println(logLine);
		}
	}

	public void success(String message) {
		String line = "[SUCCESS]" + logLine(message);
		System.out.println(GREEN + line + RESET);
		try {
			writeToFile(line);
		} catch (IOException e) {
			System.err.println("Failed to writeToFile to file");
		}
	}

	public void info(String message) {
		String line = "[INFO]" + logLine(message);
		System.out.println(BLUE + line + RESET);
		try {
			writeToFile(line);
		} catch (IOException e) {
			System.err.println("Failed to writeToFile to file");
		}
	}

	public void warning(String message) {
		String line = "[WARN] " + logLine(message);
		System.out.println(YELLOW + line + RESET);
		try {
			writeToFile(line);
		} catch (IOException e) {
			System.err.println("Failed to writeToFile to file");
		}
	}

	public void error(String message) {
		String line = "[ERROR] " + logLine(message);
		System.out.println(RED + line + RESET);
		try {
			writeToFile(line);
		} catch (IOException e) {
			System.err.println("Failed to writeToFile to file");
		}
	}
}
