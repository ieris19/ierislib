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

package com.ieris19.lib.reflection.util;

import com.ieris19.lib.reflection.util.targets.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class ClassUtilsTest {
    private static Package givenPackage;
    private static Class<?>[] targetClasses;
    @BeforeAll
    public static void setUp() {
        givenPackage = TargetClass.class.getPackage();
        targetClasses = new Class<?>[]{
                TargetClass.class,
                TargetInterface.class,
                TargetEnum.class,
                TargetRecord.class,
                TargetAnnotation.class
        };
        assertNotNull(givenPackage);
    }

    @Test
    public void getClassesFromDummyPackage() {
        try {
            Set<Class<?>> result = ClassUtils.getClassesInPackage(givenPackage);
            for (Class<?> targetClass : targetClasses) {
                assertTrue(result.contains(targetClass));
            }
        } catch (Exception e) {
            fail("Exception thrown when scanning package: " + givenPackage.getName());
        }
    }

    @Test
    public void packageClassesNotNull() {
        try {
            Set<Class<?>> result = ClassUtils.getClassesInPackage(givenPackage);
            assertFalse(result.contains(null));
        } catch (Exception e) {
            fail("Exception thrown when scanning package: " + givenPackage.getName());
        }
    }
}
