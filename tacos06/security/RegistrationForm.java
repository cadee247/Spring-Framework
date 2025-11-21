package tacocloud.tacos06.security;

import org.springframework.security.crypto.password.PasswordEncoder;
import lombok.Data;
import tacocloud.tacos06.User;

/**
 * DTO for capturing user registration input.
 * Converts form fields into a User entity with encoded password.
 */
@Data
public class RegistrationForm {

    private String username;
    private String password;
    private String fullname;
    private String street;
    private String city;
    private String state;
    private String zip;
    private String phone;

    /**
     * Converts this form into a User entity.
     * Uses Spring Security's PasswordEncoder to hash the password before storage.
     *
     * @param passwordEncoder the encoder used to securely hash the password
     * @return a new User object ready for persistence
     */
    public User toUser(PasswordEncoder passwordEncoder) {
        return new User(
                username,
                passwordEncoder.encode(password),
                fullname,
                street,
                city,
                state,
                zip,
                phone
        );
    }

}