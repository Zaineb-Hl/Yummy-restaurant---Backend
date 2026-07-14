package croco.restau.yummy.config;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import croco.restau.yummy.security.JwtFilter;
import croco.restau.yummy.servicesImpl.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	   @Autowired
	    JwtFilter jwtFilter;

	    @Autowired
	    UserDetailsServiceImpl userDetailsService;

	    @Bean
	    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	        http
	            .csrf(csrf -> csrf.disable())
	            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
	            .authorizeHttpRequests(auth -> auth
	                // Authentification : ouverte à tous
	                .requestMatchers("/api/auth/**").permitAll()

	                // Visiteur ET utilisateur connecté : consultation libre du contenu du site
	                .requestMatchers(HttpMethod.GET, "/api/chefs/**").permitAll()
	                .requestMatchers(HttpMethod.GET, "/api/meals/**").permitAll()
	                .requestMatchers(HttpMethod.GET, "/api/testimonials/**").permitAll()
	                .requestMatchers("/images/**").permitAll()

	                // Visiteur : réservation sans compte, et suivi public de son statut
	                .requestMatchers(HttpMethod.POST, "/api/reservations/guest").permitAll()
	                .requestMatchers(HttpMethod.GET, "/api/reservations/track").permitAll()

	                // Utilisateur connecté : réservation liée à son compte
	                .requestMatchers(HttpMethod.POST, "/api/reservations/user/**").hasAnyRole("CLIENT", "ADMIN")

	                // Utilisateur connecté : avis lié à son compte
	                .requestMatchers(HttpMethod.POST, "/api/testimonials/user/**").hasAnyRole("CLIENT", "ADMIN")

	                // Admin uniquement : gestion du contenu (chefs, plats, avis, upload, utilisateurs)
	                .requestMatchers("/api/chefs/**").hasRole("ADMIN")
	                .requestMatchers("/api/meals/**").hasRole("ADMIN")
	                .requestMatchers("/api/testimonials/**").hasRole("ADMIN")
	                .requestMatchers("/api/upload/**").hasRole("ADMIN")
	                .requestMatchers("/api/users/**").hasRole("ADMIN")

	                // Admin uniquement : confirmation et gestion des réservations
	                .requestMatchers(HttpMethod.PATCH, "/api/reservations/*/status").hasRole("ADMIN")
	                .requestMatchers(HttpMethod.DELETE, "/api/reservations/**").hasRole("ADMIN")
	                .requestMatchers(HttpMethod.GET, "/api/reservations/date").hasRole("ADMIN")
	                .requestMatchers(HttpMethod.GET, "/api/reservations/status").hasRole("ADMIN")
	                .requestMatchers(HttpMethod.GET, "/api/reservations").hasRole("ADMIN")

	                // Client et admin : consulter/modifier une réservation (le contrôleur vérifie
	                // que le client ne touche qu'à ses propres réservations, l'admin a accès à tout)
	                .requestMatchers("/api/reservations/**").hasAnyRole("CLIENT", "ADMIN")

	                .anyRequest().authenticated()
	            )
	            .sessionManagement(manager -> manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
	            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

	        return http.build();
	    }

	    @Bean
	    public DaoAuthenticationProvider authenticationProvider() {
	        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(userDetailsService);
	        authProvider.setPasswordEncoder(passwordEncoder());
	        return authProvider;
	    }

	    @Bean
	    public PasswordEncoder passwordEncoder() {
	        return new BCryptPasswordEncoder();
	    }

	    @Bean
	    public CorsConfigurationSource corsConfigurationSource() {
	        CorsConfiguration config = new CorsConfiguration();
	        config.setAllowedOrigins(List.of("http://localhost:4200"));
	        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
	        config.setAllowedHeaders(List.of("Authorization", "Content-Type", "*"));
	        config.setExposedHeaders(List.of("Authorization"));
	        config.setAllowCredentials(true);

	        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	        source.registerCorsConfiguration("/**", config);
	        return source;
	    }

	    @Bean
	    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
	        return http.getSharedObject(AuthenticationManagerBuilder.class)
	                   .authenticationProvider(authenticationProvider())
	                   .build();
	    }

}