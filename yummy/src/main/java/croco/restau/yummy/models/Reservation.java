package croco.restau.yummy.models;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "reservations")
public class Reservation {

	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "RESERVATION_ID")
	    private Long id;

	    private String name;
	    private String email;
	    private String phone;
	    private LocalDate date;
	    private LocalTime time;
	    private int people;
	    private String message;

	    @Enumerated(EnumType.STRING)
	    private ReservationStatus status;

	    @ManyToOne(fetch = FetchType.LAZY)
	    @JoinColumn(name = "USER_ID", nullable = true)
	    private User user;

	    @ManyToMany(fetch = FetchType.LAZY)
	    @JoinTable(
	        name = "reservation_meals",
	        joinColumns = @JoinColumn(name = "RESERVATION_ID"),
	        inverseJoinColumns = @JoinColumn(name = "MEAL_ID")
	    )
	    private List<Meal> meals;

	    public Reservation() {}

	    public Reservation(String name, String email, String phone,
	                        LocalDate date, LocalTime time, int people, String message) {
	        this.name = name;
	        this.email = email;
	        this.phone = phone;
	        this.date = date;
	        this.time = time;
	        this.people = people;
	        this.message = message;
	        this.status = ReservationStatus.PENDING;
	    }

	    public Long getId() { return id; }
	    public String getName() { return name; }
	    public String getEmail() { return email; }
	    public String getPhone() { return phone; }
	    public LocalDate getDate() { return date; }
	    public LocalTime getTime() { return time; }
	    public int getPeople() { return people; }
	    public String getMessage() { return message; }
	    public ReservationStatus getStatus() { return status; }
	    public User getUser() { return user; }
	    public List<Meal> getMeals() { return meals; }

	    public void setName(String name) { this.name = name; }
	    public void setEmail(String email) { this.email = email; }
	    public void setPhone(String phone) { this.phone = phone; }
	    public void setDate(LocalDate date) { this.date = date; }
	    public void setTime(LocalTime time) { this.time = time; }
	    public void setPeople(int people) { this.people = people; }
	    public void setMessage(String message) { this.message = message; }
	    public void setStatus(ReservationStatus status) { this.status = status; }
	    public void setUser(User user) { this.user = user; }
	    public void setMeals(List<Meal> meals) { this.meals = meals; }
	}