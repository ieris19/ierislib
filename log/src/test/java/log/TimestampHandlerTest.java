package log;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import static log.TimestampHandler.ChronoFormat.EUROPEAN;
import static log.TimestampHandler.ChronoFormat.ISO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

@DisplayName ("Time Log Test") class TimestampHandlerTest {
	private static TimestampHandler timestamp;

	@BeforeAll @DisplayName ("Setup") static void setup() {
		timestamp = TimestampHandler.getInstance();
	}

	@Nested @DisplayName ("Singleton Test") class SingletonTest {
		@Test @DisplayName ("A single instance exists") void instanceTest() {
			assertSame(timestamp, TimestampHandler.getInstance());
			TimestampHandler time1 = TimestampHandler.getInstance();
			assertSame(timestamp, time1);
			TimestampHandler time2 = TimestampHandler.getInstance();
			assertSame(timestamp, time2);
		}
	}

	@Nested @DisplayName ("Proper Formatting") class FormatterTest {
		@Test @DisplayName ("European Proper Format") void europeanFormat() {
			LocalDateTime time = LocalDateTime.of(2022, 2, 5, 21, 21, 21);
			assertEquals(time.format(EUROPEAN.format()), "05/02/2022 21:21:21");
		}

		@Test @DisplayName ("ISO Proper Format") void isoFormat() {
			LocalDateTime time = LocalDateTime.of(2022, 2, 5, 21, 21, 21);
			assertEquals(time.format(ISO.format()), "2022-02-05T21:21:21");
		}
	}

	@Nested @DisplayName ("Time Selection") class TimeAccuracyTest {
		@Test @DisplayName ("Accurate Time Test") void accuraccy() {
			DateTimeFormatter comparisonFormat = DateTimeFormatter.ofPattern("dd,MM,yyyy,hh,mm,ss");
			assertEquals(LocalDateTime.now(ZoneId.of("Z")).format(comparisonFormat),
									 timestamp.getTime().format(comparisonFormat));
		}
	}
}