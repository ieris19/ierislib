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
