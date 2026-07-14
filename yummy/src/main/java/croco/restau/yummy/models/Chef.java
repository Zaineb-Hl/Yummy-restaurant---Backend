package croco.restau.yummy.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "chefs")
public class Chef {

	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "CHEF_ID")
	    private Long id;

	    private String firstName;
	    private String lastName;
	    private String speciality;
	    private String description;
	    private String image;   

	    @OneToMany(mappedBy = "chef", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	    @JsonManagedReference
	    private List<Meal> meals;

	    public Chef() {}

	    public Chef(String firstName, String lastName, String speciality, String description, String image,
				List<Meal> meals) {
			super();
			this.firstName = firstName;
			this.lastName = lastName;
			this.speciality = speciality;
			this.description = description;
			this.image = image;
			this.meals = meals;
		}

		public Long getId() { return id; }
	    public String getFirstName() { return firstName; }
	    public String getLastName() { return lastName; }
	    public String getSpeciality() { return speciality; }
	    public String getDescription() { return description; }
	    public String getImage() { return image; }
	    public List<Meal> getMeals() { return meals; }

	    public void setFirstName(String firstName) { this.firstName = firstName; }
	    public void setLastName(String lastName) { this.lastName = lastName; }
	    public void setSpeciality(String speciality) { this.speciality = speciality; }
	    public void setDescription(String description) { this.description = description; }
	    public void setImage(String image) { this.image = image; }
	    public void setMeals(List<Meal> meals) { this.meals = meals; }
	
	
}
