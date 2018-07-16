package ru.javawebinar.topjava.service;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
        // Only for postgres driver logging
        // It uses java.util.logging and logged via jul-to-slf4j bridge
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void create() {
        service.create(MEAL_CREATED, USER_ID);
        assertMatch(Arrays.asList(MEAL_CREATED, MEAL6, MEAL5, MEAL4, MEAL3, MEAL2, MEAL1), service.getAll(USER_ID));
    }

    @Test
    public void get() {
        Meal meal = service.get(MEAL_ID, USER_ID);
        assertMatch(MEAL1, meal);
    }

    @Test(expected = NotFoundException.class)
    public void getNotFound() {
        service.get(MEAL_ID, ADMIN_ID);
    }

    @Test
    public void delete() {
        service.delete(MEAL_ID, USER_ID);
        assertMatch(Arrays.asList(MEAL6, MEAL5, MEAL4, MEAL3, MEAL2), service.getAll(USER_ID));

    }

    @Test(expected = NotFoundException.class)
    public void deleteNotFound() {
        service.delete(MEAL_ID, ADMIN_ID);
    }

    @Test
    public void getBetweenDateTimes() {
        LocalDateTime startDateTime = LocalDateTime.parse("2015-05-31T00:00:00");
        LocalDateTime endDateTime = LocalDateTime.parse("2015-05-31T14:00:00");
        List<Meal> expectedMeals = MEALS.stream()
                .filter(meal -> DateTimeUtil.isBetween(meal.getDateTime(), startDateTime, endDateTime))
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
        assertMatch(expectedMeals, service.getBetweenDateTimes(startDateTime, endDateTime, USER_ID));
    }

    @Test
    public void getAll() {
        assertMatch(MEALS, service.getAll(USER_ID));
    }

    @Test
    public void update() {
        service.update(MEAL_UPDATED, USER_ID);
        assertMatch(MEAL_UPDATED, service.get(MEAL_ID, USER_ID));
    }

    @Test(expected = NotFoundException.class)
    public void updateNotFound() {
        service.update(MEAL_UPDATED, ADMIN_ID);
        assertMatch(MEAL_UPDATED, service.get(MEAL_ID, USER_ID));
    }

}