package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 800),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
        );
        for (UserMealWithExceed meal : getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000)) {
            System.out.println(meal.toString());
        }
//        for (UserMealWithExceed meal : getFilteredWithExceededExtra(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000)) {
//            System.out.println(meal.toString());
//        }
    }

    //solution via streams
    public static List<UserMealWithExceed> getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, IntSummaryStatistics> map = mealList.stream()
                .collect(Collectors.groupingBy(p -> p.getDateTime().toLocalDate(), Collectors.summarizingInt(UserMeal::getCalories)));

        return mealList
                .stream()
                .filter(p -> (p.getDateTime().toLocalTime().isAfter(startTime)) && p.getDateTime().toLocalTime().isBefore(endTime))
                .map(p -> new UserMealWithExceed(p.getDateTime(), p.getDescription(), p.getCalories(), caloriesPerDay > map.get(p.getDateTime().toLocalDate()).getSum()))
                .collect(Collectors.toList());
    }

    //solution via loops
    public static List<UserMealWithExceed> getFilteredWithExceededExtra(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> dailyCaloriesMap = new HashMap<>();
        List<UserMealWithExceed> resultList = new ArrayList<>();

        for (UserMeal p : mealList) {
            dailyCaloriesMap.merge(p.getDateTime().toLocalDate(), p.getCalories(), Integer::sum);
        }

        for (UserMeal p : mealList) {
            if ((p.getDateTime().toLocalTime().isAfter(startTime)) && (p.getDateTime().toLocalTime().isBefore(endTime))) {
                resultList.add(new UserMealWithExceed(p.getDateTime(), p.getDescription(), p.getCalories(), caloriesPerDay > dailyCaloriesMap.get(p.getDateTime().toLocalDate())));
            }
        }
        return resultList;
    }


}
