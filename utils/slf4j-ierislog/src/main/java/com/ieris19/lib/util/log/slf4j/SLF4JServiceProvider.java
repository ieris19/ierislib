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

import org.slf4j.ILoggerFactory;
import org.slf4j.IMarkerFactory;
import org.slf4j.spi.MDCAdapter;

/**
 * This class provides the logging service for the slf4j facade. All methods directed at slf4j will be directed to this
 * provider if present in the classpath and no other slf4j providers are present. If other providers are present it
 * won't work.
 */
public class SLF4JServiceProvider implements org.slf4j.spi.SLF4JServiceProvider {
	/**
	 * Empty constructor for the SLF4JServiceProvider class as it is not used.
	 */
	public SLF4JServiceProvider() {
	}
	/**
	 * Return the instance of {@link org.slf4j.ILoggerFactory} that {@link org.slf4j.LoggerFactory} class should bind to.
	 *
	 * @return instance of {@link org.slf4j.ILoggerFactory}
	 */
	@Override public ILoggerFactory getLoggerFactory() {
		return new IerisLogFactory();
	}

	/**
	 * Return the instance of {@link org.slf4j.IMarkerFactory} that {@link org.slf4j.MarkerFactory} class should bind to.
	 *
	 * @return instance of {@link org.slf4j.IMarkerFactory}
	 */
	@Override public IMarkerFactory getMarkerFactory() {
		return null;
	}

	/**
	 * Return the instance of {@link org.slf4j.spi.MDCAdapter} that {@link org.slf4j.MDC} should bind to.
	 *
	 * @return instance of {@link org.slf4j.spi.MDCAdapter}
	 */
	@Override public MDCAdapter getMDCAdapter() {
		return null;
	}

	/**
	 * Return the maximum API version for SLF4J that the logging implementation supports.
	 *
	 * <p>For example: {@code "2.0.1"}.
	 *
	 * @return the string API version.
	 */
	@Override public String getRequestedApiVersion() {
		return "2.0.1";
	}

	/**
	 * Initialize the logging back-end.
	 *
	 * <p><b>WARNING:</b> This method is intended to be called once by
	 * {@link org.slf4j.LoggerFactory} class and from nowhere else.
	 */
	@Override public void initialize() {}
}
