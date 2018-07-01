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

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        log.debug("redirect to meals");
        request.setAttribute("meals", getMealList());
        request.getRequestDispatcher("/meals.jsp").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        String id = request.getParameter("id");
        request.setAttribute("action", action);
        request.setAttribute("id", id);

        if (action.equalsIgnoreCase("delete")) {
            MealsUtil.removeMeal(id);
            log.debug("removed: {}", id);
        }
        if (action.equalsIgnoreCase("edit")) {
            String dateTime = request.getParameter("dateTime");
            String date = dateTime.split("T")[0];
            String time = dateTime.split("T")[1];
            String description = request.getParameter("description");
            String calories = request.getParameter("calories");
            log.debug("edited: id - {},date - {},time - {},description - {},calories - {}", id, date, time, description, calories);
            request.setAttribute("date", date);
            request.setAttribute("time", time);
            request.setAttribute("description", description);
            request.setAttribute("calories", calories);
            this.getServletContext().getRequestDispatcher("/meals_edit.jsp").forward(request, response);
        }
        if (action.equalsIgnoreCase("add") || action.equalsIgnoreCase("save")) {
            String date = request.getParameter("date");
            String time = request.getParameter("time");
            String dateTime = date + " " + time;
            String description = request.getParameter("description");
            String calories = request.getParameter("calories");
            request.setAttribute("dateTime", dateTime);
            request.setAttribute("description", description);
            request.setAttribute("calories", calories);

            switch (action.toLowerCase()) {
                case "add": {
                    MealsUtil.addMeal(dateTime, description, calories);
                    log.debug("added: {}", id);
                    break;
                }
                case "save": {
                    request.setAttribute("id", id);
                    MealsUtil.editMeal(id, dateTime, description, calories);
                    log.debug("saved: {}", id);
                    break;
                }
            }
        }

        request.setAttribute("meals", getMealList());
        this.getServletContext().getRequestDispatcher("/meals.jsp").forward(request, response);

    }

    private List<MealWithExceed> getMealList() {
        return MealsUtil.getFilteredWithExceeded(MealsUtil.getMeals(), LocalTime.MIN, LocalTime.MAX, caloriesPerDay);
    }
}
