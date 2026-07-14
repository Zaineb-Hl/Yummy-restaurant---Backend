package croco.restau.yummy.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import croco.restau.yummy.models.Meal;

@Repository
public interface MealRepository extends JpaRepository<Meal, Long>{
    List<Meal> findBySpeciality(String speciality);
    List<Meal> findByChefId(Long chefId);
    List<Meal> findByNameContainingIgnoreCase(String name);
    List<Meal> findByCategory(String category);   
}
