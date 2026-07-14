package croco.restau.yummy.services;

import java.util.List;
import java.util.Optional;

import croco.restau.yummy.models.Meal;

public interface MealService {

    List<Meal> getAllMeals();
    Meal getMealById(Long id);
    List<Meal> getMealsBySpeciality(String speciality);
    List<Meal> getMealsByChef(Long chefId);
    List<Meal> getMealsByCategory(String category);   
    List<Meal> searchMealsByName(String name);
    Meal createMeal(Meal meal, Long chefId);
    Meal updateMeal(Long id, Meal meal);
    void deleteMeal(Long id);
}
