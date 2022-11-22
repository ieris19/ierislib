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

package com.ieris19.lib.util.log.slf4j;

import com.ieris19.lib.util.log.Level;
import com.ieris19.lib.util.log.Log;
import com.ieris19.lib.util.log.ieris.IerisLog;
import org.slf4j.Logger;
import org.slf4j.Marker;

/**
 * A wrapper over {@link com.ieris19.lib.util.log.ieris.IerisLog IerisLog} in conformity with the {@link Logger SLF4J}
 * facade. interface.
 */
public final class IerisLogAdapter implements Logger {
	transient final Log ierisLog;

	IerisLogAdapter(Log ierisLog) {
		this.ierisLog = ierisLog;
	}

	@Override public String getName() {
		return ((IerisLog) ierisLog).getName();
	}

	private void log(Level severity, String string, Throwable t, Marker marker) {
		String message = compose(string, t, marker);
		switch (severity.level()) {
			case 0 -> ierisLog.fatal(message);
			case 1 -> ierisLog.error(message);
			case 2 -> ierisLog.warning(message);
			case 3 -> ierisLog.success(message);
			case 4 -> ierisLog.info(message);
			case 5 -> ierisLog.debug(message);
			case 6 -> ierisLog.trace(message);
		}
	}

	private String compose(String msg, Throwable t, Marker marker) {
		StringBuilder builder = new StringBuilder(msg);
		if (marker != null) {
			builder.insert(0, " (" + marker.getName() + ") ");
		}
		if (t != null) {
			builder.append("[").append(t.getClass().getSimpleName());
			if (t.getMessage() != null) {
				builder.append(": ").append(t.getMessage());
			}
			builder.append("]");
		}
		return builder.toString();
	}

	/**
	 * Is the logger instance enabled for the TRACE level
	 *
	 * @return True if this Logger is enabled for the TRACE level, false otherwise.
	 */
	@Override public boolean isTraceEnabled() {
		return ierisLog.isLevel(Level.TRACE);
	}

	/**
	 * Delegates its operation to the {@link #isTraceEnabled()} method.
	 *
	 * @param marker The marker that will be ignored
	 *
	 * @return True if this Logger is enabled for the TRACE level, false otherwise.
	 */
	@Override public boolean isTraceEnabled(Marker marker) {
		return isTraceEnabled();
	}

	/**
	 * Log a message at the TRACE level.
	 *
	 * @param msg the message string to be logged
	 */
	@Override public void trace(String msg) {
		if (isTraceEnabled()) {
			log(Level.TRACE, msg, null, null);
		}
	}

	/**
	 * Log a message at the TRACE level according to the specified format and argument.
	 *
	 * <p>This form avoids superfluous object creation when the logger
	 * is disabled for the TRACE level.
	 *
	 * @param format the format string
	 * @param arg    the argument
	 */
	@Override public void trace(String format, Object arg) {
		if (isTraceEnabled()) {
			trace(String.format(format, arg));
		}
	}

	/**
	 * Log a message at the TRACE level according to the specified format and arguments.
	 *
	 * <p>This form avoids superfluous object creation when the logger
	 * is disabled for the TRACE level.
	 *
	 * @param format the format string
	 * @param arg1   the first argument
	 * @param arg2   the second argument
	 */
	@Override public void trace(String format, Object arg1, Object arg2) {
		if (isTraceEnabled()) {
			trace(String.format(format, arg1, arg2));
		}
	}

	/**
	 * Log a message at the TRACE level according to the specified format and arguments.
	 *
	 * <p>This form avoids superfluous string concatenation when the logger
	 * is disabled for the TRACE level. However, this variant incurs the hidden (and relatively small) cost of creating
	 * an
	 * <code>Object[]</code> before invoking the method, even if this logger is disabled for TRACE. The variants taking
	 * {@link #trace(String, Object) one} and {@link #trace(String, Object, Object) two} arguments exist solely in order
	 * to avoid this hidden cost.
	 *
	 * @param format the format string
	 * @param args   a list of 3 or more arguments
	 */
	@Override public void trace(String format, Object... args) {
		if (isTraceEnabled()) {
			trace(String.format(format, args));
		}
	}

	/**
	 * Log an exception (throwable) at the TRACE level with an accompanying message.
	 *
	 * @param msg the message accompanying the exception
	 * @param t   the exception (throwable) to log
	 */
	@Override public void trace(String msg, Throwable t) {
		if (isTraceEnabled()) {
			log(Level.TRACE, msg, t, null);
		}
	}

	/**
	 * Log a message with the specific Marker at the TRACE level.
	 *
	 * @param marker the marker data specific to this log statement
	 * @param msg    the message string to be logged
	 */
	@Override public void trace(Marker marker, String msg) {
		if (isTraceEnabled()) {
			log(Level.TRACE, msg, null, marker);
		}
	}

	/**
	 * This method is similar to {@link #trace(String, Object)} method except that the marker data is also taken into
	 * consideration.
	 *
	 * @param marker the marker data specific to this log statement
	 * @param format the format string
	 * @param arg    the argument
	 */
	@Override public void trace(Marker marker, String format, Object arg) {
		if (isTraceEnabled()) {
			trace(marker, String.format(format, arg));
		}
	}

	/**
	 * This method is similar to {@link #trace(String, Object, Object)} method except that the marker data is also taken
	 * into consideration.
	 *
	 * @param marker the marker data specific to this log statement
	 * @param format the format string
	 * @param arg1   the first argument
	 * @param arg2   the second argument
	 */
	@Override public void trace(Marker marker, String format, Object arg1, Object arg2) {
		if (isTraceEnabled()) {
			trace(marker, String.format(format, arg1, arg2));
		}
	}

	/**
	 * This method is similar to {@link #trace(String, Object...)} method except that the marker data is also taken into
	 * consideration.
	 *
	 * @param marker the marker data specific to this log statement
	 * @param format the format string
	 * @param args   an array of arguments
	 */
	@Override public void trace(Marker marker, String format, Object... args) {
		if (isTraceEnabled()) {
			trace(marker, String.format(format, args));
		}
	}

	/**
	 * This method is similar to {@link #trace(String, Throwable)} method except that the marker data is also taken into
	 * consideration.
	 *
	 * @param marker the marker data specific to this log statement
	 * @param msg    the message accompanying the exception
	 * @param t      the exception (throwable) to log
	 */
	@Override public void trace(Marker marker, String msg, Throwable t) {
		if (isTraceEnabled()) {
			log(Level.TRACE, msg, t, marker);
		}
	}

	/**
	 * Is the logger instance enabled for the DEBUG level?
	 *
	 * @return True if this Logger is enabled for the DEBUG level, false otherwise.
	 */
	@Override public boolean isDebugEnabled() {
		return ierisLog.isLevel(Level.DEBUG);
	}

	/**
	 * Delegates its operation to the {@link #isDebugEnabled()} method.
	 *
	 * @param marker The marker that will be ignored
	 *
	 * @return True if this Logger is enabled for the DEBUG level, false otherwise.
	 */
	@Override public boolean isDebugEnabled(Marker marker) {
		return isDebugEnabled();
	}

	/**
	 * Log a message at the DEBUG level.
	 *
	 * @param msg the message string to be logged
	 */
	@Override public void debug(String msg) {
		if (isDebugEnabled()) {
			log(Level.DEBUG, msg, null, null);
		}
	}

	/**
	 * Log a message at the DEBUG level according to the specified format and argument.
	 *
	 * <p>This form avoids superfluous object creation when the logger
	 * is disabled for the DEBUG level.
	 *
	 * @param format the format string
	 * @param arg    the argument
	 */
	@Override public void debug(String format, Object arg) {
		if (isDebugEnabled()) {
			debug(String.format(format, arg));
		}
	}

	/**
	 * Log a message at the DEBUG level according to the specified format and arguments.
	 *
	 * <p>This form avoids superfluous object creation when the logger
	 * is disabled for the DEBUG level.
	 *
	 * @param format the format string
	 * @param arg1   the first argument
	 * @param arg2   the second argument
	 */
	@Override public void debug(String format, Object arg1, Object arg2) {
		if (isDebugEnabled()) {
			debug(String.format(format, arg1, arg2));
		}
	}

	/**
	 * Log a message at the DEBUG level according to the specified format and arguments.
	 *
	 * <p>This form avoids superfluous string concatenation when the logger
	 * is disabled for the DEBUG level. However, this variant incurs the hidden (and relatively small) cost of creating
	 * an
	 * <code>Object[]</code> before invoking the method, even if this logger is disabled for DEBUG. The variants taking
	 * {@link #debug(String, Object) one} and {@link #debug(String, Object, Object) two} arguments exist solely in order
	 * to avoid this hidden cost.
	 *
	 * @param format the format string
	 * @param args   a list of 3 or more arguments
	 */
	@Override public void debug(String format, Object... args) {
		if (isDebugEnabled()) {
			debug(String.format(format, args));
		}
	}

	/**
	 * Log an exception (throwable) at the DEBUG level with an accompanying message.
	 *
	 * @param msg the message accompanying the exception
	 * @param t   the exception (throwable) to log
	 */
	@Override public void debug(String msg, Throwable t) {
		if (isDebugEnabled()) {
			log(Level.DEBUG, msg, t, null);
		}
	}

	/**
	 * Log a message with the specific Marker at the DEBUG level.
	 *
	 * @param marker the marker data specific to this log statement
	 * @param msg    the message string to be logged
	 */
	@Override public void debug(Marker marker, String msg) {
		if (isDebugEnabled()) {
			log(Level.DEBUG, msg, null, marker);
		}
	}

	/**
	 * This method is similar to {@link #debug(String, Object)} method except that the marker data is also taken into
	 * consideration.
	 *
	 * @param marker the marker data specific to this log statement
	 * @param format the format string
	 * @param arg    the argument
	 */
	@Override public void debug(Marker marker, String format, Object arg) {
		if (isDebugEnabled()) {
			debug(marker, String.format(format, arg));
		}
	}

	/**
	 * This method is similar to {@link #debug(String, Object, Object)} method except that the marker data is also taken
	 * into consideration.
	 *
	 * @param marker the marker data specific to this log statement
	 * @param format the format string
	 * @param arg1   the first argument
	 * @param arg2   the second argument
	 */
	@Override public void debug(Marker marker, String format, Object arg1, Object arg2) {
		if (isDebugEnabled()) {
			debug(marker, String.format(format, arg1, arg2));
		}
	}

	/**
	 * This method is similar to {@link #debug(String, Object...)} method except that the marker data is also taken into
	 * consideration.
	 *
	 * @param marker    the marker data specific to this log statement
	 * @param format    the format string
	 * @param arguments a list of 3 or more arguments
	 */
	@Override public void debug(Marker marker, String format, Object... arguments) {
		if (isDebugEnabled()) {
			debug(marker, String.format(format, arguments));
		}
	}

	/**
	 * This method is similar to {@link #debug(String, Throwable)} method except that the marker data is also taken into
	 * consideration.
	 *
	 * @param marker the marker data specific to this log statement
	 * @param msg    the message accompanying the exception
	 * @param t      the exception (throwable) to log
	 */
	@Override public void debug(Marker marker, String msg, Throwable t) {
		if (isDebugEnabled()) {
			log(Level.DEBUG, msg, t, marker);
		}
	}

	/**
	 * Is the logger instance enabled for the INFO level?
	 *
	 * @return True if this Logger is enabled for the INFO level, false otherwise.
	 */
	@Override public boolean isInfoEnabled() {
		return ierisLog.isLevel(Level.INFO);
	}

	/**
	 * Similar to {@link #isInfoEnabled()} method except that the marker data is also taken into consideration.
	 *
	 * @param marker The marker data to take into consideration
	 *
	 * @return true if this Logger is enabled for the INFO level, false otherwise.
	 */
	@Override public boolean isInfoEnabled(Marker marker) {
		return isInfoEnabled();
	}

	/**
	 * Log a message at the INFO level.
	 *
	 * @param msg the message string to be logged
	 */
	@Override public void info(String msg) {
		if (isInfoEnabled()) {
			log(Level.INFO, msg, null, null);
		}
	}

	/**
	 * Log a message at the INFO level according to the specified format and argument.
	 *
	 * <p>This form avoids superfluous object creation when the logger
	 * is disabled for the INFO level.
	 *
	 * @param format the format string
	 * @param arg    the argument
	 */
	@Override public void info(String format, Object arg) {
		if (isInfoEnabled()) {
			info(String.format(format, arg));
		}
	}

	/**
	 * Log a message at the INFO level according to the specified format and arguments.
	 *
	 * <p>This form avoids superfluous object creation when the logger
	 * is disabled for the INFO level.
	 *
	 * @param format the format string
	 * @param arg1   the first argument
	 * @param arg2   the second argument
	 */
	@Override public void info(String format, Object arg1, Object arg2) {
		if (isInfoEnabled()) {
			info(String.format(format, arg1, arg2));
		}
	}

	/**
	 * Log a message at the INFO level according to the specified format and arguments.
	 *
	 * <p>This form avoids superfluous string concatenation when the logger
	 * is disabled for the INFO level. However, this variant incurs the hidden (and relatively small) cost of creating an
	 * <code>Object[]</code> before invoking the method, even if this logger is disabled for INFO. The variants taking
	 * {@link #info(String, Object) one} and {@link #info(String, Object, Object) two} arguments exist solely in order to
	 * avoid this hidden cost.
	 *
	 * @param format    the format string
	 * @param arguments a list of 3 or more arguments
	 */
	@Override public void info(String format, Object... arguments) {
		if (isInfoEnabled()) {
			info(String.format(format, arguments));
		}
	}

	/**
	 * Log an exception (throwable) at the INFO level with an accompanying message.
	 *
	 * @param msg the message accompanying the exception
	 * @param t   the exception (throwable) to log
	 */
	@Override public void info(String msg, Throwable t) {
		if (isInfoEnabled()) {
			log(Level.INFO, msg, t, null);
		}
	}

	/**
	 * Log a message with the specific Marker at the INFO level.
	 *
	 * @param marker The marker specific to this log statement
	 * @param msg    the message string to be logged
	 */
	@Override public void info(Marker marker, String msg) {
		if (isInfoEnabled()) {
			log(Level.INFO, msg, null, marker);
		}
	}

	/**
	 * This method is similar to {@link #info(String, Object)} method except that the marker data is also taken into
	 * consideration.
	 *
	 * @param marker the marker data specific to this log statement
	 * @param format the format string
	 * @param arg    the argument
	 */
	@Override public void info(Marker marker, String format, Object arg) {
		if (isInfoEnabled()) {
			info(marker, String.format(format, arg));
		}
	}

	/**
	 * This method is similar to {@link #info(String, Object, Object)} method except that the marker data is also taken
	 * into consideration.
	 *
	 * @param marker the marker data specific to this log statement
	 * @param format the format string
	 * @param arg1   the first argument
	 * @param arg2   the second argument
	 */
	@Override public void info(Marker marker, String format, Object arg1, Object arg2) {
		if (isInfoEnabled()) {
			info(marker, String.format(format, arg1, arg2));
		}
	}

	/**
	 * This method is similar to {@link #info(String, Object...)} method except that the marker data is also taken into
	 * consideration.
	 *
	 * @param marker    the marker data specific to this log statement
	 * @param format    the format string
	 * @param arguments a list of 3 or more arguments
	 */
	@Override public void info(Marker marker, String format, Object... arguments) {
		if (isInfoEnabled()) {
			info(marker, String.format(format, arguments));
		}
	}

	/**
	 * This method is similar to {@link #info(String, Throwable)} method except that the marker data is also taken into
	 * consideration.
	 *
	 * @param marker the marker data for this log statement
	 * @param msg    the message accompanying the exception
	 * @param t      the exception (throwable) to log
	 */
	@Override public void info(Marker marker, String msg, Throwable t) {
		if (isInfoEnabled()) {
			log(Level.INFO, msg, t, marker);
		}
	}

	/**
	 * Is the logger instance enabled for the WARN level?
	 *
	 * @return True if this Logger is enabled for the WARN level, false otherwise.
	 */
	@Override public boolean isWarnEnabled() {
		return ierisLog.isLevel(Level.WARNING);
	}

	/**
	 * Similar to {@link #isWarnEnabled()} method except that the marker data is also taken into consideration.
	 *
	 * @param marker The marker data to take into consideration
	 *
	 * @return True if this Logger is enabled for the WARN level, false otherwise.
	 */
	@Override public boolean isWarnEnabled(Marker marker) {
		return false;
	}

	/**
	 * Log a message at the WARN level.
	 *
	 * @param msg the message string to be logged
	 */
	@Override public void warn(String msg) {
		if (isWarnEnabled()) {
			log(Level.WARNING, msg, null, null);
		}
	}

	/**
	 * Log a message at the WARN level according to the specified format and argument.
	 *
	 * <p>This form avoids superfluous object creation when the logger
	 * is disabled for the WARN level.
	 *
	 * @param format the format string
	 * @param arg    the argument
	 */
	@Override public void warn(String format, Object arg) {
		if (isWarnEnabled()) {
			warn(String.format(format, arg));
		}
	}

	/**
	 * Log a message at the WARN level according to the specified format and arguments.
	 *
	 * <p>This form avoids superfluous string concatenation when the logger
	 * is disabled for the WARN level. However, this variant incurs the hidden (and relatively small) cost of creating an
	 * <code>Object[]</code> before invoking the method, even if this logger is disabled for WARN. The variants taking
	 * {@link #warn(String, Object) one} and {@link #warn(String, Object, Object) two} arguments exist solely in order to
	 * avoid this hidden cost.
	 *
	 * @param format    the format string
	 * @param arguments a list of 3 or more arguments
	 */
	@Override public void warn(String format, Object... arguments) {
		if (isWarnEnabled()) {
			warn(String.format(format, arguments));
		}
	}

	/**
	 * Log a message at the WARN level according to the specified format and arguments.
	 *
	 * <p>This form avoids superfluous object creation when the logger
	 * is disabled for the WARN level.
	 *
	 * @param format the format string
	 * @param arg1   the first argument
	 * @param arg2   the second argument
	 */
	@Override public void warn(String format, Object arg1, Object arg2) {
		if (isWarnEnabled()) {
			warn(String.format(format, arg1, arg2));
		}
	}

	/**
	 * Log an exception (throwable) at the WARN level with an accompanying message.
	 *
	 * @param msg the message accompanying the exception
	 * @param t   the exception (throwable) to log
	 */
	@Override public void warn(String msg, Throwable t) {
		if (isWarnEnabled()) {
			log(Level.WARNING, msg, t, null);
		}
	}

	/**
	 * Log a message with the specific Marker at the WARN level.
	 *
	 * @param marker The marker specific to this log statement
	 * @param msg    the message string to be logged
	 */
	@Override public void warn(Marker marker, String msg) {
		if (isWarnEnabled()) {
			log(Level.WARNING, msg, null, marker);
		}
	}

	/**
	 * This method is similar to {@link #warn(String, Object)} method except that the marker data is also taken into
	 * consideration.
	 *
	 * @param marker the marker data specific to this log statement
	 * @param format the format string
	 * @param arg    the argument
	 */
	@Override public void warn(Marker marker, String format, Object arg) {
		if (isWarnEnabled()) {
			warn(marker, String.format(format, arg));
		}
	}

	/**
	 * This method is similar to {@link #warn(String, Object, Object)} method except that the marker data is also taken
	 * into consideration.
	 *
	 * @param marker the marker data specific to this log statement
	 * @param format the format string
	 * @param arg1   the first argument
	 * @param arg2   the second argument
	 */
	@Override public void warn(Marker marker, String format, Object arg1, Object arg2) {
		if (isWarnEnabled()) {
			warn(marker, String.format(format, arg1, arg2));
		}
	}

	/**
	 * This method is similar to {@link #warn(String, Object...)} method except that the marker data is also taken into
	 * consideration.
	 *
	 * @param marker    the marker data specific to this log statement
	 * @param format    the format string
	 * @param arguments a list of 3 or more arguments
	 */
	@Override public void warn(Marker marker, String format, Object... arguments) {
		if (isWarnEnabled()) {
			warn(marker, String.format(format, arguments));
		}
	}

	/**
	 * This method is similar to {@link #warn(String, Throwable)} method except that the marker data is also taken into
	 * consideration.
	 *
	 * @param marker the marker data for this log statement
	 * @param msg    the message accompanying the exception
	 * @param t      the exception (throwable) to log
	 */
	@Override public void warn(Marker marker, String msg, Throwable t) {
		if (isWarnEnabled()) {
			log(Level.WARNING, msg, t, marker);
		}
	}

	/**
	 * Is the logger instance enabled for the ERROR level?
	 *
	 * @return True if this Logger is enabled for the ERROR level, false otherwise.
	 */
	@Override public boolean isErrorEnabled() {
		return ierisLog.isLevel(Level.ERROR);
	}

	/**
	 * Similar to {@link #isErrorEnabled()} method except that the marker data is also taken into consideration.
	 *
	 * @param marker The marker data to take into consideration
	 *
	 * @return True if this Logger is enabled for the ERROR level, false otherwise.
	 */
	@Override public boolean isErrorEnabled(Marker marker) {
		return isErrorEnabled();
	}

	/**
	 * Log a message at the ERROR level.
	 *
	 * @param msg the message string to be logged
	 */
	@Override public void error(String msg) {
		if (isErrorEnabled()) {
			log(Level.ERROR, msg, null, null);
		}
	}

	/**
	 * Log a message at the ERROR level according to the specified format and argument.
	 *
	 * <p>This form avoids superfluous object creation when the logger
	 * is disabled for the ERROR level.
	 *
	 * @param format the format string
	 * @param arg    the argument
	 */
	@Override public void error(String format, Object arg) {
		if (isErrorEnabled()) {
			error(String.format(format, arg));
		}
	}

	/**
	 * Log a message at the ERROR level according to the specified format and arguments.
	 *
	 * <p>This form avoids superfluous object creation when the logger
	 * is disabled for the ERROR level.
	 *
	 * @param format the format string
	 * @param arg1   the first argument
	 * @param arg2   the second argument
	 */
	@Override public void error(String format, Object arg1, Object arg2) {
		if (isErrorEnabled()) {
			error(String.format(format, arg1, arg2));
		}
	}

	/**
	 * Log a message at the ERROR level according to the specified format and arguments.
	 *
	 * <p>This form avoids superfluous string concatenation when the logger
	 * is disabled for the ERROR level. However, this variant incurs the hidden (and relatively small) cost of creating
	 * an
	 * <code>Object[]</code> before invoking the method, even if this logger is disabled for ERROR. The variants taking
	 * {@link #error(String, Object) one} and {@link #error(String, Object, Object) two} arguments exist solely in order
	 * to avoid this hidden cost.
	 *
	 * @param format    the format string
	 * @param arguments a list of 3 or more arguments
	 */
	@Override public void error(String format, Object... arguments) {
		if (isErrorEnabled()) {
			error(String.format(format, arguments));
		}
	}

	/**
	 * Log an exception (throwable) at the ERROR level with an accompanying message.
	 *
	 * @param msg the message accompanying the exception
	 * @param t   the exception (throwable) to log
	 */
	@Override public void error(String msg, Throwable t) {
		if (isErrorEnabled()) {
			log(Level.ERROR, msg, t, null);
		}
	}

	/**
	 * Log a message with the specific Marker at the ERROR level.
	 *
	 * @param marker The marker specific to this log statement
	 * @param msg    the message string to be logged
	 */
	@Override public void error(Marker marker, String msg) {
		if (isErrorEnabled()) {
			log(Level.ERROR, msg, null, marker);
		}
	}

	/**
	 * This method is similar to {@link #error(String, Object)} method except that the marker data is also taken into
	 * consideration.
	 *
	 * @param marker the marker data specific to this log statement
	 * @param format the format string
	 * @param arg    the argument
	 */
	@Override public void error(Marker marker, String format, Object arg) {
		if (isErrorEnabled()) {
			error(marker, String.format(format, arg));
		}
	}

	/**
	 * This method is similar to {@link #error(String, Object, Object)} method except that the marker data is also taken
	 * into consideration.
	 *
	 * @param marker the marker data specific to this log statement
	 * @param format the format string
	 * @param arg1   the first argument
	 * @param arg2   the second argument
	 */
	@Override public void error(Marker marker, String format, Object arg1, Object arg2) {
		if (isErrorEnabled()) {
			error(marker, String.format(format, arg1, arg2));
		}
	}

	/**
	 * This method is similar to {@link #error(String, Object...)} method except that the marker data is also taken into
	 * consideration.
	 *
	 * @param marker    the marker data specific to this log statement
	 * @param format    the format string
	 * @param arguments a list of 3 or more arguments
	 */
	@Override public void error(Marker marker, String format, Object... arguments) {
		if (isErrorEnabled()) {
			error(marker, String.format(format, arguments));
		}
	}

	/**
	 * This method is similar to {@link #error(String, Throwable)} method except that the marker data is also taken into
	 * consideration.
	 *
	 * @param marker the marker data specific to this log statement
	 * @param msg    the message accompanying the exception
	 * @param t      the exception (throwable) to log
	 */
	@Override public void error(Marker marker, String msg, Throwable t) {
		if (isErrorEnabled()) {
			log(Level.ERROR, msg, t, marker);
		}
	}
}
