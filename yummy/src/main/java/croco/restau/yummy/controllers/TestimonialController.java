package croco.restau.yummy.controllers;

import croco.restau.yummy.models.Testimonial;
import croco.restau.yummy.services.TestimonialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/testimonials")
public class TestimonialController {

	 @Autowired
	    private TestimonialService testimonialService;

	    // Ajout par l'admin (sans compte)
	    @PostMapping
	    public Testimonial addTestimonial(@RequestBody Testimonial testimonial) {
	        return testimonialService.createTestimonial(testimonial);
	    }

	    // Ajout par un client connecté
	    @PostMapping("/user/{userId}")
	    public Testimonial addUserTestimonial(@RequestBody Testimonial testimonial, @PathVariable Long userId) {
	        return testimonialService.createUserTestimonial(testimonial, userId);
	    }

	    @PutMapping("/{id}")
	    public Testimonial editTestimonial(@PathVariable Long id, @RequestBody Testimonial testimonial) {
	        return testimonialService.updateTestimonial(id, testimonial);
	    }
	    
	    @GetMapping
	    public List<Testimonial> getAllTestimonials() {
	        return testimonialService.getAllTestimonials();
	    }

	    @GetMapping("/{id}")
	    public Testimonial getTestimonialById(@PathVariable Long id) {
	        return testimonialService.getTestimonialById(id);
	    }

	    @DeleteMapping("/{id}")
	    public void deleteTestimonial(@PathVariable Long id) {
	        testimonialService.deleteTestimonial(id);
	    }
}
