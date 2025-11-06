package tacos.tacos02;

import lombok.Data;

/**
 * Represents a single taco ingredient with an ID, name, and category type.
 * Used to build tacos in the Taco Cloud application.
 */
@Data // Lombok annotation that auto-generates getters, setters, toString, equals, and hashCode
public class Ingredient {

    // Unique identifier for the ingredient (e.g., "CHED" for Cheddar)
    private final String id;

    // Display name of the ingredient (e.g., "Cheddar")
    private final String name;

    // Category of the ingredient (e.g., CHEESE, PROTEIN)
    private final Type type;

    /**
     * Enum representing the different types of ingredients.
     * Used to group ingredients in the taco design form.
     */
    public enum Type {
        WRAP,     // Tortillas or wraps
        PROTEIN,  // Meats or plant-based proteins
        VEGGIES,  // Vegetables
        CHEESE,   // Cheese options
        SAUCE     // Sauces and dressings
    }
}