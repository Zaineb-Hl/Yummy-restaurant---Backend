package croco.restau.yummy.services;

import java.util.List;
import java.util.Optional;

import croco.restau.yummy.models.Chef;

public interface ChefService {
	
    List<Chef> getAllChefs();
    Chef getChefById(Long id);
    List<Chef> getChefsBySpeciality(String speciality);
    List<Chef> searchChefsByName(String lastName);
    Chef createChef(Chef chef);
    Chef updateChef(Long id, Chef chef);
    void deleteChef(Long id);

}
