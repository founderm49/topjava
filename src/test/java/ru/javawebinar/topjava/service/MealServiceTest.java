package ru.javawebinar.topjava.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.MealTestData.assertMatch;
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

    private List<Meal> mealList;

    @Before
    public void setUp() {
        mealList = new ArrayList<>(MealTestData.MEALS);
    }

    @Test
    public void create() {
        Meal meal = new Meal(LocalDateTime.now(), "Test meal", 9999);
        service.create(meal, USER_ID);
        mealList.add(meal);
        Collections.sort(mealList, Comparator.comparing(Meal::getDateTime).reversed());
        assertMatch(mealList, service.getAll(USER_ID));

    }

    @Test
    public void get() {
        Meal meal = service.get(100002, USER_ID);
        assertMatch(mealList.get(0), meal);
    }

    @Test(expected = NotFoundException.class)
    public void getNegative() {
        service.get(100002, ADMIN_ID);
    }

    @Test
    public void delete() {
        service.delete(100002, USER_ID);
        mealList.remove(0);
        Collections.sort(mealList, Comparator.comparing(Meal::getDateTime).reversed());
        assertMatch(mealList, service.getAll(USER_ID));

    }

    @Test(expected = NotFoundException.class)
    public void deleteNegative() {
        service.delete(100002, ADMIN_ID);
    }

    @Test
    public void getBetweenDateTimes() {
        LocalDateTime startDateTime = LocalDateTime.parse("2015-05-31T00:00:00");
        LocalDateTime endDateTime = LocalDateTime.parse("2015-05-31T14:00:00");
        List<Meal> expectedMeals = mealList.stream()
                .filter(meal -> DateTimeUtil.isBetween(meal.getDateTime(), startDateTime, endDateTime))
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
        assertMatch(expectedMeals, service.getBetweenDateTimes(startDateTime, endDateTime, USER_ID));
    }

    @Test
    public void getAll() {
        Collections.sort(mealList, Comparator.comparing(Meal::getDateTime).reversed());
        assertMatch(mealList, service.getAll(USER_ID));
    }

    @Test
    public void update() {
        Meal expected = new Meal(mealList.get(0));
        expected.setCalories(9999);
        Meal meal = service.get(100002, USER_ID);
        meal.setCalories(9999);
        service.update(meal, USER_ID);
        assertMatch(expected, service.get(100002, USER_ID));
    }

    @Test(expected = NotFoundException.class)
    public void updateNegative() {
        Meal expected = new Meal(mealList.get(0));
        expected.setCalories(9999);
        Meal meal = service.get(100002, USER_ID);
        meal.setCalories(9999);
        service.update(meal, ADMIN_ID);
        assertMatch(expected, service.get(100002, USER_ID));
    }

}