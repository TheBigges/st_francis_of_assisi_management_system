package com.calapi.st.francis.assisi.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;

import io.micrometer.common.util.StringUtils;

public class CommonUtils {

	private final static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy");

	public static String nullSafe(String value) {
		return value != null ? value : "";
	}

	public static String nullSafeNumber(Number value) {
		return value != null ? value.toString() : "";
	}

	public static String joinNames(String... parts) {
		return Arrays.stream(parts).filter(Objects::nonNull).filter(s -> !s.isBlank()).collect(Collectors.joining(" "));
	}

	public static String formatDate(LocalDate date) {
		return date != null ? date.format(formatter) : "";
	}

	public static String toUpper(String value) {
		return value != null ? value.toUpperCase() : "";
	}

	public static String getOrdinalDay(LocalDate date) {
		Objects.requireNonNull(date, "date must not be null");
		int day = date.getDayOfMonth();
		if (day >= 11 && day <= 13) {
			return day + "th";
		}
		return switch (day % 10) {
		case 1 -> day + "st";
		case 2 -> day + "nd";
		case 3 -> day + "rd";
		default -> day + "th";
		};
	}

	public static String getMonthName(LocalDate date) {
		Objects.requireNonNull(date, "date must not be null");
		return date.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH);
	}

	public static String getYear(LocalDate date) {
		Objects.requireNonNull(date, "date must not be null");
		return String.valueOf(date.getYear());
	}

	public static String extractLastToken(String input) {
		Objects.requireNonNull(input, "input must not be null");
		String[] tokens = input.split(",");
		for (int i = tokens.length - 1; i >= 0; i--) {
			String trimmed = tokens[i].trim();
			if (!trimmed.isEmpty()) {
				return trimmed;
			}
		}
		throw new IllegalArgumentException("No valid tokens found in input");
	}

	public static String removeLastToken(String input) {
		Objects.requireNonNull(input, "input must not be null");
		String trimmedInput = input.trim();
		int lastCommaIndex = trimmedInput.lastIndexOf(',');
		if (lastCommaIndex < 0) {
			throw new IllegalArgumentException("Input does not contain multiple tokens");
		}
		return trimmedInput.substring(0, lastCommaIndex).trim();
	}

	public static List<String> getFirstTwoTokens(String input) {
		if (StringUtils.isBlank(input)) {
			return List.of();
		}
		List<String> tokens = Arrays.stream(input.split(",")).map(String::trim).filter(s -> !s.isEmpty()).limit(2)
				.collect(Collectors.toList());
		if (tokens.size() < 2) {
			throw new IllegalArgumentException("Input must contain at least two non-blank tokens");
		}
		return tokens;
	}

}
