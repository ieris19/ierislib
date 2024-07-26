/*
 * Copyright 2024 Ieris19
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
