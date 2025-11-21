package tacocloud.tacos06.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import tacocloud.tacos06.Ingredient;
import tacocloud.tacos06.data.IngredientRepository;

/**
 * Converts a String ID into an Ingredient object using the IngredientRepository.
 * Used by Spring to bind form data to Ingredient entities.
 */
@Component
public class IngredientByIdConverter implements Converter<String, Ingredient> {

    private IngredientRepository ingredientRepo;

    @Autowired
    public IngredientByIdConverter(IngredientRepository ingredientRepo) {
        this.ingredientRepo = ingredientRepo;
    }

    @Override
    public Ingredient convert(String id) {
        return ingredientRepo.findById(id).orElse(null);
    }

}