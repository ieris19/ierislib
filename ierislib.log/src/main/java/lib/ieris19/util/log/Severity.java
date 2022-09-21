package lib.ieris19.util.log;

/**
 * An enum that contains all the possible severity levels for a log message
 */
public enum Severity {
	/**
	 * Fatal error that interrupts the functioning of the program
	 */
	FATAL(0),
	/**
	 * Error that doesn't interrupt the functioning of the program
	 */
	ERROR(1),
	/**
	 * Warning that something went wrong but doesn't affect the functioning of the program
	 */
	WARNING(2),
	/**
	 * Success message that indicates that something went right
	 */
	SUCCESS(3),
	/**
	 * Informational message that indicates something that happened
	 */
	INFO(4),
	/**
	 * Debug message that indicates actions that are being performed step by step
	 */
	DEBUG(5),
	/**
	 * Trace message that indicates the flow of the program
	 */
	TRACE(6);

	/**
	 * The level of the severity
	 */
	private int logLevel;

	/**
	 * Creates a new Severity level with the specified index
	 * @param logLevel The index of the severity as an integer
	 */
	Severity(int logLevel) {
		this.logLevel = logLevel;
	}

	/**
	 * Returns the severity as an integer for comparison purposes
	 * @return The severity level as an integer
	 */
	public int level() {
		return logLevel;
	}
}
