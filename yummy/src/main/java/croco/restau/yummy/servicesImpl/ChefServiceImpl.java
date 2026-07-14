package croco.restau.yummy.servicesImpl;

import croco.restau.yummy.models.Chef;
import croco.restau.yummy.repositories.ChefRepository;
import croco.restau.yummy.services.ChefService;
import croco.restau.yummy.services.FileStorageService;
import jakarta.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChefServiceImpl implements ChefService {
	

	   @Autowired
	    private ChefRepository chefRepository;

	    @Autowired
	    private FileStorageService fileStorageService;

	    @Override
	    public List<Chef> getAllChefs() {
	        return chefRepository.findAll();
	    }

	    @Override
	    public Chef getChefById(Long id) {
	        return chefRepository.findById(id)
	                .orElseThrow(() -> new EntityNotFoundException("Chef non trouvé : " + id));
	    }

	    @Override
	    public List<Chef> getChefsBySpeciality(String speciality) {
	        return chefRepository.findBySpeciality(speciality);
	    }

	    @Override
	    public List<Chef> searchChefsByName(String lastName) {
	        return chefRepository.findByLastNameContainingIgnoreCase(lastName);
	    }

	    @Override
	    public Chef createChef(Chef chef) {
	        return chefRepository.save(chef);
	    }

	    @Override
	    public Chef updateChef(Long id, Chef updated) {
	        Chef existing = getChefById(id);
	        String previousImage = existing.getImage();

	        existing.setFirstName(updated.getFirstName());
	        existing.setLastName(updated.getLastName());
	        existing.setSpeciality(updated.getSpeciality());
	        existing.setDescription(updated.getDescription());
	        existing.setImage(updated.getImage());

	        Chef saved = chefRepository.save(existing);

	        if (previousImage != null && !previousImage.isBlank() && !previousImage.equals(saved.getImage())) {
	            fileStorageService.delete(previousImage, "chefs");
	        }

	        return saved;
	    }

	    @Override
	    public void deleteChef(Long id) {
	        Chef existing = getChefById(id);
	        chefRepository.deleteById(id);

	        if (existing.getImage() != null && !existing.getImage().isBlank()) {
	            fileStorageService.delete(existing.getImage(), "chefs");
	        }
	    }
}
