package com.ieris19.lib.util.log;

import com.ieris19.lib.util.log.custom.CustomLog;
import com.ieris19.lib.util.log.custom.LogBuilder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CustomLogTest {
	private static final String LOG_NAME = "Custom-Test";

	@Test @DisplayName ("Template Loggers") void templateTest() {
		CustomLog log = LogBuilder.template("default").build(LOG_NAME);
		log.setLogLevel(Level.TRACE.value());
		log.info("This message is a generic log");
		log = LogBuilder.template("minimal").build(LOG_NAME);
		log.info("This message is a minimal log");
		log = LogBuilder.template("complete").build(LOG_NAME);
		log.info("This message is a complete log");
	}
}
