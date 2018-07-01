package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class UserDao implements CRUDInterface {

    private static final List<Meal> meals;
    private static final AtomicInteger counter = new AtomicInteger(0);

    static {
        meals = new CopyOnWriteArrayList<>(Arrays.asList(
                new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500, 1),
                new Meal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000, 2),
                new Meal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500, 3),
                new Meal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000, 4),
                new Meal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500, 5),
                new Meal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510, 6)));
    }

    public static List<Meal> getMeals() {
        return meals;
    }

    @Override
    public void create(String dateTime, String description, String calories) {
        DateTimeFormatter fmt = new DateTimeFormatterBuilder()
                .appendPattern("yyyy-MM-dd HH:mm")
                .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
                .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
                .toFormatter();
        //thread-safe counter
        meals.add(new Meal(LocalDateTime.parse(dateTime, fmt), description, Integer.parseInt(calories), counter.incrementAndGet()));

    }
    @Override
    public Meal read(String id) {
        return meals.get(Integer.parseInt(id));
    }

    @Override
    public void update(String id, String dateTime, String description, String calories) {
        delete(id);
        create(dateTime, description, calories);
    }

    @Override
    public void delete(String id) {
        meals.remove(Integer.parseInt(id));
    }

}
