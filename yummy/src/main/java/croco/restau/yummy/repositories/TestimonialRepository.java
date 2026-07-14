package croco.restau.yummy.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import croco.restau.yummy.models.Testimonial;

@Repository
public interface TestimonialRepository extends JpaRepository<Testimonial, Long> {

}
