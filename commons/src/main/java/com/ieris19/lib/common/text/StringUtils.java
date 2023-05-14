package com.ieris19.lib.common.text;

import java.util.Collection;
import java.util.List;

public class StringUtils {
	public static String titleCase(String str) {
		String[] words = str.split(" ");
		StringBuilder builder = new StringBuilder();
		for (String word : words) {
			builder.append(word.substring(0, 1).toUpperCase()).append(word.substring(1)).append(" ");
		}
		return builder.toString().trim();
	}

	public static String uppercaseFirst(String str) {
		return str.substring(0, 1).toUpperCase() + str.substring(1);
	}

	public static String lowercaseFirst(String str) {
		return str.substring(0, 1).toLowerCase() + str.substring(1);
	}

	public static String concatList(List<?> objects, String separator, String finalizer) {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < objects.size() - 1; i++) {
			builder.append(objects.get(i).toString()).append(separator);
		}
		builder.append(objects.get(objects.size() - 1).toString()).append(finalizer);
		return builder.toString();
	}

	public static String enumerateList(List<?> objects) {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < objects.size() - 1; i++) {
			builder.append(objects.get(i).toString()).append(", ");
		}
		builder.append(" and ").append(objects.get(objects.size() - 1).toString());
		return builder.toString();
	}
}
