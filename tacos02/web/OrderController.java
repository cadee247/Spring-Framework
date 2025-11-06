package tacos.tacos02.web;

import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.bind.annotation.ModelAttribute;

import lombok.extern.slf4j.Slf4j;
import tacos.tacos02.TacoOrder;

/**
 * Handles requests related to taco orders.
 * Displays the order form, processes submitted orders,
 * and manages session-scoped TacoOrder objects.
 */
@Slf4j // Enables logging with SLF4J
@Controller // Marks this class as a Spring MVC controller
@RequestMapping("/orders") // Maps all requests starting with /orders
@SessionAttributes("tacoOrder") // Keeps TacoOrder in session across multiple requests
public class OrderController {

    /**
     * Handles GET requests to /orders/current.
     * Displays the order form view.
     */
    @GetMapping("/current")
    public String orderForm() {
        return "orderForm"; // Renders orderForm.html
    }

    /**
     * Initializes a new TacoOrder if one doesn't already exist in the session.
     * This ensures the form has a backing object.
     */
    @ModelAttribute(name = "tacoOrder")
    public TacoOrder tacoOrder() {
        return new TacoOrder();
    }

    /**
     * Handles POST requests when the user submits the order form.
     * Validates the order, logs it, clears the session, and redirects to the homepage.
     */
    @PostMapping
    public String processOrder(
            @Valid TacoOrder order,        // Validates the submitted order
            Errors errors,                 // Holds validation errors
            SessionStatus sessionStatus)  // Used to clear the session

    {
        if (errors.hasErrors()) {
            return "orderForm"; // Redisplay form if there are validation errors
        }

        log.info("Order submitted: {}", order); // Log the order details
        sessionStatus.setComplete(); // Clear the tacoOrder from session

        return "redirect:/"; // Redirect to homepage after successful submission
    }
}