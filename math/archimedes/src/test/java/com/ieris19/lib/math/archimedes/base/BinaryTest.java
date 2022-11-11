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
			return outputFail(testName, "Failed with exception: " + e.getClass().getSimpleName());
		}
		if (!output.equals(correctAnswer))
			return outputFail(testName, "Expected output " + correctAnswer + " but got " + output);
		else
			return outputPass(testName);
	}

	private static boolean outputPass(String testName) {
		System.out.println("[Pass " + testName + "]");
		return true;
	}

	private static boolean outputFail(String testName, String message) {
		System.out.println("[FAIL " + testName + "] " + message);
		return false;
	}
}