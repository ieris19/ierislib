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

import com.ieris19.lib.util.log.Log;
import com.ieris19.lib.util.log.ieris.IerisLog;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;

public class IerisLogFactory implements ILoggerFactory {

	/**
	 * Return an appropriate {@link Logger} instance as specified by the
	 * <code>name</code> parameter.
	 *
	 * <p>If the name parameter is equal to {@link Logger#ROOT_LOGGER_NAME}, that is
	 * the string value "ROOT" (case insensitive), then the root logger of the underlying logging system is returned.
	 *
	 * <p>Null-valued name arguments are considered invalid.
	 *
	 * <p>Certain extremely simple logging systems, e.g. NOP, may always
	 * return the same logger instance regardless of the requested name.
	 *
	 * @param name the name of the Logger to return
	 *
	 * @return a Logger instance
	 */
	@Override public Logger getLogger(String name) {
		return new IerisLogAdapter(IerisLog.getInstance(name));
	}
}
