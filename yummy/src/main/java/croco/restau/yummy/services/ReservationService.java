package croco.restau.yummy.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import croco.restau.yummy.models.Reservation;
import croco.restau.yummy.models.ReservationStatus;

public interface ReservationService {

    List<Reservation> getAllReservations();
    Reservation getReservationById(Long id);
    List<Reservation> getReservationsByUser(Long userId);
    List<Reservation> getReservationsByDate(LocalDate date);
    List<Reservation> getReservationsByStatus(ReservationStatus status);
    Reservation createGuestReservation(Reservation reservation);
    Reservation createReservation(Reservation reservation, Long userId, List<Long> mealIds);
    // Suivi public pour un visiteur : id + email doivent correspondre
    Reservation trackReservation(Long id, String email);
    // Réservée à l'admin : confirmation / annulation d'une réservation
    Reservation updateStatus(Long id, ReservationStatus status);
    Reservation updateReservation(Long id, Reservation reservation);
    void deleteReservation(Long id);
}
