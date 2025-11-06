package tacos.tacos02.web;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

import tacos.tacos02.Ingredient;
import tacos.tacos02.Ingredient.Type;
import tacos.tacos02.Taco;
import tacos.tacos02.TacoOrder;

/**
 * Handles requests for designing a taco.
 * Populates the model with ingredients, manages session-scoped TacoOrder,
 * and processes taco submissions.
 */
@Slf4j // Enables logging with SLF4J
@Controller // Marks this class as a Spring MVC controller
@RequestMapping("/design") // Maps all requests starting with /design
@SessionAttributes("tacoOrder") // Persists TacoOrder across multiple requests
public class DesignTacoController {

    /**
     * Adds categorized ingredients to the model for rendering in the design form.
     */
    @ModelAttribute
    public void addIngredientsToModel(Model model) {
        List<Ingredient> ingredients = Arrays.asList(
                new Ingredient("FLTO", "Flour Tortilla", Type.WRAP),
                new Ingredient("COTO", "Corn Tortilla", Type.WRAP),
                new Ingredient("GRBF", "Ground Beef", Type.PROTEIN),
                new Ingredient("CARN", "Carnitas", Type.PROTEIN),
                new Ingredient("TMTO", "Diced Tomatoes", Type.VEGGIES),
                new Ingredient("LETC", "Lettuce", Type.VEGGIES),
                new Ingredient("CHED", "Cheddar", Type.CHEESE),
                new Ingredient("JACK", "Monterrey Jack", Type.CHEESE),
                new Ingredient("SLSA", "Salsa", Type.SAUCE),
                new Ingredient("SRCR", "Sour Cream", Type.SAUCE)
        );

        // Group ingredients by type and add each group to the model
        for (Type type : Type.values()) {
            model.addAttribute(type.toString().toLowerCase(),
                    filterByType(ingredients, type));
        }
    }

    /**
     * Initializes a new TacoOrder if one doesn't already exist in the session.
     */
    @ModelAttribute(name = "tacoOrder")
    public TacoOrder order() {
        return new TacoOrder();
    }

    /**
     * Initializes a new Taco object for form binding.
     */
    @ModelAttribute(name = "taco")
    public Taco taco() {
        return new Taco();
    }

    /**
     * Handles GET requests to /design and returns the taco design view.
     */
    @GetMapping
    public String showDesignForm() {
        return "design";
    }

    /**
     * Handles POST requests when the user submits a taco design.
     * Validates the taco, adds it to the order, and redirects to the order form.
     */
    @PostMapping
    public String processTaco(
            @Valid Taco taco, Errors errors,
            @ModelAttribute TacoOrder tacoOrder) {

        if (errors.hasErrors()) {
            return "design"; // Redisplay form with validation errors
        }

        tacoOrder.addTaco(taco); // Add taco to the current order
        log.info("Processing taco: {}", taco);

        return "redirect:/orders/current"; // Redirect to order form
    }

    /**
     * Filters a list of ingredients by their type.
     */
    private Iterable<Ingredient> filterByType(List<Ingredient> ingredients, Type type) {
        return ingredients.stream()
                .filter(ingredient -> ingredient.getType().equals(type))
                .collect(Collectors.toList());
    }
}