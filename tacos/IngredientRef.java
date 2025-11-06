package tacos.tacos03.tacos;

import lombok.Data;

/**
 * A lightweight reference to an ingredient by ID,
 * used for form binding and taco composition.
 */
@Data
public class IngredientRef {

    /** The ID of the referenced ingredient */
    private final String ingredient;
}