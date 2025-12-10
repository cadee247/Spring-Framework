package devdarvegga.runnerz.run;

import java.util.List;
import java.util.Optional;

/**
 * Abstraction for data access operations related to Run entities.
 * Allows flexible switching between JDBC, in-memory, or mock implementations.
 */
public interface RunRepository {

    /**
     * Retrieves all runs from the data source.
     * @return List of all Run records.
     */
    List<Run> findAll();

    /**
     * Finds a run by its unique ID.
     * @param id The run's identifier.
     * @return Optional containing the Run if found, or empty if not.
     */
    Optional<Run> findById(Integer id);

    /**
     * Persists a new run to the data source.
     * @param run The run to create.
     */
    void create(Run run);

    /**
     * Updates an existing run by ID.
     * @param run The updated run data.
     * @param id The ID of the run to update.
     */
    void update(Run run, Integer id);

    /**
     * Deletes a run by its ID.
     * @param id The ID of the run to delete.
     */
    void delete(Integer id);

    /**
     * Returns the total number of runs in the data source.
     * @return Integer count of runs.
     */
    int count();

    /**
     * Saves a batch of runs to the data source.
     * Typically used for bulk inserts or initial seeding.
     * @param runs List of runs to save.
     */
    void saveAll(List<Run> runs);

    /**
     * Finds all runs that match a given location.
     * @param location The location filter (e.g., "INDOOR", "OUTDOOR").
     * @return List of runs matching the location.
     */
    List<Run> findByLocation(String location);
}