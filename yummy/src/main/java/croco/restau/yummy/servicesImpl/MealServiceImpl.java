package croco.restau.yummy.servicesImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import croco.restau.yummy.repositories.ChefRepository;
import croco.restau.yummy.repositories.MealRepository;
import croco.restau.yummy.services.FileStorageService;
import croco.restau.yummy.services.MealService;
import croco.restau.yummy.models.Chef;
import croco.restau.yummy.models.Meal;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class MealServiceImpl implements MealService {
	
  	@Autowired
    private MealRepository mealRepository;

    @Autowired
    private ChefRepository chefRepository;

    @Autowired
    private FileStorageService fileStorageService;

    @Override
    public List<Meal> getAllMeals() {
        return mealRepository.findAll();
    }

    @Override
    public Meal getMealById(Long id) {
        return mealRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Meal non trouvé : " + id));
    }

    @Override
    public List<Meal> getMealsBySpeciality(String speciality) {
        return mealRepository.findBySpeciality(speciality);
    }

    @Override
    public List<Meal> getMealsByChef(Long chefId) {
        return mealRepository.findByChefId(chefId);
    }

    @Override
    public List<Meal> getMealsByCategory(String category) {
        return mealRepository.findByCategory(category);
    }
    
    @Override
    public List<Meal> searchMealsByName(String name) {
        return mealRepository.findByNameContainingIgnoreCase(name);
    }

    @Override
    public Meal createMeal(Meal meal, Long chefId) {
        Chef chef = chefRepository.findById(chefId)
                .orElseThrow(() -> new EntityNotFoundException("Chef non trouvé : " + chefId));
        meal.setChef(chef);
        return mealRepository.save(meal);
    }

    
    @Override
    public Meal updateMeal(Long id, Meal updated) {
        Meal existing = getMealById(id);
        String previousImage = existing.getImage();

        existing.setName(updated.getName());
        existing.setIngredients(updated.getIngredients());
        existing.setSpeciality(updated.getSpeciality());
        existing.setDescription(updated.getDescription());
        existing.setPrice(updated.getPrice());
        existing.setImage(updated.getImage());
        existing.setCategory(updated.getCategory());

        if (updated.getChef() != null && updated.getChef().getId() != null) {
            Chef chef = chefRepository.findById(updated.getChef().getId())
                    .orElseThrow(() -> new EntityNotFoundException("Chef non trouvé"));
            existing.setChef(chef);
        }

        Meal saved = mealRepository.save(existing);

        // L'image a changé : on supprime l'ancien fichier pour ne pas le laisser orphelin
        if (previousImage != null && !previousImage.isBlank() && !previousImage.equals(saved.getImage())) {
            fileStorageService.delete(previousImage, "meals");
        }

        return saved;
    }

    @Override
    public void deleteMeal(Long id) {
        Meal existing = getMealById(id);
        mealRepository.deleteById(id);

        if (existing.getImage() != null && !existing.getImage().isBlank()) {
            fileStorageService.delete(existing.getImage(), "meals");
        }
    }

    


}
