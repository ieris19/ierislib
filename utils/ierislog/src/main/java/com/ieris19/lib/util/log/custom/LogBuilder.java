/*
 * Copyright 2021 Ieris19
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 *
 */

package com.ieris19.lib.util.log.custom;

import com.ieris19.lib.util.log.TimestampHandler;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * A builder class for creating custom loggers with custom headers before each log line
 */
public class LogBuilder {
	/**
	 * The array of text fields that logs will use to create their headers
	 */
	private final ArrayList<TextField> headerBuilder;
	/**
	 * A map of common predetermined text fields that can be used in the header builder
	 */
	private final HashMap<String, TextField> defaultElements;
	/**
	 * Whether the log has an open bracket or not. Nested brackets are not supported
	 */
	private boolean openSection;

	/**
	 * A constructor to initialize an empty builder
	 */
	public LogBuilder() {
		headerBuilder = new ArrayList<TextField>();
		defaultElements = new HashMap<String, TextField>();
		setDefaultElements();
		openSection = false;
	}

	/**
	 * A method that maps the default elements that can be used in the header builder. It is called by the constructor and
	 * contains the hard coded default elements
	 */
	private void setDefaultElements() {
		defaultElements.put("separator", (args) -> "/");
		defaultElements.put("start-section", (args) -> "[");
		defaultElements.put("close-section", (args) -> "] ");

		defaultElements.put("timestamp", (args) -> TimestampHandler.getInstance().getFormatted());
		defaultElements.put("date", (args) -> TimestampHandler.getInstance().getFormattedDate());
		defaultElements.put("time", (args) -> TimestampHandler.getInstance().getFormattedTime());

		defaultElements.put("severity", (args) -> args[0]);
		defaultElements.put("thread", (args) -> Thread.currentThread().getName());
	}

	/**
	 * Creates a new builder based on one of the predefined templates. The previous builder is discarded, regardless of
	 * how many elements it contained. <br><br>
	 *
	 * The following templates are available: <br>
	 * <ul>
	 * 	<li>minimal: [timestamp] Message</li>
	 *  <li>default: [timestamp] [thread/severity] Message</li>
	 *  <li>complete: [ISO-Timestamp 'Instant:'milliseconds of day] [thread] [severity] Message</li>
	 * </ul>
	 *
	 * @param template
	 *
	 * @return
	 */
	public static LogBuilder template(String template) {
		return switch (template) {
			case "minimal" -> minimalTemplate();
			case "complete" -> completeTemplate();
			default -> defaultTemplate();
		};
	}

	/**
	 * Creates a new builder based on the minimal template
	 *
	 * @return a builder of form: [timestamp] Message
	 */
	private static LogBuilder minimalTemplate() {
		return new LogBuilder().add("time");
	}

	/**
	 * Creates a new builder based on the default template
	 *
	 * @return a builder of form: [timestamp] [thread/severity] Message
	 */
	private static LogBuilder defaultTemplate() {
		return new LogBuilder().addSection().add("time").closeSection().addSection().add("thread").add("separator")
				.add("severity").closeSection();
	}

	/**
	 * Creates a new builder based on the complete template
	 *
	 * @return a builder of form: [ISO-Timestamp 'Instant:'milliseconds of day] [thread] [severity] Message
	 */
	private static LogBuilder completeTemplate() {
		return new LogBuilder().addSection().add("timestamp")
				.add((args) -> TimestampHandler.getInstance().getFormatted(DateTimeFormatter.ofPattern("' Instant:'AA")))
				.closeSection().addSection().add("thread").closeSection().addSection().add("severity").closeSection();
	}

	/**
	 * Adds a new field to the header builder
	 *
	 * @param key The name of the field to add from the map of default fields
	 *
	 * @return The builder with the additional field
	 *
	 * @see LogBuilder#defaultElements
	 * @see LogBuilder#add(TextField)
	 */
	public LogBuilder add(String key) {
		return defaultElements.get(key) != null ? add(defaultElements.get(key)) : this;
	}

	/**
	 * Adds a new field to the header builder
	 *
	 * @param field The field to add
	 *
	 * @return The builder with the additional field
	 */
	public LogBuilder add(TextField field) {
		if (!openSection) {
			addSection();
		}
		headerBuilder.add(field);
		return this;
	}

	/**
	 * Adds a new section to the header builder
	 *
	 * @return The builder with the additional section
	 *
	 * @throws IllegalStateException if the previous section was not closed
	 */
	public LogBuilder addSection() throws IllegalStateException {
		if (openSection) {
			throw new IllegalStateException("Cannot add a section to an open section");
		} else {
			openSection = true;
			return add("start-section");
		}
	}

	/**
	 * Closes the current section in the header builder
	 *
	 * @return The builder with the closed section
	 *
	 * @throws IllegalStateException if the previous section was already closed
	 */
	public LogBuilder closeSection() throws IllegalStateException {
		if (!openSection) {
			throw new IllegalStateException("Cannot close a closed section");
		}
		add("close-section");
		openSection = false;
		return this;
	}

	/**
	 * Creates a new logger with the current header builder content
	 *
	 * @param name The name of the application being logged
	 *
	 * @return A new logger with the current header builder
	 */
	public CustomLog build(String name) {
		if (openSection) {
			closeSection();
		}
		CustomLog log = new CustomLog(name, headerBuilder.toArray(new TextField[0]));
		log.debug("IerisLog has been initialized");
		return log;
	}
}
