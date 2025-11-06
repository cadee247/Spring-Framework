package tacos.tacos03.tacos.data;

import tacos.tacos03.tacos.Ingredient;
import tacos.tacos03.tacos.Ingredient.Type;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

/**
 * Raw implementation of {@link IngredientRepository} for
 * comparison with {@link JdbcIngredientRepository} to illustrate
 * the power of using {@link org.springframework.jdbc.core.JdbcTemplate}.
 */
public class RawJdbcIngredientRepository implements IngredientRepository {

    private final DataSource dataSource;

    public RawJdbcIngredientRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Iterable<Ingredient> findAll() {
        List<Ingredient> ingredients = new ArrayList<>();
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(
                        "SELECT id, name, type FROM Ingredient");
                ResultSet resultSet = statement.executeQuery()
        ) {
            while (resultSet.next()) {
                Ingredient ingredient = new Ingredient(
                        resultSet.getString("id"),
                        resultSet.getString("name"),
                        Type.valueOf(resultSet.getString("type"))
                );
                ingredients.add(ingredient);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching ingredients: " + e.getMessage());
        }
        return ingredients;
    }

    @Override
    public Optional<Ingredient> findById(String id) {
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(
                        "SELECT id, name, type FROM Ingredient WHERE id=?")
        ) {
            statement.setString(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Ingredient ingredient = new Ingredient(
                            resultSet.getString("id"),
                            resultSet.getString("name"),
                            Type.valueOf(resultSet.getString("type"))
                    );
                    return Optional.of(ingredient);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching ingredient by ID: " + e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public Ingredient save(Ingredient ingredient) {
        // Not implemented in raw JDBC version
        return null;
    }
}