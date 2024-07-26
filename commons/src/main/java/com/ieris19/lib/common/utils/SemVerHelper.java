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

package com.ieris19.lib.common.utils;

import java.util.Arrays;

public class SemVerHelper {
    public record SemVer(int major, int minor, int patch) {
        public boolean isEqualTo(SemVer other) {
            return this.equals(other);
        }

        public boolean isGreaterThan(SemVer other) {
            return this.major > other.major || this.minor > other.minor || this.patch > other.patch;
        }

        public boolean isLessThan(SemVer other) {
            return this.major < other.major || this.minor < other.minor || this.patch < other.patch;
        }

        public boolean isGreaterOrEqualTo(SemVer other) {
            return this.isEqualTo(other) || this.isGreaterThan(other);
        }

        public boolean isLessOrEqualTo(SemVer other) {
            return this.isEqualTo(other) || this.isLessThan(other);
        }

        public boolean isCompatibleWith(SemVer other) {
            return this.major == other.major && this.minor == other.minor;
        }

        @Override
        public String toString() {
            return major + "." + minor + "." + patch;
        }
    }

    public static SemVer parseVersion(String version) {
        int[] array =  Arrays.stream(version.split("\\."))
                .mapToInt(Integer::parseInt)
                .toArray();
        if (array.length != 3) {
            throw new IllegalArgumentException("Invalid version format");
        }
        return new SemVer(array[0], array[1], array[2]);
    }
}
