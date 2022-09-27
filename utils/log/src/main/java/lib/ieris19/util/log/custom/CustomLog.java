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

package lib.ieris19.util.log.custom;

import lib.ieris19.util.log.ieris.IerisLog;

/**
 * A custom Log class that allows to log messages according to a custom format defined by the builder used to create
 * it.
 */
public class CustomLog extends IerisLog {
	/**
	 * An array of fields that will be used as fields to include information in the log message
	 */
	TextField[] headerBuilder;

	/**
	 * Creates a new CustomLog with the specified header builder array
	 *
	 * @param fields The header builder array
	 */
	CustomLog(String name, TextField[] fields) {
		super(name);
		this.headerBuilder = fields;
	}

	/**
	 * Composes a header that will be added in front of all log lines. This header includes the sections specified by the
	 * builder
	 *
	 * @param logType type of action logged
	 *
	 * @return A fully formed header for the line to be logged
	 */
	@Override public String logHeader(String logType) {
		StringBuilder header = new StringBuilder();
		for (TextField element : headerBuilder) {
			header.append(element.getText(logType));
		}
		return header.toString();
	}
}
