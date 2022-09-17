package lib.ieris19.util.log.custom;

import lib.ieris19.util.log.TimestampHandler;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;

public class LogBuilder {
	private ArrayList<TextContent> headerBuilder;
	private HashMap<String, TextContent> defaultElements;
	private boolean openSection;

	public LogBuilder() {
		headerBuilder = new ArrayList<TextContent>();
		defaultElements = new HashMap<String, TextContent>();
		setDefaultElements();
		openSection = false;
	}

	public void setDefaultElements() {
		defaultElements.put("separator", (args) -> "/");
		defaultElements.put("start-section", (args) -> "[");
		defaultElements.put("close-section", (args) -> "]");

		defaultElements.put("timestamp", (args) -> TimestampHandler.getInstance().getFormatted());
		defaultElements.put("date", (args) -> TimestampHandler.getInstance().getFormattedDate());
		defaultElements.put("time", (args) -> TimestampHandler.getInstance().getFormattedTime());

		defaultElements.put("severity", (args) -> args[0]);
		defaultElements.put("thread", (args) -> Thread.currentThread().getName());
	}

	public static CustomLog template(String template) {
		return switch (template) {
			case "minimal" -> minimalTemplate();
			case "complete" -> completeTemplate();
			default -> defaultTemplate();
		};
	}

	private static CustomLog minimalTemplate() {
		return new LogBuilder().add("time").build();
	}

	private static CustomLog defaultTemplate() {
		return new LogBuilder().addSection().add("time").closeSection().addSection().add("thread").add("separator")
				.add("severity").closeSection().build();
	}

	private static CustomLog completeTemplate() {
		return new LogBuilder().addSection().add("timestamp")
				.add((args) -> TimestampHandler.getInstance().getFormatted(DateTimeFormatter.ofPattern("' Instant:'AA")))
				.closeSection().addSection().add("thread").closeSection().addSection().add("severity").closeSection().build();
	}

	public LogBuilder add(String key) {
		return defaultElements.get(key) != null ? add(defaultElements.get(key)) : this;
	}

	public LogBuilder add(TextContent section) throws IllegalArgumentException {
		if (!openSection) {
			addSection();
		}
		headerBuilder.add(section);
		return this;
	}

	public LogBuilder addSection() {
		if (openSection) {
			throw new IllegalStateException("Cannot add a section to an open section");
		} else {
			openSection = true;
			return add("start-section");
		}
	}

	public LogBuilder closeSection() {
		if (openSection) {
			add("close-section");
			openSection = false;
			return this;
		} else {
			throw new IllegalStateException("Cannot close a closed section");
		}
	}

	public CustomLog build() {
		if (openSection) {
			closeSection();
		}
		return new CustomLog(headerBuilder.toArray(new TextContent[headerBuilder.size()]));
	}
}
