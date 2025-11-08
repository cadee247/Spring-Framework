package tacos.tacos03.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import tacos.tacos03.Ingredient;

/**
 * JdbcIngredientRepository is a JDBC-based implementation of the IngredientRepository interface.
 * It uses Spring's JdbcTemplate to interact with the database.
 */
@Repository
public class JdbcIngredientRepository implements IngredientRepository {

    private JdbcTemplate jdbcTemplate;

    // Constructor injection of JdbcTemplate
    public JdbcIngredientRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Retrieves all ingredients from the database.
     * @return an Iterable of all Ingredient objects.
     */
    @Override
    public Iterable<Ingredient> findAll() {
        return jdbcTemplate.query(
                "select id, name, type from Ingredient", // SQL query to fetch all ingredients
                this::mapRowToIngredient // Row mapping function
        );
    }

    /**
     * Finds an ingredient by its ID.
     * @param id the ID of the ingredient to find.
     * @return an Optional containing the Ingredient if found, or empty if not.
     */
    @Override
    public Optional<Ingredient> findById(String id) {
        List<Ingredient> results = jdbcTemplate.query(
                "select id, name, type from Ingredient where id=?", // SQL query with parameter
                this::mapRowToIngredient, // Row mapping function
                id // Parameter value
        );
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }

    /**
     * Saves a new ingredient to the database.
     * @param ingredient the Ingredient object to save.
     * @return the saved Ingredient object.
     */
    @Override
    public Ingredient save(Ingredient ingredient) {
        jdbcTemplate.update(
                "insert into Ingredient (id, name, type) values (?, ?, ?)", // SQL insert statement
                ingredient.getId(), // ID parameter
                ingredient.getName(), // Name parameter
                ingredient.getType().toString() // Type parameter as string
        );
        return ingredient;
    }

    /**
     * Maps a single row of the ResultSet to an Ingredient object.
     * @param row the ResultSet row.
     * @param rowNum the row number.
     * @return the mapped Ingredient object.
     * @throws SQLException if a database access error occurs.
     */
    private Ingredient mapRowToIngredient(ResultSet row, int rowNum)
            throws SQLException {
        return new Ingredient(
                row.getString("id"),
                row.getString("name"),
                Ingredient.Type.valueOf(row.getString("type")) // Convert string to enum
        );
    }

    /*
     * Alternative implementation of findById using queryForObject.
     * This version is commented out in favor of using query with Optional.
     *
     * @Override
     * public Ingredient findById(String id) {
     *   return jdbcTemplate.queryForObject(
     *       "select id, name, type from Ingredient where id=?",
     *       new RowMapper<Ingredient>() {
     *         public Ingredient mapRow(ResultSet rs, int rowNum)
     *             throws SQLException {
     *           return new Ingredient(
     *               rs.getString("id"),
     *               rs.getString("name"),
     *               Ingredient.Type.valueOf(rs.getString("type")));
     *         };
     *       }, id);
     * }
     */
}