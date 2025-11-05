package tacos.tacos01;

// marks this class as a web controller
import org.springframework.stereotype.Controller;

// maps HTTP GET requests to handler methods
import org.springframework.web.bind.annotation.GetMapping;

// @Controller tells Spring this class should handle web requests
// Spring will automatically create an instance of this class
@Controller
public class HomeController {

    // @GetMapping maps HTTP GET requests for "/" (root URL) to this method
    // When someone visits http://localhost:8080/, this method runs
    @GetMapping("/")
    public String home() {
        // Returns "home" - Spring looks for a template named "home.html"
        // in src/main/resources/templates/ folder
        return "home";
    }
}