package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.UserDao;
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
    private final UserDao dao;

    public MealServlet() {
        super();
        dao = new UserDao();
    }

    private static final Logger log = getLogger(MealServlet.class);
    private static int caloriesPerDay = 2000;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        log.debug("redirect to meals");
        request.setAttribute("meals", getExceededList());
        request.getRequestDispatcher("/meals.jsp").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        String count = request.getParameter("count");
        request.setAttribute("action", action);
        request.setAttribute("count", count);

        if (action.equalsIgnoreCase("delete")) {
            dao.delete(count);
            log.debug("deleted entry with ID: {}", count);
        }
        //additional logic to split dateTime to date && time for better user experience
        if (action.equalsIgnoreCase("edit")) {
            String dateTime = request.getParameter("dateTime");
            String date = dateTime.split("T")[0];
            String time = dateTime.split("T")[1];
            String description = request.getParameter("description");
            String calories = request.getParameter("calories");
            log.debug("edited: id - {},date - {},time - {},description - {},calories - {}", count, date, time, description, calories);
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
                    dao.create(dateTime, description, calories);
                    log.debug("created new meal with ID: {}", count);
                    break;
                }
                case "save": {
                    request.setAttribute("count", count);
                    dao.update(count, dateTime, description, calories);
                    log.debug("updated row with ID: {}", count);
                    break;
                }
            }
        }
        //unfiltered MealList
        request.setAttribute("meals", MealsUtil.getFilteredWithExceeded(UserDao.getMeals(), LocalTime.MIN, LocalTime.MAX, caloriesPerDay));
        this.getServletContext().getRequestDispatcher("/meals.jsp").forward(request, response);

    }

    private List<MealWithExceed> getExceededList() {
        return MealsUtil.getFilteredWithExceeded(UserDao.getMeals(), LocalTime.MIN, LocalTime.MAX, caloriesPerDay);
    }
}
