package croco.restau.yummy.servicesImpl;

import croco.restau.yummy.models.User;
import croco.restau.yummy.repositories.UserRepository;
import croco.restau.yummy.services.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserServiceImpl implements UserService{

		@Autowired
	    private UserRepository userRepository;

	    @Autowired
	    private PasswordEncoder passwordEncoder;

	    @Override
	    public List<User> getAllUsers() {
	        return userRepository.findAll();
	    }

	    @Override
	    public User getUserById(Long id) {
	        return userRepository.findById(id)
	                .orElseThrow(() -> new EntityNotFoundException("User non trouvé : " + id));
	    }

	    @Override
	    public User getUserByEmail(String email) {
	        return userRepository.findByEmail(email)
	                .orElseThrow(() -> new EntityNotFoundException("User non trouvé avec l'email : " + email));
	    }

	    @Override
	    public User createUser(User user) {
	        if (userRepository.existsByEmail(user.getEmail()))
	            throw new IllegalArgumentException("Email déjà utilisé : " + user.getEmail());

	        user.setPassword(passwordEncoder.encode(user.getPassword()));
	        return userRepository.save(user);
	    }

	    @Override
	    public User updateUser(Long id, User updated) {
	        User existing = getUserById(id);
	        existing.setFirstName(updated.getFirstName());
	        existing.setLastName(updated.getLastName());
	        existing.setEmail(updated.getEmail());
	        existing.setPhone(updated.getPhone());
	        existing.setRole(updated.getRole());
	        return userRepository.save(existing);
	    }

	    @Override
	    public void deleteUser(Long id) {
	        if (!userRepository.existsById(id))
	            throw new EntityNotFoundException("User non trouvé : " + id);
	        userRepository.deleteById(id);
	    }

}
