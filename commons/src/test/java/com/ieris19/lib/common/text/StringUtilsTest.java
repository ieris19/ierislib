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

package com.ieris19.lib.common.text;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StringUtilsTest {
    //------------------------------------------------------------------------------------------------------------------
    // CASING
    //------------------------------------------------------------------------------------------------------------------
    @Test
    void titleCase() {
        String test = "thIs is a tEst sTrinG";
        String expected = "This Is A Test String";
        assertEquals(expected, StringUtils.titleCase(test));
    }

    //------------------------------------------------------------------------------------------------------------------
    // CONCATENATION
    //------------------------------------------------------------------------------------------------------------------
    @Test
    void listOut3() {
        String expected = "Cat, Dog and Bird";
        assertEquals(expected, StringUtils.listOut(", ", " and ", "Cat", "Dog", "Bird"));
    }

    @Test
    void listOut2() {
        String expected = "Cat and Dog";
        assertEquals(expected, StringUtils.listOut(", ", " and ", "Cat", "Dog"));
    }

    @Test
    void listOut1() {
        String expected = "Cat";
        assertEquals(expected, StringUtils.listOut(", ", " and ", "Cat"));
    }

    @Test
    void listOut0() {
        String expected = "";
        assertEquals(expected, StringUtils.listOut(", ", " and "));
    }

    @Test
    void concatenate() {
        String expected = "Cat and Dog and Bird";
        assertEquals(expected, StringUtils.concatenate(" and ", "Cat", "Dog", "Bird"));
    }

    @Test
    void enumerate() {
        String expected = "Cat, Dog and Bird";
        assertEquals(expected, StringUtils.enumerate("Cat", "Dog", "Bird"));
    }

    @Test
    void enumerateList() {
        List<String> list = new ArrayList<>();
        list.add("Cat");
        list.add("Dog");
        list.add("Bird");
        list.add("Fish");
        list.add("Mouse");
        String expected = "Cat, Dog, Bird, Fish and Mouse";
        assertEquals(expected, StringUtils.listOut(", ", " and ", list));
    }

    //------------------------------------------------------------------------------------------------------------------
    // LENGTH
    //------------------------------------------------------------------------------------------------------------------
    @Test
    void trim() {
        String provided = "   supercalifragilisticexpialidocious   ";
        String expected = "super";
        assertEquals(expected, StringUtils.trim(provided, 5));
    }

    @Test
    void trimShort() {
        String provided = "cat";
        assertEquals("cat", StringUtils.trim(provided, 50));
    }

    @Test
    void trimReversed() {
        String provided = "   supercalifragilisticexpialidocious   ";
        String expected = "expialidocious";
        assertEquals(expected, StringUtils.trim(provided, -14));
    }

    @Test
    void trimReversedShort() {
        String provided = "cat";
        assertEquals("cat", StringUtils.trim(provided, -50));
    }

    @Test
    void padRightSpace() {
        String expected = "cat       ";
        assertEquals(expected, StringUtils.padRight("cat", 10, ' '));
    }

    @Test
    void padRightOverflow() {
        String provided = "supercalifragilisticexpialidocious";
        String expected = "supercalifragilisticexpialidocious";
        assertEquals(expected, StringUtils.padRight(provided, 15, ' '));
    }

    @Test
    void padLeftSpace() {
        String expected = "       cat";
        assertEquals(expected, StringUtils.padLeft("cat", 10, ' '));
    }

    @Test
    void padLeftOverflow() {
        String provided = "supercalifragilisticexpialidocious";
        String expected = "supercalifragilisticexpialidocious";
        assertEquals(expected, StringUtils.padLeft(provided, 15, ' '));
    }

    @Test
    void fixedLength() {
        String expected = "cat       ";
        assertEquals(expected, StringUtils.fixedLength("cat", 10, ' ', false));
    }

    @Test
    void fixedLengthReversed() {
        String expected = "       cat";
        assertEquals(expected, StringUtils.fixedLength("cat", 10, ' ', true));
    }

    @Test
    void fixedLengthCut() {
        String expected = "cat";
        assertEquals(expected, StringUtils.fixedLength("cat, mouse and dog", 3, ' ', false));
    }
}