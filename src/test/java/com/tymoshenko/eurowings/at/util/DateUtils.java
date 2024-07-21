package com.tymoshenko.eurowings.at.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DateUtils {
    private static final String TEST_DATE_FORMAT = "dd.mm.yyyy";

    public static LocalDate toDate(String dateStr) {
        return toDate(dateStr, TEST_DATE_FORMAT, Locale.ENGLISH);
    }

    public static LocalDate toDate(String dateStr, String format, Locale locale) {
        return switch (dateStr) {
            case "tomorrow" -> LocalDate.now().plusDays(1);
            case "today" -> LocalDate.now();
            default -> LocalDate.parse(dateStr, DateTimeFormatter.ofPattern(format, locale));
        };
    }

    public static String toString(LocalDate date, String format) {
        return toString(date, format, Locale.ENGLISH);
    }

    public static String toString(LocalDate date, String format, Locale locale) {
        return date.format(DateTimeFormatter.ofPattern(format, locale));
    }
}
