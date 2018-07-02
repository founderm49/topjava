package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.TimeUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private static MealDao dao;
    private static int caloriesPerDay = 2000;

    @Override
    public void init() throws ServletException {
        super.init();
        dao = new MealDao();
    }

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
        String id = request.getParameter("id");
        request.setAttribute("action", action);
        request.setAttribute("id", id);

        switch (action.toLowerCase()) {
            case "add": {
                String date = request.getParameter("date");
                String time = request.getParameter("time");
                String dateTime = date + " " + time;
                String description = request.getParameter("description");
                String calories = request.getParameter("calories");
                request.setAttribute("dateTime", dateTime);
                request.setAttribute("description", description);
                request.setAttribute("calories", calories);
                dao.create(new Meal(TimeUtil.stringToDateTime(dateTime), description, Integer.parseInt(calories)));
                log.debug("created new meal with [date={}, description={}, calories={}] ", dateTime, description, calories);
            }
            break;
            case "edit": {
                String dateTime = request.getParameter("dateTime");
                String date = dateTime.split("T")[0];
                String time = dateTime.split("T")[1];
                String description = request.getParameter("description");
                String calories = request.getParameter("calories");
                request.setAttribute("date", date);
                request.setAttribute("time", time);
                request.setAttribute("description", description);
                request.setAttribute("calories", calories);
                log.debug("edited: id - {},date - {},time - {},description - {},calories - {}", id, date, time, description, calories);
                this.getServletContext().getRequestDispatcher("/meals_edit.jsp").forward(request, response);
                break;
            }
            case "save": {
                String date = request.getParameter("date");
                String time = request.getParameter("time");
                String dateTime = date + " " + time;
                String description = request.getParameter("description");
                String calories = request.getParameter("calories");
                dao.update(Integer.parseInt(id), new Meal(TimeUtil.stringToDateTime(dateTime), description, Integer.parseInt(calories)));
                log.debug("saved: id - {},date - {},time - {},description - {},calories - {}", id, date, time, description, calories);

            }
            break;
            case "delete": {
                dao.delete(Integer.parseInt(id));
                log.debug("deleted entry with ID: {}", id);
            }
            break;
        }
        request.setAttribute("meals", getExceededList());
        this.getServletContext().getRequestDispatcher("/meals.jsp").forward(request, response);

    }

    private List<MealWithExceed> getExceededList() {
        return MealsUtil.getFilteredWithExceeded(new ArrayList<>(dao.getAll().values()), LocalTime.MIN, LocalTime.MAX, caloriesPerDay);
    }
}
