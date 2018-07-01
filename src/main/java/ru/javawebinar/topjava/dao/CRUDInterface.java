package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

interface CRUDInterface {
    void create(String dateTime, String description, String calories);

    Meal read(String id);

    void update(String id, String dateTime, String description, String calories);

    void delete(String id);

}
