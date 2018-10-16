package ru.javawebinar.topjava.web;



import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.InMemoryMealRepository;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Objects;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger LOG = getLogger(MealServlet.class);
    private MealRepository repository;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        repository = new InMemoryMealRepository();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String id = req.getParameter("id");
        Meal meal = new Meal(
                id.isEmpty() ? null : Integer.valueOf(id),
                LocalDateTime.parse(req.getParameter("dateTime")),
                req.getParameter("descriptin"),
                Integer.valueOf(req.getParameter("calories"))
        );
        LOG.info(meal.isNew() ? "created {}" : "updated {}", meal);
        repository.save(meal);
        resp.sendRedirect("meals");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "getAll";
        }
        int id;
        switch (action) {
            case "create":
                Meal meal = new Meal(LocalDateTime.now(), "", 1000);
                LOG.info("Created {}", meal);
                request.setAttribute("meal", meal);
                request.getRequestDispatcher("/mealForm.jsp").forward(request, response);
                break;
            case "getAll":
                LOG.info("getAll");
                request.setAttribute("meals", MealsUtil.getWithExceeded(repository.getAll(),2000));
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
                break;
            case "delete":
                id = getId(request);
                repository.delete(id);
                LOG.info("Deleted {}", id);
                response.sendRedirect("meals");
                break;
            case "edit":
                id = getId(request);
                request.setAttribute("meal", repository.get(id));
                LOG.info("Edit {}", id);
                request.getRequestDispatcher("/mealForm.jsp").forward(request, response);
                break;
        }
    }

    public int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.valueOf(paramId);
    }
}
