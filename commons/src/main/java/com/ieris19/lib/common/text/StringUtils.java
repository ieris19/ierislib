package com.ieris19.lib.common.text;

import java.util.Arrays;
import java.util.List;

/**
 * A class that provides utility methods for operating with Strings
 */
public class StringUtils {
    //------------------------------------------------------------------------------------------------------------------
    // CASING
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Returns a string where the first character of each word is capitalized and the rest are lowercase
     *
     * @param str The string to title case
     * @return The title cased string
     */
    public static String titleCase(String str) {
        char[] string = str.toCharArray();
        string[0] = Character.toUpperCase(string[0]);
        for (int i = 1; i < string.length; i++) {
            if (Character.isWhitespace(string[i - 1])) {
                string[i] = Character.toUpperCase(string[i]);
            } else {
                string[i] = Character.toLowerCase(string[i]);
            }
        }
        return new String(string);
    }

    //------------------------------------------------------------------------------------------------------------------
    // CONCATENATION
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Returns a string where the varargs are concatenated with the separator in between them and the finalizer
     * separating the last element from the rest
     *
     * @param separator The separator to use between the elements
     * @param finalizer The finalizer to use between the last element and the rest
     * @param args      The elements to concatenate
     * @return The concatenated string
     */
    public static String listOut(String separator, String finalizer, String... args) {
        return switch (args.length) {
            case 0 -> "";
            case 1 -> args[0];
            case 2 -> args[0] + finalizer + args[1];
            default -> {
                StringBuilder builder = new StringBuilder(args[0]);
                for (int i = 1; i < args.length - 1; i++) {
                    builder.append(separator).append(args[i]);
                }
                builder.append(finalizer).append(args[args.length - 1]);
                yield builder.toString();
            }
        };
    }

    /**
     * Returns a string where the list is converted to Strings and {@link #listOut(String, String, String...)}
     * is called with the given parameters to make a list in natural language.
     *
     * @param objects The elements to concatenate
     * @return A natural language list of the elements
     * @see #listOut(String, String, String...)
     */
    public static String listOut(String separator, String finalizer, List<?> objects) {
        Object[] listArray = objects.toArray(new Object[0]);
        Arrays.stream(listArray).forEach(o -> o = o.toString());
        String[] varargs = Arrays.copyOf(listArray, listArray.length, String[].class);
        return listOut(separator, finalizer, varargs);
    }

    /**
     * A simplified version of {@link #listOut(String, String, String...)} that uses the same string for the
     * separator and the finalizer
     *
     * @param separator The separator to use between the elements
     * @param args      The elements to concatenate
     * @return The concatenated string
     */
    public static String concatenate(String separator, String... args) {
        return listOut(separator, separator, args);
    }

    /**
     * List out the varargs in natural language, using a comma to separate all but the last element using the word "and"
     * there instead
     *
     * @param args The elements to concatenate
     * @return A natural language list of the elements
     */
    public static String enumerate(String... args) {
        return listOut(", ", " and ", args);
    }

    //------------------------------------------------------------------------------------------------------------------
    // LENGTH
    //------------------------------------------------------------------------------------------------------------------
    public static String trim(String string, int length) {
        if (length > string.length() || length < string.length() * -1)
            return string;
        if (length < 0) {
            String trimmed = string.trim();
            return trimmed.substring(trimmed.length() + length);
        }
        return string.trim().substring(0, length);
    }

    public static String padRight(String string, int length, char filler) {
        return string + String.valueOf(filler).repeat(Math.max(0, length - string.length()));
    }

    public static String padLeft(String string, int length, char filler) {
        return String.valueOf(filler).repeat(Math.max(0, length - string.length())) + string;
    }

    public static String fixedLength(String str, int desiredLength, char filler, boolean reversed) {
        if (reversed) {
            return padLeft(trim(str, -1 * desiredLength), desiredLength, filler);
        } else {
            return padRight(trim(str, desiredLength), desiredLength, filler);
        }
    }
}
