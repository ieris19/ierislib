package log;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static log.ChronoFormat.*;

/**
 * A class that can return the current date and time appropriately formatted for the log console
 */
public class CurrentTime {
	private static CurrentTime currentTime;

	/**
	 * Private constructor to avoid accidentally creating multiple instances of {@link CurrentTime}.
	 * Please refer to {@link #getInstance()} for more information
	 */
	private CurrentTime() {
	}

	/**
	 * Factory method for the {@link CurrentTime}, it will only create a new instance on its first
	 * call and return the already created instance every call after that. This means there will
	 * always be one single instance of the <code>CurrentTime</code> class
	 *
	 * @return The instance of <code>CurrentTime</code> for the current runtime, regardless of if it's
	 * new or it had been previously created
	 */
	public synchronized static CurrentTime getInstance() {
		if (currentTime == null) {
			currentTime = new CurrentTime();
		}
		return currentTime;
	}

	/**
	 * Returns the current time in the least ambiguous format according to ISO normalization
	 *
	 * @return Current time with ISO Formatting
	 */
	public String getFormattedTimeStamp() {
		return getFormattedTimeStamp(ISO);
	}

	/**
	 * Returns a formatted date and time string according to the pattern in the parameter object
	 *
	 * @param formatter Pattern for the String's format
	 *
	 * @return A formatted string
	 */
	public String getFormattedTimeStamp(ChronoFormat formatter) {
		return getTime().format(formatter.format());
	}

	/**
	 * Formats a string with only the current date in ISO format, omitting all the time information.
	 * This is great for sorting
	 *
	 * @return The current date in UTC time
	 */
	public String getFormattedDate() {
		return getFormattedTimeStamp(DATE_ONLY);
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
