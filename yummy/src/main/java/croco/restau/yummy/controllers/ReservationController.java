package croco.restau.yummy.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import croco.restau.yummy.models.Reservation;
import croco.restau.yummy.models.ReservationStatus;
import croco.restau.yummy.models.User;
import croco.restau.yummy.services.ReservationService;
import croco.restau.yummy.services.UserService;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

	@Autowired
    private ReservationService reservationService;

	@Autowired
	private UserService userService;

    @PostMapping("/guest")
    public Reservation addGuestReservation(@RequestBody Reservation reservation) {
        return reservationService.createGuestReservation(reservation);
    }

    // Suivi public d'une réservation (visiteur sans compte) : id + email requis,
    // aucune autre information ne permet d'y accéder 
    @GetMapping("/track")
    public Reservation trackReservation(@RequestParam Long id, @RequestParam String email) {
        return reservationService.trackReservation(id, email);
    }

    @PostMapping("/user/{userId}")
    public Reservation addReservation(@RequestBody Reservation reservation,
                                       @PathVariable Long userId,
                                       @RequestParam(required = false) List<Long> mealIds) {
        checkOwnerOrAdmin(userId);
        return reservationService.createReservation(reservation, userId, mealIds);
    }

    @PutMapping("/{id}")
    public Reservation editReservation(@PathVariable Long id, @RequestBody Reservation reservation) {
        checkOwnsReservationOrAdmin(id);
        return reservationService.updateReservation(id, reservation);
    }

    @GetMapping
    public List<Reservation> getAllReservations() {
        // Réservé à l'admin (voir SecurityConfig) : vue globale pour la confirmation des réservations
        return reservationService.getAllReservations();
    }

    @GetMapping("/{id}")
    public Reservation getReservationById(@PathVariable Long id) {
        checkOwnsReservationOrAdmin(id);
        return reservationService.getReservationById(id);
    }

    @GetMapping("/user/{userId}")
    public List<Reservation> getByUser(@PathVariable Long userId) {
        // Historique d'un utilisateur : lui-même uniquement, ou l'admin
        checkOwnerOrAdmin(userId);
        return reservationService.getReservationsByUser(userId);
    }

    @GetMapping("/date")
    public List<Reservation> getByDate(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate value) {
        return reservationService.getReservationsByDate(value);
    }

    @GetMapping("/status")
    public List<Reservation> getByStatus(@RequestParam String value) {
        return reservationService.getReservationsByStatus(ReservationStatus.valueOf(value.toUpperCase()));
    }

    // Confirmation de réservation : réservée à l'admin 
    @PatchMapping("/{id}/status")
    public Reservation updateStatus(@PathVariable Long id, @RequestParam String status) {
        return reservationService.updateStatus(id, ReservationStatus.valueOf(status.toUpperCase()));
    }

    @DeleteMapping("/{id}")
    public void deleteReservation(@PathVariable Long id) {
        reservationService.deleteReservation(id);
    }

    // --- Contrôles d'appartenance : un CLIENT ne doit accéder qu'à ses propres données ---

    private void checkOwnerOrAdmin(Long userId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            throw new AccessDeniedException("Authentification requise");
        }
        boolean isAdmin = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        if (isAdmin) return;

        User current = userService.getUserByEmail(auth.getName());
        if (!current.getId().equals(userId)) {
            throw new AccessDeniedException("Accès refusé : ce n'est pas votre compte");
        }
    }

    private void checkOwnsReservationOrAdmin(Long reservationId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            throw new AccessDeniedException("Authentification requise");
        }
        boolean isAdmin = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        if (isAdmin) return;

        User current = userService.getUserByEmail(auth.getName());
        Reservation reservation = reservationService.getReservationById(reservationId);
        if (reservation.getUser() == null || !reservation.getUser().getId().equals(current.getId())) {
            throw new AccessDeniedException("Accès refusé : ce n'est pas votre réservation");
        }
    }
    

	
}
