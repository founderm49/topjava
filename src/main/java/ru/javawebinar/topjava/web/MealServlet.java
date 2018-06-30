package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalTime;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private static int caloriesPerDay = 2000;
    private static final List<MealWithExceed> mealList = MealsUtil.getFilteredWithExceeded(MealsUtil.getMeals(), LocalTime.MIN, LocalTime.MAX, caloriesPerDay);


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.debug("redirect to meals");

        request.setAttribute("meals", mealList);

        try {
            request.getRequestDispatcher("/meals.jsp").forward(request, response);
        } catch (ServletException e) {
            e.printStackTrace();
        }
        response.sendRedirect("meals.jsp");

    }
}
