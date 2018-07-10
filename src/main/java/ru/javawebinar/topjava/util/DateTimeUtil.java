package ru.javawebinar.topjava.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private DateTimeUtil() {
        throw new UnsupportedOperationException("Utility class, can't be instantiated");
    }

    public static <T extends Comparable> boolean isBetween(T lt, T startTime, T endTime) {
        return lt.compareTo(startTime) >= 0 && lt.compareTo(endTime) <= 0;
    }

    public static String toString(LocalDateTime ldt) {
        return ldt == null ? "" : ldt.format(DATE_TIME_FORMATTER);
    }

    public static LocalDate parseLocalDate(String s) {
        return s.isEmpty() ? null : LocalDate.parse(s);
    }

    public static LocalTime parseLocalTime(String s) {
        return s.isEmpty() ? null : LocalTime.parse(s);
    }
}
