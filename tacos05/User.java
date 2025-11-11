package tacos.tacos05;

import java.util.Arrays;
import java.util.Collection;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

/**
 * Represents an authenticated user in the Taco Cloud system.
 * Implements Spring Security's UserDetails interface for login and role-based access.
 * Persisted as a JPA entity in the "users" table.
 */
@Entity
@Table(name = "users") // Avoids conflict with SQL reserved keyword "user"
@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true) // Required by JPA
@RequiredArgsConstructor // Used by RegistrationForm to construct a new user
public class User implements UserDetails {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id; // Auto-generated primary key

    // Required fields for authentication and user profile
    private final String username;
    private final String password;
    private final String fullname;
    private final String street;
    private final String city;
    private final String state;
    private final String zip;
    private final String phoneNumber;

    /**
     * Returns the user's granted authorities.
     * All users are assigned the "ROLE_USER" authority by default.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
    }

    // Account status flags — all return true for simplicity
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}