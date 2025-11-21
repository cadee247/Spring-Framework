package devdarvegga.runnerz;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Basic smoke test to verify that the Spring application context loads successfully.
 *
 * This test ensures that all beans can be created and wired without errors.
 * It's often used as a sanity check in CI pipelines or during refactoring.
 */
@SpringBootTest
class ApplicationTests {

    /**
     * Verifies that the Spring Boot application context loads without throwing exceptions.
     *
     * No assertions are needed — failure to load the context will cause the test to fail.
     */
    @Test
    void contextLoads() {
        // Intentionally empty — success means the app context loaded without crashing
    }
}