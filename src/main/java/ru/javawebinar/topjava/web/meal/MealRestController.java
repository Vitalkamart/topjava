package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.List;

import static ru.javawebinar.topjava.AuthorizedUser.*;

@Controller
public class MealRestController {

    @Autowired
    private MealService service;

    public Meal create(Meal meal) {
        return service.create(meal, userId());
    }

    public Meal get(int id){
        return service.get(id, userId());
    }

    void delete(int id) {
        service.delete(id, userId());
    }

    void update(Meal meal) {
        service.update(meal, userId());
    }

    public List<MealWithExceed> getAll() {
        return MealsUtil.getWithExceeded(service.getAll(userId()), getCaloriesPerDay());
    }

    public List<MealWithExceed> getBetween (LocalDateTime startDate, LocalDateTime endDate) {
        return MealsUtil.getWithExceeded(service.getBetween(startDate, endDate, userId()), getCaloriesPerDay());
    }
}