package devdarvegga.runnerz.run;

import java.util.List;

/**
 * Wrapper record for a list of Run objects.
 * Used for bulk operations like JSON deserialization or batch inserts.
 *
 * This structure allows Jackson to map a JSON object like:
 * {
 *   "runs": [ { ... }, { ... }, ... ]
 * }
 * directly into a Runs instance.
 */
public record Runs(List<Run> runs) {
}