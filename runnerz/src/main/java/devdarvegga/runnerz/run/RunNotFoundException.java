package devdarvegga.runnerz.run;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Custom exception thrown when a requested Run is not found in the repository.
 *
 * Annotated with @ResponseStatus to automatically return a 404 Not Found response
 * when this exception is thrown from a controller method.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class RunNotFoundException extends RuntimeException {

    /**
     * Constructs a new RunNotFoundException with a default message.
     * This message will be included in the HTTP response body if not overridden.
     */
    public RunNotFoundException() {
        super("Run Not Found");
    }
}