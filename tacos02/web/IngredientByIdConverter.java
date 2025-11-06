package tacos.tacos02.web;

import java.util.HashMap;
import java.util.Map;

import tacos.tacos02.Ingredient;
import tacos.tacos02.Ingredient.Type;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * A Spring component that converts a String ID into an Ingredient object.
 * This is used by Spring MVC to automatically bind form values (like checkbox IDs)
 * to Ingredient objects when processing form submissions.
 */
@Component
public class IngredientByIdConverter implements Converter<String, Ingredient> {

    // A map that holds all known ingredients, keyed by their ID
    private Map<String, Ingredient> ingredientMap = new HashMap<>();

    /**
     * Constructor that populates the ingredient map with hardcoded ingredients.
     * Each entry maps an ID (like "FLTO") to a full Ingredient object.
     */
    public IngredientByIdConverter() {
        ingredientMap.put("FLTO",
                new Ingredient("FLTO", "Flour Tortilla", Type.WRAP));
        ingredientMap.put("COTO",
                new Ingredient("COTO", "Corn Tortilla", Type.WRAP));
        ingredientMap.put("GRBF",
                new Ingredient("GRBF", "Ground Beef", Type.PROTEIN));
        ingredientMap.put("CARN",
                new Ingredient("CARN", "Carnitas", Type.PROTEIN));
        ingredientMap.put("TMTO",
                new Ingredient("TMTO", "Diced Tomatoes", Type.VEGGIES));
        ingredientMap.put("LETC",
                new Ingredient("LETC", "Lettuce", Type.VEGGIES));
        ingredientMap.put("CHED",
                new Ingredient("CHED", "Cheddar", Type.CHEESE));
        ingredientMap.put("JACK",
                new Ingredient("JACK", "Monterrey Jack", Type.CHEESE));
        ingredientMap.put("SLSA",
                new Ingredient("SLSA", "Salsa", Type.SAUCE));
        ingredientMap.put("SRCR",
                new Ingredient("SRCR", "Sour Cream", Type.SAUCE));
    }

    /**
     * Converts a String ID (like "GRBF") into the corresponding Ingredient object.
     * This method is called automatically by Spring when binding form data.
     */
    @Override
    public Ingredient convert(String id) {
        return ingredientMap.get(id);
    }
}