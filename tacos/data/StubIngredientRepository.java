package tacos.tacos03.tacos.data;

import org.springframework.stereotype.Repository;
import tacos.tacos03.tacos.Ingredient;
import tacos.tacos03.tacos.Ingredient.Type;

import java.util.List;
import java.util.Optional;

@Repository
public class StubIngredientRepository implements IngredientRepository {

    private final List<Ingredient> ingredients = List.of(
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

    @Override
    public Iterable<Ingredient> findAll() {
        return ingredients;
    }

    @Override
    public Optional<Ingredient> findById(String id) {
        return ingredients.stream().filter(i -> i.getId().equals(id)).findFirst();
    }

    @Override
    public Ingredient save(Ingredient ingredient) {
        return ingredient;
    }
}