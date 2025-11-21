package tacocloud.tacos06.data;

import org.springframework.data.repository.CrudRepository;
import tacocloud.tacos06.Ingredient;

/**
 * Repository interface for Ingredient entities.
 * Extends CrudRepository to provide basic CRUD operations.
 */
public interface IngredientRepository extends CrudRepository<Ingredient, String> {
}