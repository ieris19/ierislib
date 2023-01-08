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

import com.ieris19.lib.util.log.common.TimestampHandler;
import org.junit.jupiter.api.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

@DisplayName ("Time Log Test")
class TimestampHandlerTest {
	private static TimestampHandler timestamp;

	@BeforeAll @DisplayName ("Setup") static void setup() {
		timestamp = TimestampHandler.getInstance();
	}

	@Nested
	@DisplayName ("Singleton Test")
	class SingletonTest {
		@Test @DisplayName ("A single instance exists") void instanceTest() {
			assertSame(timestamp, TimestampHandler.getInstance());
			TimestampHandler time1 = TimestampHandler.getInstance();
			assertSame(timestamp, time1);
			TimestampHandler time2 = TimestampHandler.getInstance();
			assertSame(timestamp, time2);
		}
	}

	@Nested
	@DisplayName ("Proper Formatting")
	class FormatterTest {
		@Test @DisplayName ("European Proper Format") void europeanFormat() {
			LocalDateTime time = LocalDateTime.of(2022, 2, 5, 21, 21, 21);
			assertEquals(time.format(ChronoFormat.EUROPEAN.get()), "05/02/2022 21:21:21");
		}

		@Test @DisplayName ("ISO Proper Format") void isoFormat() {
			LocalDateTime time = LocalDateTime.of(2022, 2, 5, 21, 21, 21);
			assertEquals(time.format(ChronoFormat.ISO.get()), "2022-02-05T21:21:21");
		}
	}

	@Nested
	@DisplayName ("Time Selection")
	class TimeAccuracyTest {
		@Test @DisplayName ("Accurate Time Test") void accuracy() {
			DateTimeFormatter comparisonFormat = DateTimeFormatter.ofPattern("dd,MM,yyyy,hh,mm,ss");
			Assertions.assertEquals(LocalDateTime.now(ZoneId.of("Z")).format(comparisonFormat),
															timestamp.getTime().format(comparisonFormat));
		}
	}
}