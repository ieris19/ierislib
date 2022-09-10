package lib.ieris19.util.log;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CustomLogTest {
	private static final String LOG_NAME = "Custom-Test";

	@Test @DisplayName ("Template Loggers") void templateTest() {
		CustomLog log = LogBuilder.template("default");
		log.setName(LOG_NAME);
		log.info("This message is a generic log");
		log = LogBuilder.template("minimal");
		log.setName(LOG_NAME);
		log.info("This message is a minimal log");
		log = LogBuilder.template("complete");
		log.setName(LOG_NAME);
		log.info("This message is a complete log");
	}
}
