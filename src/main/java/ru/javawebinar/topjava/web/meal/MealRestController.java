package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

@Controller
public class MealRestController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    private final MealService service;

    @Autowired
    public MealRestController(MealService service) {
        this.service = service;
    }

    public List<MealWithExceed> getAll() {
        int userId = SecurityUtil.authUserId();
        log.info("getAll");
        return MealsUtil.getWithExceeded(service.getAll(userId), SecurityUtil.authUserCaloriesPerDay());
    }

    public Meal get(int id) {
        int userId = SecurityUtil.authUserId();
        log.info("get {}", id);
        return service.get(id, userId);
    }

    public Meal create(Meal meal) {
        int userId = SecurityUtil.authUserId();
        log.info("create {}", meal);
        checkNew(meal);
        return service.create(meal, userId);
    }

    public void delete(int id) {
        int userId = SecurityUtil.authUserId();
        log.info("delete {}", id);
        service.delete(id, userId);
    }

    public void update(Meal meal, int id) {
        int userId = SecurityUtil.authUserId();
        log.info("update {} with id={}", meal, id);
        assureIdConsistent(meal, id);
        service.update(meal, userId);
    }

    public List<MealWithExceed> getFiltered(LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime) {
        int userId = SecurityUtil.authUserId();
        log.info("getAll");
        if (startDate == null) {
            log.info("startDate is null");
            startDate = LocalDate.MIN;
        }
        if (startTime == null) {
            log.info("startTime is null");
            startTime = LocalTime.MIN;
        }
        if (endDate == null) {
            log.info("endDate is null");
            endDate = LocalDate.MAX;
        }
        if (endTime == null) {
            log.info("endTime is null");
            endTime = LocalTime.MAX;
        }
        return MealsUtil.getWithExceeded(service.getBetweenDateTimes(LocalDateTime.of(startDate, startTime), LocalDateTime.of(endDate, endTime), userId), SecurityUtil.authUserCaloriesPerDay());
    }

}