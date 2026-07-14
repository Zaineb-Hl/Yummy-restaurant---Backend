package croco.restau.yummy.repositories;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import croco.restau.yummy.models.Reservation;
import croco.restau.yummy.models.ReservationStatus;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long>{
	  List<Reservation> findByUserId(Long userId);
	    List<Reservation> findByStatus(ReservationStatus status);
	    List<Reservation> findByDate(LocalDate date);

	    // Suivi public : un visiteur ne peut retrouver que SA réservation,
	    // en fournissant à la fois l'id ET l'email utilisés lors de la réservation.
	    Optional<Reservation> findByIdAndEmailIgnoreCase(Long id, String email);
	
}
