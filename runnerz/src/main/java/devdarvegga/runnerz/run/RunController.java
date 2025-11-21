package devdarvegga.runnerz.run;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Run resources.
 * Maps HTTP requests to CRUD operations via JdbcRunRepository.
 */
@RestController
@RequestMapping("/api/runs")
class RunController {

    private final JdbcRunRepository runRepository;

    /**
     * Constructor injection of the repository.
     */
    RunController(JdbcRunRepository runRepository) {
        this.runRepository = runRepository;
    }

    /**
     * GET /api/runs
     * Returns a list of all runs.
     */
    @GetMapping
    List<Run> findAll() {
        return runRepository.findAll();
    }

    /**
     * GET /api/runs/{id}
     * Returns a single run by ID.
     * Throws 404 if not found.
     */
    @GetMapping("/{id}")
    Run findById(@PathVariable Integer id) {
        return runRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Run not found."));
    }

    /**
     * POST /api/runs
     * Creates a new run.
     * Validates input and returns 201 Created.
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    void create(@Valid @RequestBody Run run) {
        runRepository.create(run);
    }

    /**
     * PUT /api/runs/{id}
     * Updates an existing run by ID.
     * Validates input and returns 204 No Content.
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}")
    void update(@Valid @RequestBody Run run, @PathVariable Integer id) {
        runRepository.update(run, id);
    }

    /**
     * DELETE /api/runs/{id}
     * Deletes a run by ID.
     * Returns 204 No Content.
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    void delete(@PathVariable Integer id) {
        runRepository.delete(id);
    }

    /**
     * GET /api/runs/search?location=INDOOR
     * Returns all runs filtered by location.
     */
    @GetMapping("/search")
    List<Run> findByLocation(@RequestParam String location) {
        return runRepository.findByLocation(location);
    }
}