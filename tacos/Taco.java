package tacos.tacos03.tacos;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.Data;

/**
 * Represents a single taco creation with a name and selected ingredients.
 */
@Data
public class Taco {

    /** Unique identifier for the taco */
    private Long id;

    /** Timestamp when the taco was created */
    private Date createdAt = new Date();

    /** Name of the taco, must be at least 5 characters */
    @NotNull(message = "Taco name is required")
    @Size(min = 5, message = "Name must be at least 5 characters long")
    private String name;

    /** List of ingredient references, must contain at least one */
    @Size(min = 1, message = "You must choose at least 1 ingredient")
    private List<IngredientRef> ingredients = new ArrayList<>();

    /**
     * Adds an ingredient to the taco by reference.
     * @param ingredient the ingredient to add
     */
    public void addIngredient(Ingredient ingredient) {
        if (ingredient != null && ingredient.getId() != null) {
            this.ingredients.add(new IngredientRef(ingredient.getId()));
        }
    }
}