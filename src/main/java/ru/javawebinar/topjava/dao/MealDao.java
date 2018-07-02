package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MealDao implements CRUDInterface {

    private static final Map<Integer, Meal> mealsMap;
    private static final AtomicInteger counter = new AtomicInteger(0);

    static {
        mealsMap = new ConcurrentHashMap<>();
        mealsMap.put(counter.incrementAndGet(), new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500, counter.get()));
        mealsMap.put(counter.incrementAndGet(), new Meal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000, counter.get()));
        mealsMap.put(counter.incrementAndGet(), new Meal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500, counter.get()));
        mealsMap.put(counter.incrementAndGet(), new Meal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000, counter.get()));
        mealsMap.put(counter.incrementAndGet(), new Meal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500, counter.get()));
        mealsMap.put(counter.incrementAndGet(), new Meal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510, counter.get()));
    }

    @Override
    public Map<Integer, Meal> getAll() {
        return mealsMap;
    }

    @Override
    public Meal create(Meal meal) {
        int id = counter.incrementAndGet();
        mealsMap.put(id, meal);
        meal.setId(id);
        return meal;
    }

    @Override
    public Meal read(int id) {
        return mealsMap.get(id);
    }

    @Override
    public void update(int id, Meal meal) {
        delete(id);
        create(meal);
    }

    @Override
    public void delete(int id) {
        mealsMap.remove(id);
    }

}
