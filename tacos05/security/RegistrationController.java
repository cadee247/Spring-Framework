package tacos.tacos05.security;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import tacos.tacos05.data.UserRepository;

/**
 * Handles user registration requests.
 * Integrates with Spring Security to encode passwords before saving users.
 */
@Controller
@RequestMapping("/register") // Maps all requests under /register to this controller
public class RegistrationController {

    private UserRepository userRepo;           // Repository for persisting User entities
    private PasswordEncoder passwordEncoder;   // Used to securely hash user passwords

    /**
     * Constructor injection of dependencies.
     * Spring will automatically wire the UserRepository and PasswordEncoder beans.
     */
    public RegistrationController(
            UserRepository userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Displays the registration form.
     * Mapped to GET /register
     *
     * @return the name of the Thymeleaf template for registration
     */
    @GetMapping
    public String registerForm() {
        return "registration";
    }

    /**
     * Processes the submitted registration form.
     * Converts the form into a User object, encodes the password, and saves it.
     *
     * Spring Security behavior:
     * - PasswordEncoder hashes the raw password before storage.
     * - The saved User can later be authenticated via Spring Security's login flow.
     *
     * @param form the submitted RegistrationForm containing user input
     * @return redirect to login page after successful registration
     */
    @PostMapping
    public String processRegistration(RegistrationForm form) {
        userRepo.save(form.toUser(passwordEncoder)); // Converts form to User and saves it
        return "redirect:/login"; // Redirects to login page after successful registration
    }

}