package devdarvegga.runnerz.run;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.web.client.RestClient;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Full integration test for RunController using a real HTTP server and test database.
 * Verifies REST endpoints and repository behavior with seeded data.
 */
@Sql(scripts = {"/schema.sql", "/data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RunControllerIntTest {

    @LocalServerPort
    int randomServerPort;

    RestClient restClient;

    /**
     * Initializes RestClient with the randomized test server port before each test.
     */
    @BeforeEach
    void setUp() {
        restClient = RestClient.create("http://localhost:" + randomServerPort);
    }

    /**
     * Verifies that GET /api/runs returns all seeded runs.
     * Expects 10 runs based on data.sql.
     */
    @Test
    void shouldFindAllRuns() {
        List<Run> runs = restClient.get()
                .uri("/api/runs")
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});
        assertEquals(10, runs.size());
    }

    /**
     * Verifies that GET /api/runs/{id} returns the correct run.
     * Asserts all fields match expected values from data.sql.
     */
    @Test
    void shouldFindRunById() {
        Run run = restClient.get()
                .uri("/api/runs/1")
                .retrieve()
                .body(Run.class);

        assertAll(
                () -> assertEquals(1, run.id()),
                () -> assertEquals("Run 1", run.title()),
                () -> assertEquals("2024-02-20T06:00", run.startedOn().toString()),
                () -> assertEquals("2024-02-20T07:00", run.completedOn().toString()),
                () -> assertEquals(5, run.miles()),
                () -> assertEquals(Location.INDOOR, run.location())
        );
    }

    /**
     * Verifies that POST /api/runs creates a new run.
     * Asserts that the response status is 201 Created.
     */
    @Test
    void shouldCreateNewRun() {
        Run run = new Run(11, "Evening Run", LocalDateTime.now(), LocalDateTime.now().plusHours(2), 10, Location.OUTDOOR);

        ResponseEntity<Void> newRun = restClient.post()
                .uri("/api/runs")
                .body(run)
                .retrieve()
                .toBodilessEntity();

        assertEquals(201, newRun.getStatusCodeValue());
    }

    /**
     * Verifies that PUT /api/runs/{id} updates an existing run.
     * Asserts that the response status is 204 No Content.
     */
    @Test
    void shouldUpdateExistingRun() {
        Run run = restClient.get()
                .uri("/api/runs/1")
                .retrieve()
                .body(Run.class);

        ResponseEntity<Void> updatedRun = restClient.put()
                .uri("/api/runs/1")
                .body(run)
                .retrieve()
                .toBodilessEntity();

        assertEquals(204, updatedRun.getStatusCodeValue());
    }

    /**
     * Verifies that DELETE /api/runs/{id} removes a run.
     * Asserts that the response status is 204 No Content.
     */
    @Test
    void shouldDeleteRun() {
        ResponseEntity<Void> response = restClient.delete()
                .uri("/api/runs/1")
                .retrieve()
                .toBodilessEntity();

        assertEquals(204, response.getStatusCodeValue());
    }

    /**
     * Verifies that GET /api/runs/search?location=INDOOR returns filtered results.
     * Expects 5 indoor runs based on data.sql.
     */
    @Test
    void shouldFindRunsByLocation() {
        List<Run> indoorRuns = restClient.get()
                .uri("/api/runs/search?location=INDOOR")
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});

        assertEquals(5, indoorRuns.size()); // Based on your data.sql
    }
}