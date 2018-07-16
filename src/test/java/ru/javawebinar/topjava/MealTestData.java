package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {

    public static final int MEAL_ID = START_SEQ + 2;

    public static final Meal MEAL1 = new Meal(MEAL_ID, LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500);
    public static final Meal MEAL2 = new Meal(MEAL_ID + 1, LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000);
    public static final Meal MEAL3 = new Meal(MEAL_ID + 2, LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500);
    public static final Meal MEAL4 = new Meal(MEAL_ID + 3, LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000);
    public static final Meal MEAL5 = new Meal(MEAL_ID + 4, LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500);
    public static final Meal MEAL6 = new Meal(MEAL_ID + 5, LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510);


    public static final Meal MEAL_CREATED = new Meal(null, LocalDateTime.of(2018, Month.JULY, 16, 20, 0), "Test meal", 9999);
    public static final Meal MEAL_UPDATED = new Meal(MEAL_ID, LocalDateTime.of(2018, Month.MAY, 30, 10, 0), "Завтрак #upd", 50);


    public static final List<Meal> MEALS = Arrays.asList(MEAL6, MEAL5, MEAL4, MEAL3, MEAL2, MEAL1);

    public static void assertMatch(Meal expected, Meal actual) {
        assertThat(expected).isEqualToComparingFieldByField(actual);
    }

    public static void assertMatch(Iterable<Meal> expected, Iterable<Meal> actual) {
        assertThat(expected).usingFieldByFieldElementComparator().isEqualTo(actual);
    }
}

