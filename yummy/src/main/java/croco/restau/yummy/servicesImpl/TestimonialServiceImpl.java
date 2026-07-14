package croco.restau.yummy.servicesImpl;

import croco.restau.yummy.models.Testimonial;
import croco.restau.yummy.models.User;
import croco.restau.yummy.repositories.TestimonialRepository;
import croco.restau.yummy.repositories.UserRepository;
import croco.restau.yummy.services.TestimonialService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TestimonialServiceImpl implements TestimonialService {

	 @Autowired
	    private TestimonialRepository testimonialRepository;

	    @Autowired
	    private UserRepository userRepository;

	    @Override
	    public List<Testimonial> getAllTestimonials() {
	        return testimonialRepository.findAll();
	    }

	    @Override
	    public Testimonial getTestimonialById(Long id) {
	        return testimonialRepository.findById(id)
	                .orElseThrow(() -> new EntityNotFoundException("Témoignage non trouvé : " + id));
	    }

	    // Ajout par l'admin — pas de compte associé
	    @Override
	    public Testimonial createTestimonial(Testimonial testimonial) {
	        testimonial.setUser(null);
	        return testimonialRepository.save(testimonial);
	    }

	    // Ajout par un client connecté — lié à son compte
	    @Override
	    public Testimonial createUserTestimonial(Testimonial testimonial, Long userId) {
	        User user = userRepository.findById(userId)
	                .orElseThrow(() -> new EntityNotFoundException("User non trouvé : " + userId));

	        testimonial.setUser(user);
	        testimonial.setName(user.getFirstName() + " " + user.getLastName());
	        return testimonialRepository.save(testimonial);
	    }

	    @Override
	    public Testimonial updateTestimonial(Long id, Testimonial updated) {
	        Testimonial existing = getTestimonialById(id);
	        existing.setName(updated.getName());
	        existing.setRole(updated.getRole());
	        existing.setContent(updated.getContent());
	        existing.setImage(updated.getImage());
	        existing.setRating(updated.getRating());
	        return testimonialRepository.save(existing);
	    }

	    @Override
	    public void deleteTestimonial(Long id) {
	        if (!testimonialRepository.existsById(id))
	            throw new EntityNotFoundException("Témoignage non trouvé : " + id);
	        testimonialRepository.deleteById(id);
	    }
}
