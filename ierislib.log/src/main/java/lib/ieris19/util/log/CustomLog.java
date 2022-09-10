package lib.ieris19.util.log;

public class CustomLog extends Log {
	TextContent[] headerBuilder;

	CustomLog(TextContent[] text) {
		super();
		this.headerBuilder = text;
	}

	/**
	 * Composes a header that will be added in front of all log lines. This header includes the sections specified by the
	 * builder
	 *
	 * @param logType type of action logged
	 *
	 * @return A fully formed header for the line to be logged
	 */
	@Override protected String logHeader(String logType) {
		StringBuilder header = new StringBuilder();
		for (TextContent section : headerBuilder) {
			header.append(section.getText(new String[] {logType}));
		}
		return header.toString();
	}
}

