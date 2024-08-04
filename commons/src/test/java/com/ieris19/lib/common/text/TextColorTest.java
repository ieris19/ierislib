package com.ieris19.lib.common.text;

import org.junit.jupiter.api.Test;

public class TextColorTest {
    @Test
    public void testFormatter() {
        String test = "test";
        String result = AnsiColorCodes.format(test, AnsiColorCodes.RED);
        String expected = "\u001B[31mtest\u001B[0m";
        assert result.equals(expected);
    }

    @Test
    public void testFormatterSubsequent() {
        String test = "test";
        String middle = AnsiColorCodes.format(test, AnsiColorCodes.RED);
        String result = AnsiColorCodes.format(middle, AnsiColorCodes.GREEN);
        String expected = "\u001B[32m\u001B[31mtest\u001B[0m\u001B[0m";
        assert result.equals(expected);
    }
}
