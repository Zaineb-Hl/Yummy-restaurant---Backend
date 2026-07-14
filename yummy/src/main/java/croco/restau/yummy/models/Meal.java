package croco.restau.yummy.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "meals")
public class Meal {

	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "MEAL_ID")
	    private Long id;

	    private String name;
	    private String ingredients;
	    private String speciality;
	    private String description;
	    private Double price;
	    private String image;
	    private String category; 

	    @ManyToOne(fetch = FetchType.LAZY)
	    @JoinColumn(name = "CHEF_ID")
	    @JsonBackReference
	    private Chef chef;

	    @ManyToMany(mappedBy = "meals", fetch = FetchType.LAZY)
	    private List<Reservation> reservations;

	    public Meal() {}

	  
	    public Meal(String name, String ingredients, String speciality, String description, Double price, String image,
				String category, Chef chef, List<Reservation> reservations) {
			super();
			this.name = name;
			this.ingredients = ingredients;
			this.speciality = speciality;
			this.description = description;
			this.price = price;
			this.image = image;
			this.category = category;
			this.chef = chef;
			this.reservations = reservations;
		}

		public Long getId() { return id; }
	    public String getName() { return name; }
	    public String getIngredients() { return ingredients; }
	    public String getSpeciality() { return speciality; }
	    public String getDescription() { return description; }
	    public Double getPrice() { return price; }
	    public String getImage() { return image; }
	    public String getCategory() { return category; }
	    public Chef getChef() { return chef; }
	    public List<Reservation> getReservations() { return reservations; }

	    public void setName(String name) { this.name = name; }
	    public void setIngredients(String ingredients) { this.ingredients = ingredients; }
	    public void setSpeciality(String speciality) { this.speciality = speciality; }
	    public void setDescription(String description) { this.description = description; }
	    public void setPrice(Double price) { this.price = price; }
	    public void setImage(String image) { this.image = image; }
	    public void setCategory(String category) { this.category = category; }
	    public void setChef(Chef chef) { this.chef = chef; }
	    public void setReservations(List<Reservation> reservations) { this.reservations = reservations; }
	}
