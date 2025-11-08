package tacos.tacos03.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import tacos.tacos03.Ingredient;

/**
 * Raw implementation of {@link IngredientRepository} for
 * comparison with {@link JdbcIngredientRepository} to illustrate
 * the power and convenience of using {@link JdbcTemplate}.
 * This version manually manages connections, statements, and result sets.
 */
public class RawJdbcIngredientRepository implements IngredientRepository {

    private DataSource dataSource;

    // Constructor injection of DataSource
    public RawJdbcIngredientRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * Retrieves all ingredients from the database using raw JDBC.
     * @return a list of Ingredient objects
     */
    @Override
    public Iterable<Ingredient> findAll() {
        List<Ingredient> ingredients = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = dataSource.getConnection(); // Manually acquire connection
            statement = connection.prepareStatement(
                    "select id, name, type from Ingredient"); // Prepare SQL query
            resultSet = statement.executeQuery(); // Execute query

            while (resultSet.next()) {
                Ingredient ingredient = new Ingredient(
                        resultSet.getString("id"),
                        resultSet.getString("name"),
                        Ingredient.Type.valueOf(resultSet.getString("type"))); // Map row to Ingredient
                ingredients.add(ingredient);
            }

        } catch (SQLException e) {
            // TODO: Proper error handling should be implemented here
        } finally {
            // Manual resource cleanup
            if (resultSet != null) {
                try { resultSet.close(); } catch (SQLException e) {}
            }
            if (statement != null) {
                try { statement.close(); } catch (SQLException e) {}
            }
            if (connection != null) {
                try { connection.close(); } catch (SQLException e) {}
            }
        }

        return ingredients;
    }

    /**
     * Finds an ingredient by its ID using raw JDBC.
     * @param id the ID of the ingredient
     * @return an Optional containing the Ingredient if found
     */
    @Override
    public Optional<Ingredient> findById(String id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement(
                    "select id, name, type from Ingredient where id=?");
            statement.setString(1, id); // Set query parameter
            resultSet = statement.executeQuery();

            Ingredient ingredient = null;
            if (resultSet.next()) {
                ingredient = new Ingredient(
                        resultSet.getString("id"),
                        resultSet.getString("name"),
                        Ingredient.Type.valueOf(resultSet.getString("type")));
            }

            return Optional.of(ingredient); // May throw if ingredient is null

        } catch (SQLException e) {
            // TODO: Proper error handling should be implemented here
        } finally {
            if (resultSet != null) {
                try { resultSet.close(); } catch (SQLException e) {}
            }
            if (statement != null) {
                try { statement.close(); } catch (SQLException e) {}
            }
            if (connection != null) {
                try { connection.close(); } catch (SQLException e) {}
            }
        }

        return Optional.empty(); // Fallback if exception occurs or no result
    }

    /**
     * Save method is not implemented in this raw version.
     * Intended for comparison only.
     */
    @Override
    public Ingredient save(Ingredient ingredient) {
        // TODO: I only needed one method for comparison purposes, so
        //       I've not bothered implementing this one (yet).
        return null;
    }

}