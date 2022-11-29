package com.ieris19.lib.math.archimedes.base;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName ("Binary Numbers Utilities Test")
class BinaryTest {
	@Nested
	@DisplayName ("Converter")
	class Converter {

		@Test @DisplayName ("Conversion to Binary") void test() {
			String testName = "Conversion to Binary";
			assertTrue(runTest(17, "+10001", "Test 1: " + testName));
			assertTrue(runTest(19, "+10011", "Test 2: " + testName));
			assertTrue(runTest(147, "+10010011", "Test 3: " + testName));
			assertTrue(runTest(347, "+101011011", "Test 4: " + testName));
			assertTrue(runTest(122, "+1111010", "Test 5: " + testName));
			assertTrue(runTest(127, "+1111111", "Test 6: " + testName));
		}
	}

	private static boolean runTest(int input, String correctAnswer, String testName) {
		String output;
		try {
			output = Binary.convertDecimal(input);
		} catch (Exception e) {
			System.out.println("[FAIL " + testName + "] " + "Failed with exception: " + e.getClass().getSimpleName());
			return false;
		}
		if (!output.equals(correctAnswer)) {
			System.out.println("[FAIL " + testName + "] " + "Expected output " + correctAnswer + " but got " + output);
			return false;
		} else {
			System.out.println("[Pass " + testName + "]");
			return true;
		}
	}
}