package tacos.tacos03.data;

import java.util.Optional;
import tacos.tacos03.Ingredient;

/**
 * IngredientRepository defines the contract for accessing and manipulating Ingredient data.
 * This interface is typically implemented by a Spring Data MongoDB or JPA repository.
 */
public interface IngredientRepository {

    /**
     * Retrieve all Ingredient objects from the data store.
     *
     * @return an Iterable collection of all available ingredients
     */
    Iterable<Ingredient> findAll();

    /**
     * Find a specific Ingredient by its unique ID.
     *
     * @param id the identifier of the ingredient
     * @return an Optional containing the Ingredient if found, or empty if not
     */
    Optional<Ingredient> findById(String id);

    /**
     * Save or update an Ingredient in the data store.
     * If the ingredient already exists, it will be updated; otherwise, it will be inserted.
     *
     * @param ingredient the Ingredient object to persist
     * @return the saved Ingredient instance
     */
    Ingredient save(Ingredient ingredient);

}