package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.util.Map;

interface CRUDInterface {
    Meal create(Meal meal);

    Meal read(int id);

    void update(int id, Meal meal);

    void delete(int id);

    Map<Integer, Meal> getAll();

}
