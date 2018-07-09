package ru.javawebinar.topjava.web;

import static ru.javawebinar.topjava.util.MealsUtil.DEFAULT_CALORIES_PER_DAY;

public class SecurityUtil {

    private SecurityUtil() {
        throw new UnsupportedOperationException("Utility class, can't be instantiated");
    }

    private static int authUserId;

    public static void setAuthUserId(int userId) {
        authUserId = userId;
    }

    public static int authUserId() {
        return authUserId;
    }

    public static int authUserCaloriesPerDay() {
        return DEFAULT_CALORIES_PER_DAY;
    }
}