package tacos.tacos03.tacos.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import tacos.tacos03.tacos.Ingredient;
import tacos.tacos03.tacos.data.IngredientRepository;

/**
 * Converts a String ingredient ID into an Ingredient object
 * for use in form binding and model population.
 */
@Component
public class IngredientByIdConverter implements Converter<String, Ingredient> {

    private final IngredientRepository ingredientRepo;

    /**
     * Injects the IngredientRepository used to look up ingredients by ID.
     */
    @Autowired
    public IngredientByIdConverter(IngredientRepository ingredientRepo) {
        this.ingredientRepo = ingredientRepo;
    }

    /**
     * Converts the given ingredient ID into an Ingredient object.
     * @param id the ingredient ID from the form
     * @return the matching Ingredient, or null if not found or blank
     */
    @Override
    public Ingredient convert(String id) {
        if (id == null || id.trim().isEmpty()) {
            return null;
        }
        return ingredientRepo.findById(id).orElse(null);
    }
}