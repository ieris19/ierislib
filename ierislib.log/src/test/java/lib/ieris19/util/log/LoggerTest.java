package lib.ieris19.util.log;

import lib.ieris19.util.log.ieris.IerisLog;
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
		Runnable threadSpawnLogger = () -> {
			log.success("This element was logged from a different thread");
		};
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