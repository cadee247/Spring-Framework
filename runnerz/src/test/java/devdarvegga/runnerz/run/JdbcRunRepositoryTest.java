package devdarvegga.runnerz.run;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Integration test for JdbcRunRepository using an in-memory test database.
 * Verifies that JDBC queries return expected results.
 */
@JdbcTest // Loads only JDBC-related components (DataSource, JdbcTemplate, etc.)
@Import(JdbcRunRepository.class) // Explicitly imports the repository under test
class JdbcRunRepositoryTest {

    @Autowired
    JdbcRunRepository repository;

    /**
     * Verifies that findAll() returns the expected number of runs.
     * Assumes test data is preloaded via schema.sql and data.sql.
     */
    @Test
    void shouldFindAllRuns() {
        List<Run> runs = repository.findAll();

        // Adjust this number based on how many rows are inserted in your test data.sql
        assertEquals(1, runs.size());
    }
}