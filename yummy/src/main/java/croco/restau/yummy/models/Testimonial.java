package croco.restau.yummy.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "testimonials")
public class Testimonial {

	  	@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "TESTIMONIAL_ID")
	    private Long id;

	    private String name;
	    private String role;
	    private String content;
	    private String image;
	    private int rating;

	    @ManyToOne(fetch = FetchType.LAZY)
	    @JoinColumn(name = "USER_ID", nullable = true)   
	    private User user;

	    public Testimonial() {}

		public Testimonial(String name, String role, String content, String image, int rating, User user) {
			super();
			this.name = name;
			this.role = role;
			this.content = content;
			this.image = image;
			this.rating = rating;
			this.user = user;
		}

		public Long getId() {
			return id;
		}

		public String getName() {
			return name;
		}

		public String getRole() {
			return role;
		}

		public String getContent() {
			return content;
		}

		public String getImage() {
			return image;
		}

		public int getRating() {
			return rating;
		}

		public User getUser() {
			return user;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public void setName(String name) {
			this.name = name;
		}

		public void setRole(String role) {
			this.role = role;
		}

		public void setContent(String content) {
			this.content = content;
		}

		public void setImage(String image) {
			this.image = image;
		}

		public void setRating(int rating) {
			this.rating = rating;
		}

		public void setUser(User user) {
			this.user = user;
		}
	    
}
