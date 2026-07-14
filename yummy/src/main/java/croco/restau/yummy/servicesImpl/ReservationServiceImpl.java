package croco.restau.yummy.servicesImpl;
import croco.restau.yummy.models.Meal;
import croco.restau.yummy.models.Reservation;
import croco.restau.yummy.models.ReservationStatus;
import croco.restau.yummy.models.User;
import croco.restau.yummy.repositories.MealRepository;
import croco.restau.yummy.repositories.ReservationRepository;
import croco.restau.yummy.repositories.UserRepository;
import croco.restau.yummy.services.ReservationService;
import jakarta.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public class ReservationServiceImpl implements ReservationService{
	
	 @Autowired
	    private ReservationRepository reservationRepository;
	    @Autowired
	    private UserRepository userRepository;
	    @Autowired
	    private MealRepository mealRepository;

	    @Override
	    public List<Reservation> getAllReservations() {
	        return reservationRepository.findAll();
	    }

	    @Override
	    public Reservation getReservationById(Long id) {
	        return reservationRepository.findById(id)
	                .orElseThrow(() -> new EntityNotFoundException("Réservation non trouvée : " + id));
	    }

	    @Override
	    public List<Reservation> getReservationsByUser(Long userId) {
	        return reservationRepository.findByUserId(userId);
	    }

	    @Override
	    public List<Reservation> getReservationsByDate(LocalDate date) {
	        return reservationRepository.findByDate(date);
	    }

	    @Override
	    public List<Reservation> getReservationsByStatus(ReservationStatus status) {
	        return reservationRepository.findByStatus(status);
	    }

	    @Override
	    public Reservation createGuestReservation(Reservation reservation) {
	        reservation.setUser(null);
	        reservation.setStatus(ReservationStatus.PENDING);
	        return reservationRepository.save(reservation);
	    }

	    @Override
	    public Reservation createReservation(Reservation reservation, Long userId, List<Long> mealIds) {
	        User user = userRepository.findById(userId)
	                .orElseThrow(() -> new EntityNotFoundException("User non trouvé : " + userId));

	        if (mealIds != null && !mealIds.isEmpty()) {
	            List<Meal> meals = mealRepository.findAllById(mealIds);
	            reservation.setMeals(meals);
	        }

	        reservation.setUser(user);
	        reservation.setStatus(ReservationStatus.PENDING);
	        return reservationRepository.save(reservation);
	    }

	    @Override
	    public Reservation trackReservation(Long id, String email) {
	        return reservationRepository.findByIdAndEmailIgnoreCase(id, email)
	                .orElseThrow(() -> new EntityNotFoundException(
	                        "Aucune réservation trouvée avec cet identifiant et cet email"));
	    }

	    // Confirmation / annulation : appelée uniquement par l'admin (contrôlé côté sécurité)
	    @Override
	    public Reservation updateStatus(Long id, ReservationStatus status) {
	        Reservation existing = getReservationById(id);
	        existing.setStatus(status);
	        return reservationRepository.save(existing);
	    }

	    @Override
	    public Reservation updateReservation(Long id, Reservation updated) {
	        Reservation existing = getReservationById(id);
	        existing.setName(updated.getName());
	        existing.setEmail(updated.getEmail());
	        existing.setPhone(updated.getPhone());
	        existing.setDate(updated.getDate());
	        existing.setTime(updated.getTime());
	        existing.setPeople(updated.getPeople());
	        existing.setMessage(updated.getMessage());
	        return reservationRepository.save(existing);
	    }

	    @Override
	    public void deleteReservation(Long id) {
	        if (!reservationRepository.existsById(id))
	            throw new EntityNotFoundException("Réservation non trouvée : " + id);
	        reservationRepository.deleteById(id);
	    }
}
