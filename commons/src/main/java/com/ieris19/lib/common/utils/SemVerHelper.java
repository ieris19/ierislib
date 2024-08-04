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
