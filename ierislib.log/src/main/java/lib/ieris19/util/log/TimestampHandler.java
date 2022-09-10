package lib.ieris19.util.log;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static lib.ieris19.util.log.TimestampHandler.ChronoFormat.*;

/**
 * A class that can return the current date and time appropriately formatted for the log console
 */
public class TimestampHandler {
	/**
	 * Singleton instance of the timestamp handler
	 */
	private static TimestampHandler instance;

	/**
	 * Private constructor to avoid accidentally creating multiple instances of
	 * {@link TimestampHandler}. Please refer to {@link #getInstance()} for more information
	 */
	private TimestampHandler() {}

	/**
	 * Method to obtain the {@link TimestampHandler} instance, it will only create a new instance on
	 * its first call and return the already created instance every call after that. This means there
	 * will always be one single instance of the <code>TimestampHandler</code> class
	 *
	 * @return The instance of <code>TimestampHandler</code> for the current runtime, regardless of if
	 * it's new, or it had been previously created
	 */
	public synchronized static TimestampHandler getInstance() {
		if (instance == null) {
			instance = new TimestampHandler();
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
	public String getFormatted(ChronoFormat formatter) {
		return getFormatted(formatter.get());
	}

	public String getFormatted(DateTimeFormatter formatter) {
		return getTime().format(formatter);
	}

	/**
	 * Returns the current date and time in the least ambiguous format according to ISO normalization
	 *
	 * @return Current time with ISO Formatting
	 */
	public String getFormatted() {
		return getFormatted(ISO);
	}

	/**
	 * Formats a string with only the current date in ISO format, omitting all the time information.
	 * This is great for sorting
	 *
	 * @return The current UTC date in ISO format
	 */
	public String getFormattedDate() {
		return getFormatted(DATE_ONLY);
	}

	/**
	 * Formats a string with only the current time in ISO format, omitting all the date information.
	 * This is great for logging
	 *
	 * @return The current UTC time in ISO format
	 */
	public String getFormattedTime() {
		return getFormatted(TIME_ONLY);
	}

	/**
	 * Gets the current UTC time using the system clock
	 *
	 * @return UTC date and time at the moment of the call
	 */
	public LocalDateTime getTime() {
		return LocalDateTime.now(Clock.systemUTC());
	}

	/**
	 * Enumeration containing the {@link DateTimeFormatter} to be used in timestamping
	 */
	public enum ChronoFormat {
		/**
		 * Formatter for the European standard of Date and Time (dd/MM/yyyy HH:mm:ss)
		 */
		EUROPEAN("dd/MM/yyyy HH:mm:ss"),
		/**
		 * Formatter for the ISO standard of Date and Time (yyyy-MM-dd'T'HH:mm:ss)
		 */
		ISO("yyyy-MM-dd'T'HH:mm:ss"),
		/**
		 * Formatter for the ISO standard of Date only (yyyy-MM-dd)
		 */
		DATE_ONLY("yyyy-MM-dd"),
		/**
		 * Formatter for the ISO standard of Time only (HH:mm:ss)
		 */
		TIME_ONLY("HH:mm:ss");

		/**
		 * The time formatter for this specific {@link ChronoFormat ChronoFormat}
		 */
		private final DateTimeFormatter formatter;

		/**
		 * Constructs a formatter from the provided pattern
		 *
		 * @param pattern the Pattern for the {@link DateTimeFormatter}
		 */
		ChronoFormat(String pattern) {
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
	}
}
