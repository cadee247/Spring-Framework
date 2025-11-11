package tacos.tacos05.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import tacos.tacos05.User;
import tacos.tacos05.data.UserRepository;

/**
 * Configures Spring Security for the Taco Cloud application.
 * Defines password encoding, user lookup, and access rules for protected routes.
 */
@Configuration
public class SecurityConfig {

    /**
     * Defines the password encoder bean.
     * BCrypt is a strong hashing algorithm used to securely store passwords.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Custom UserDetailsService bean that loads users from MongoDB via UserRepository.
     * Required by Spring Security to authenticate users during login.
     *
     * @param userRepo the repository used to look up users by username
     * @return a lambda that returns a User or throws an exception if not found
     */
    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepo) {
        return username -> {
            User user = userRepo.findByUsername(username);
            if (user != null) {
                return user; // User must implement UserDetails
            }
            throw new UsernameNotFoundException("User '" + username + "' not found");
        };
    }

    /**
     * Configures the security filter chain.
     * Defines which routes require authentication and how login/logout should behave.
     *
     * Key behaviors:
     * - "/design" and "/orders/**" require authenticated users with role "USER"
     * - "/h2-console/**" is fully open (for development/testing)
     * - All other routes are publicly accessible
     * - Custom login page at "/login" with redirect to "/" on success
     * - CSRF protection is disabled for H2 console
     * - Frame options set to same origin to allow H2 console to render
     *
     * @param http the HttpSecurity object used to build the filter chain
     * @return the configured SecurityFilterChain
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/design", "/orders/**").hasRole("USER") // Protected routes
                        .requestMatchers("/h2-console/**").permitAll()            // Dev console access
                        .anyRequest().permitAll()                                 // Public access
                )
                .formLogin(form -> form
                        .loginPage("/login")              // Custom login page
                        .defaultSuccessUrl("/", true)     // Redirect after login
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/")            // Redirect after logout
                        .permitAll()
                )
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/h2-console/**") // Disable CSRF for H2 console
                )
                .headers(headers -> headers
                        .frameOptions(frame -> frame.sameOrigin()) // Allow H2 console to render in iframe
                );

        return http.build();
    }
}