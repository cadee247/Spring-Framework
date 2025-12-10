package devdarvegga.runnerz.run;

import java.util.*;

/**
 * In-memory implementation of a Run repository.
 * Stores Run objects in a HashMap for quick access.
 */
public class InMemoryRunRepository {

    private final Map<Integer, Run> runs = new HashMap<>();

    /**
     * Returns all runs in the repository.
     *
     * @return List of Run objects.
     */
    public List<Run> findAll() {
        return new ArrayList<>(runs.values());
    }

    /**
     * Finds a run by its ID.
     *
     * @param id Run ID.
     * @return Optional containing the Run if found, empty otherwise.
     */
    public Optional<Run> findById(int id) {
        return Optional.ofNullable(runs.get(id));
    }

    /**
     * Creates a new run.
     *
     * @param run Run object to add.
     */
    public void create(Run run) {
        runs.put(run.id(), run);
    }

    /**
     * Updates an existing run by ID.
     *
     * @param run Run object with updated data.
     * @param id  ID of the run to update.
     */
    public void update(Run run, int id) {
        if (!runs.containsKey(id)) {
            throw new RunNotFoundException();
        }
        runs.put(id, run);
    }

    /**
     * Deletes a run by ID.
     *
     * @param id ID of the run to delete.
     */
    public void delete(int id) {
        if (!runs.containsKey(id)) {
            throw new RunNotFoundException();
        }
        runs.remove(id);
    }

    /**
     * Finds all runs by location.
     *
     * @param location Location to filter by.
     * @return List of Run objects at the given location.
     */
    public List<Run> findByLocation(Location location) {
        List<Run> filtered = new ArrayList<>();
        for (Run run : runs.values()) {
            if (run.location() == location) {
                filtered.add(run);
            }
        }
        return filtered;
    }
}
