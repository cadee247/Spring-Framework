package devdarvegga.runnerz.user;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;

/**
 * REST client for accessing user data from an external API.
 * Uses Spring's RestClient to interact with https://jsonplaceholder.typicode.com/users.
 *
 * This component is useful for mocking external user data in development or testing scenarios.
 */
@Component
public class UserRestClient {

    private final RestClient restClient;

    /**
     * Constructor that builds a RestClient with a predefined base URL.
     * Uses RestClient.Builder for flexible configuration.
     */
    public UserRestClient(RestClient.Builder builder) {
        this.restClient = builder
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .build();
    }

    /**
     * Fetches all users from the external API.
     * @return List of User objects.
     */
    public List<User> findAll() {
        return restClient.get()
                .uri("/users")
                .retrieve()
                .body(new ParameterizedTypeReference<List<User>>() {});
    }

    /**
     * Fetches a single user by ID from the external API.
     * @param id The user's ID.
     * @return A User object.
     */
    public User findById(Integer id) {
        return restClient.get()
                .uri("/users/{id}", id)
                .retrieve()
                .body(User.class);
    }
}