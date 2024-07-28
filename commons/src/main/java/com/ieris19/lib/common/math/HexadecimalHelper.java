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

package com.ieris19.lib.common.math;

import java.util.Arrays;
import java.util.HexFormat;

public class HexadecimalHelper {
    public static String bytesToHex(byte[] bytes) {
       return HexFormat.of().formatHex(bytes).toUpperCase();
    }

    public static byte[] bytesFromHex(String hex) {
        return HexFormat.of().parseHex(hex.toUpperCase());
    }
}
