package devdarvegga.runnerz.run;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

/**
 * JDBC-backed implementation of RunRepository.
 * Uses JdbcClient to interact with the 'run' table in the database.
 */
@Repository
public class JdbcRunRepository implements RunRepository {

    private final JdbcClient jdbcClient;

    /**
     * Constructor injection of JdbcClient.
     */
    public JdbcRunRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    /**
     * Retrieves all runs from the database.
     */
    public List<Run> findAll() {
        return jdbcClient.sql("SELECT * FROM run")
                .query(Run.class)
                .list();
    }

    /**
     * Finds a run by its ID.
     * Returns an Optional containing the run if found.
     */
    public Optional<Run> findById(Integer id) {
        return jdbcClient.sql("SELECT id, title, started_on, completed_on, miles, location FROM run WHERE id = :id")
                .param("id", id)
                .query(Run.class)
                .optional();
    }

    /**
     * Retrieves all runs that match the given location.
     */
    public List<Run> findByLocation(String location) {
        return jdbcClient.sql("SELECT * FROM run WHERE location = :location")
                .param("location", location)
                .query(Run.class)
                .list();
    }

    /**
     * Inserts a new run into the database.
     * Asserts that exactly one row was affected.
     */
    public void create(Run run) {
        var updated = jdbcClient.sql("INSERT INTO run (id, title, started_on, completed_on, miles, location) VALUES (?, ?, ?, ?, ?, ?)")
                .params(List.of(
                        run.id(),
                        run.title(),
                        run.startedOn(),
                        run.completedOn(),
                        run.miles(),
                        run.location().toString()
                ))
                .update();

        Assert.state(updated == 1, "Failed to create run " + run.title());
    }

    /**
     * Updates an existing run by ID.
     * Asserts that exactly one row was affected.
     */
    public void update(Run run, Integer id) {
        var updated = jdbcClient.sql("UPDATE run SET title = ?, started_on = ?, completed_on = ?, miles = ?, location = ? WHERE id = ?")
                .params(List.of(
                        run.title(),
                        run.startedOn(),
                        run.completedOn(),
                        run.miles(),
                        run.location().toString(),
                        id
                ))
                .update();

        Assert.state(updated == 1, "Failed to update run " + run.title());
    }

    /**
     * Deletes a run by ID.
     * Asserts that exactly one row was affected.
     */
    public void delete(Integer id) {
        var updated = jdbcClient.sql("DELETE FROM run WHERE id = :id")
                .param("id", id)
                .update();

        Assert.state(updated == 1, "Failed to delete run " + id);
    }

    /**
     * Returns the total number of runs in the database.
     */
    public int count() {
        return jdbcClient.sql("SELECT * FROM run")
                .query()
                .listOfRows()
                .size();
    }

    /**
     * Saves a list of runs by calling create() on each.
     */
    public void saveAll(List<Run> runs) {
        runs.forEach(this::create);
    }
}