package log;

public enum TextColor {
	RESET("\u001B[0m"),
	RED("\u001B[31m"),
	GREEN("\u001B[32m"),
	YELLOW("\u001B[33m"),
	BLUE("\u001B[34m");

	private final String ANSICode;

	TextColor(String code) {
		this.ANSICode = code;
	}

	@Override public String toString() {
		return ANSICode;
	}
}
