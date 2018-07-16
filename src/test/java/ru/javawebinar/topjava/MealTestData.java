package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class MealTestData {

    public static final List<Meal> MEALS = Collections.unmodifiableList(Arrays.asList(
            new Meal(100002, LocalDateTime.parse("2015-05-30T10:00:00"), "Завтрак", 500),
            new Meal(100003, LocalDateTime.parse("2015-05-30T13:00:00"), "Обед", 1000),
            new Meal(100004, LocalDateTime.parse("2015-05-30T20:00:00"), "Ужин", 500),
            new Meal(100005, LocalDateTime.parse("2015-05-31T10:00:00"), "Завтрак", 1000),
            new Meal(100006, LocalDateTime.parse("2015-05-31T13:00:00"), "Обед", 500),
            new Meal(100007, LocalDateTime.parse("2015-05-31T20:00:00"), "Ужин", 510))
    );

    public static void assertMatch(Meal expected, Meal actual) {
        assertThat(expected).isEqualToComparingFieldByField(actual);
    }

    public static void assertMatch(Iterable<Meal> expected, Iterable<Meal> actual) {
        assertThat(expected).usingFieldByFieldElementComparator().isEqualTo(actual);
    }
}

