package croco.restau.yummy.models;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
public class User {
	
	 	@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "USER_ID")
	    private Long id;

	    private String firstName;
	    private String lastName;

	    @Column(unique = true, nullable = false)
	    private String email;

	    private String password;
	    private String phone;

	    @Enumerated(EnumType.STRING)
	    private Role role;

	    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	    private List<Reservation> reservations;

	    public User() {}

	    public User(String firstName, String lastName, String email, String password, String phone, Role role) {
	        this.firstName = firstName;
	        this.lastName = lastName;
	        this.email = email;
	        this.password = password;
	        this.phone = phone;
	        this.role = role;
	    }

	    public Long getId() { 
	    	return id;
	    }
	    public String getFirstName() { 
	    	return firstName;
	    	}
	    public String getLastName() { return lastName; }
	    public String getEmail() { return email; }
	    public String getPassword() { return password; }
	    public String getPhone() { return phone; }
	    public Role getRole() { return role; }
	    public List<Reservation> getReservations() { return reservations; }

	    public void setFirstName(String firstName) { this.firstName = firstName; }
	    public void setLastName(String lastName) { this.lastName = lastName; }
	    public void setEmail(String email) { this.email = email; }
	    public void setPassword(String password) { this.password = password; }
	    public void setPhone(String phone) { this.phone = phone; }
	    public void setRole(Role role) { this.role = role; }
	    public void setReservations(List<Reservation> reservations) { this.reservations = reservations; }

}
