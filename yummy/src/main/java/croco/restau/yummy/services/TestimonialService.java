package croco.restau.yummy.services;

import java.util.List;

import croco.restau.yummy.models.Testimonial;

public interface TestimonialService {
	
    List<Testimonial> getAllTestimonials();
    Testimonial getTestimonialById(Long id);
    Testimonial createTestimonial(Testimonial testimonial);
    Testimonial updateTestimonial(Long id, Testimonial testimonial);
    Testimonial createUserTestimonial(Testimonial testimonial, Long userId);
    void deleteTestimonial(Long id);

}
