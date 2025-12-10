package devdarvegga.runnerz.run;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * Immutable data model representing a single running session.
 * Includes validation annotations and derived metrics like duration and average pace.
 */
public record Run(
        Integer id, // Unique identifier for the run

        @NotEmpty
        String title, // Descriptive title of the run (e.g., "Morning Sprint")

        LocalDateTime startedOn, // Timestamp when the run started

        LocalDateTime completedOn, // Timestamp when the run ended

        @Positive
        Integer miles, // Distance covered in miles (must be > 0)

        Location location // Enum indicating whether the run was indoor or outdoor
) {

    /**
     * Compact constructor with validation logic.
     * Ensures completedOn is chronologically after startedOn.
     */
    public Run {
        if (!completedOn.isAfter(startedOn)) {
            throw new IllegalArgumentException("Completed On must be after Started On");
        }
    }

    /**
     * Calculates the total duration of the run.
     * @return Duration between start and completion.
     */
    public Duration getDuration() {
        return Duration.between(startedOn, completedOn);
    }

    /**
     * Calculates the average pace in minutes per mile.
     * @return Integer value of duration divided by miles.
     */
    public Integer getAvgPace() {
        return Math.toIntExact(getDuration().toMinutes() / miles);
    }
}