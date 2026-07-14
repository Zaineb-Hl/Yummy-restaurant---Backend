package croco.restau.yummy.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import croco.restau.yummy.DTO.LoginRequest;
import croco.restau.yummy.DTO.SignupRequest;
import croco.restau.yummy.models.Role;
import croco.restau.yummy.models.User;
import croco.restau.yummy.security.JwtUtil;
import croco.restau.yummy.services.UserService;

import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	 	@Autowired
	    private UserService userService;

	    @Autowired
	    private UserDetailsService userDetailsService;

	    @Autowired
	    private AuthenticationManager authenticationManager;

	    @Autowired
	    private JwtUtil jwtUtil;

	    @PostMapping("/signup")
	    public ResponseEntity<?> signup(@RequestBody SignupRequest signupRequest) {

	        // Construction de l'entité User à partir du DTO reçu
	        User user = new User();
	        user.setFirstName(signupRequest.getFirstName());
	        user.setLastName(signupRequest.getLastName());
	        user.setEmail(signupRequest.getEmail());
	        user.setPhone(signupRequest.getPhone());
	        user.setPassword(signupRequest.getPassword());

	        // Si aucun rôle n'est précisé, on met CLIENT par défaut
	        Role role = signupRequest.getRole() != null
	                ? Role.valueOf(signupRequest.getRole())
	                : Role.CLIENT;
	        user.setRole(role);

	        try {
	            User createdUser = userService.createUser(user);

	            UserDetails userDetails = userDetailsService.loadUserByUsername(createdUser.getEmail());
	            String token = jwtUtil.createToken(userDetails, createdUser);

	            Map<String, Object> response = new HashMap<>();
	            response.put("status", HttpStatus.CREATED.value());
	            response.put("message", "Compte créé avec succès");
	            response.put("token", token);
	            response.put("email", createdUser.getEmail());
	            response.put("role", createdUser.getRole().name());
	            response.put("userId", createdUser.getId());  

	            return ResponseEntity.status(HttpStatus.CREATED).body(response);

	        } catch (IllegalArgumentException ex) {
	            Map<String, Object> error = new HashMap<>();
	            error.put("status", HttpStatus.CONFLICT.value());
	            error.put("message", ex.getMessage());
	            return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
	        }
	    }

	    @PostMapping("/login")
	    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
	        Map<String, Object> map = new HashMap<>();

	        try {
	            Authentication authentication = authenticationManager.authenticate(
	                new UsernamePasswordAuthenticationToken(
	                    loginRequest.getEmail(),
	                    loginRequest.getPassword()
	                )
	            );

	            if (authentication.isAuthenticated()) {
	                User user = userService.getUserByEmail(loginRequest.getEmail());
	                UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
	                String token = jwtUtil.createToken(userDetails, user);

	                map.put("status", HttpStatus.OK.value());
	                map.put("message", "Authentification réussie");
	                map.put("token", token);
	                map.put("email", user.getEmail());
	                map.put("role", user.getRole().name());
	                map.put("userId", user.getId());  

	                return ResponseEntity.ok(map);
	            } else {
	                map.put("status", HttpStatus.UNAUTHORIZED.value());
	                map.put("message", "Authentification échouée");
	                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(map);
	            }

	        } catch (BadCredentialsException ex) {
	            map.put("status", HttpStatus.UNAUTHORIZED.value());
	            map.put("message", "Email ou mot de passe incorrect");
	            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(map);

	        } catch (LockedException ex) {
	            map.put("status", HttpStatus.UNAUTHORIZED.value());
	            map.put("message", "Votre compte est verrouillé");
	            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(map);

	        } catch (DisabledException ex) {
	            map.put("status", HttpStatus.UNAUTHORIZED.value());
	            map.put("message", "Votre compte est désactivé");
	            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(map);

	        } catch (AuthenticationException ex) {
	            map.put("status", HttpStatus.UNAUTHORIZED.value());
	            map.put("message", "Authentification échouée");
	            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(map);
	        }
	    }
	

}
