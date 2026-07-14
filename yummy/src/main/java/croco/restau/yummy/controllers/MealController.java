package croco.restau.yummy.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

import croco.restau.yummy.services.MealService;
import croco.restau.yummy.models.Meal;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/meals")
public class MealController {
	
	  	@Autowired
	    private MealService mealService;

	    @PostMapping("/chef/{chefId}")
	    public Meal addMeal(@RequestBody Meal meal, @PathVariable Long chefId) {
	        return mealService.createMeal(meal, chefId);
	    }
	    
	    @PutMapping("/{id}")
	    public Meal editMeal(@PathVariable Long id, @RequestBody Meal meal) {
	        return mealService.updateMeal(id, meal);
	    }

	    @GetMapping
	    public List<Meal> getAllMeals() {
	        return mealService.getAllMeals();
	    }

	    @GetMapping("/{id}")
	    public Meal getMealById(@PathVariable Long id) {
	        return mealService.getMealById(id);
	    }

	    @GetMapping("/speciality")
	    public List<Meal> getBySpeciality(@RequestParam String value) {
	        return mealService.getMealsBySpeciality(value);
	    }

	    @GetMapping("/category")
	    public List<Meal> getByCategory(@RequestParam String value) {
	        return mealService.getMealsByCategory(value);
	    }
	    
	    @GetMapping("/chef/{chefId}")
	    public List<Meal> getByChef(@PathVariable Long chefId) {
	        return mealService.getMealsByChef(chefId);
	    }

	    @GetMapping("/search")
	    public List<Meal> searchByName(@RequestParam String name) {
	        return mealService.searchMealsByName(name);
	    }

	    @DeleteMapping("/{id}")
	    public void deleteMeal(@PathVariable Long id) {
	        mealService.deleteMeal(id);
	    }
}
