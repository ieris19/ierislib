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

package com.ieris19.lib.util.log;

import com.ieris19.lib.util.log.core.IerisLog;
import com.ieris19.lib.util.log.core.Log;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;

class LoggerTest {
	private Log log;
	private static File logFile;

	@BeforeEach void setup() {
		this.log = IerisLog.getInstance("Test");
		try {
			logFile = log.getLogFile();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Test @DisplayName ("Printer Test") void logPrinterTest() {
		log.trace("This message is a generic log");
		log.success("This message is a success");
		log.info("This message is informational");
		log.warning("This message is a warning");
		log.error("This message was an error");
		log.fatal("This message is a fatal error");
		assertTrue(logFile.exists());
	}

	@Test @DisplayName ("Thread Name Test") void threadTester() {
		Runnable threadSpawnLogger = () -> log.success("This element was logged from a different thread");
		String[] names = {"Server", "Worker", "Reader", "Writer", "UI"};
		for (String name : names) {
			Thread x = new Thread(threadSpawnLogger, name);
			x.start();
			try {
				x.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}