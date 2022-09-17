package lib.ieris19.util.log;

public enum LogLevel {
	FATAL(0),
	ERROR(1),
	WARNING(2),
	SUCCESS(3),
	INFO(4),
	DEBUG(5),
	TRACE(6);

	private int logLevel;

	LogLevel(int logLevel) {
		this.logLevel = logLevel;
	}

	public int level() {
		return logLevel;
	}
}
