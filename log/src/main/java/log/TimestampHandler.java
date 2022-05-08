package log;

import java.time.Clock;
import java.time.LocalDateTime;

import static log.ChronoFormat.*;

/**
 * A class that can return the current date and time appropriately formatted for the log console
 */
public class TimestampHandler {
	private static TimestampHandler timestampHandler;

	/**
	 * Private constructor to avoid accidentally creating multiple instances of
	 * {@link TimestampHandler}. Please refer to {@link #getInstance()} for more information
	 */
	private TimestampHandler() {
	}

	/**
	 * Factory method for the {@link TimestampHandler}, it will only create a new instance on its
	 * first call and return the already created instance every call after that. This means there will
	 * always be one single instance of the <code>CurrentTime</code> class
	 *
	 * @return The instance of <code>CurrentTime</code> for the current runtime, regardless of if it's
	 * new or it had been previously created
	 */
	public synchronized static TimestampHandler getInstance() {
		if (timestampHandler == null) {
			timestampHandler = new TimestampHandler();
		}
		return timestampHandler;
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
	 * Returns a formatted date and/or time string according to the pattern in the parameter object
	 *
	 * @param formatter Pattern for the String's format
	 *
	 * @return A formatted string
	 */
	public String getFormatted(ChronoFormat formatter) {
		return getTime().format(formatter.format());
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
}
