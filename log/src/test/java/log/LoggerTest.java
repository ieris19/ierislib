package log;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertTrue;

class LoggerTest {
	@Test @DisplayName ("Printer Test") void logPrinterTest() {
		Log log = Log.getInstance();
		TimestampHandler timestamp = TimestampHandler.getInstance();
		log.log("This message is a generic log");
		log.success("This message is a success");
		log.info("This message is informational");
		log.warning("This message is a warning");
		log.error("This message was an error");
		log.fatal("This message is a fatal error");
		assertTrue(new File("C:/Users/Ieris19/Logs/",
												"Log - " + timestamp.getFormattedDate() + ".log").exists());
	}
}