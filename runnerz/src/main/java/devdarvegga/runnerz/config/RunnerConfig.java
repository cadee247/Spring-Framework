// src/main/java/devdarvegga/runnerz/config/RunnerConfig.java
package devdarvegga.runnerz.config;

import devdarvegga.runnerz.user.UserRestClient;
import devdarvegga.runnerz.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Configuration class that defines startup behavior for the application.
 * Specifically, it wires a CommandLineRunner to fetch and print user data
 * from an external API using UserRestClient.
 */
@Configuration
public class RunnerConfig {

    private static final Logger log = LoggerFactory.getLogger(RunnerConfig.class);

    /**
     * Bean that runs once after the application context is loaded.
     * Uses UserRestClient to fetch all users from the external API.
     * Prints the result to the console for verification or debugging.
     *
     * @param client Injected UserRestClient bean
     * @return CommandLineRunner that executes on startup
     */
    @Bean
    CommandLineRunner runner(UserRestClient client) {
        return args -> {
            // Fetch all users from https://jsonplaceholder.typicode.com/users
            List<User> users = client.findAll();

            // Print the list of users to the console
            System.out.println(users);

            // Optional: log the result for structured output
            log.info("Fetched {} users from external API.", users.size());
        };
    }
}