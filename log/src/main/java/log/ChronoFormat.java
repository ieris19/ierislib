package log;

import java.time.format.DateTimeFormatter;

public enum ChronoFormat {
	EUROPEAN(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")),
	ISO(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")),
	DATE_ONLY(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
	TIME_ONLY(DateTimeFormatter.ofPattern("HH:mm:ss"));

	private final DateTimeFormatter formatter;

	ChronoFormat(DateTimeFormatter formatter) {
		this.formatter = formatter;
	}

	public DateTimeFormatter format() {
		return this.formatter;
	}
}
