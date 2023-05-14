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

package com.ieris19.lib.util.console;

/**
 * Thrown in the {@link Console} to indicate that the command is not valid
 */
public class InvalidCommandException extends RuntimeException {

	/**
	 * Constructs an {@code InvalidCommandException} with no detail message.
	 */
	public InvalidCommandException() {
		super();
	}

	/**
	 * Constructs an {@code InvalidCommandException} with the specified detail message.
	 *
	 * @param message the detail message.
	 */
	public InvalidCommandException(String message) {
		super(message);
	}
}