package tacos.tacos03.data;

import java.sql.Types;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import tacos.tacos03.IngredientRef;
import tacos.tacos03.Taco;
import tacos.tacos03.TacoOrder;

/**
 * JdbcOrderRepository is a JDBC-based implementation of the OrderRepository interface.
 * It handles saving and retrieving TacoOrder objects and their associated Taco and IngredientRef entities.
 */
@Repository
public class JdbcOrderRepository implements OrderRepository {

    private JdbcOperations jdbcOperations;

    // Constructor injection of JdbcOperations
    public JdbcOrderRepository(JdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    /**
     * Saves a TacoOrder to the database, including its tacos and ingredients.
     * Uses a transaction to ensure atomicity.
     * @param order the TacoOrder to save
     * @return the saved TacoOrder with generated ID
     */
    @Override
    @Transactional
    public TacoOrder save(TacoOrder order) {
        // Create a factory for the PreparedStatement with SQL and parameter types
        PreparedStatementCreatorFactory pscf =
                new PreparedStatementCreatorFactory(
                        "insert into Taco_Order " +
                                "(delivery_name, delivery_street, delivery_city, " +
                                "delivery_state, delivery_zip, cc_number, " +
                                "cc_expiration, cc_cvv, placed_at) " +
                                "values (?,?,?,?,?,?,?,?,?)",
                        Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
                        Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
                        Types.VARCHAR, Types.VARCHAR, Types.TIMESTAMP
                );
        pscf.setReturnGeneratedKeys(true); // Enable auto-generated key retrieval

        order.setPlacedAt(new Date()); // Set current timestamp
        PreparedStatementCreator psc =
                pscf.newPreparedStatementCreator(Arrays.asList(
                        order.getDeliveryName(),
                        order.getDeliveryStreet(),
                        order.getDeliveryCity(),
                        order.getDeliveryState(),
                        order.getDeliveryZip(),
                        order.getCcNumber(),
                        order.getCcExpiration(),
                        order.getCcCVV(),
                        order.getPlacedAt()));

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcOperations.update(psc, keyHolder); // Execute insert
        long orderId = keyHolder.getKey().longValue(); // Retrieve generated ID
        order.setId(orderId);

        // Save each taco in the order
        List<Taco> tacos = order.getTacos();
        int i = 0;
        for (Taco taco : tacos) {
            saveTaco(orderId, i++, taco);
        }

        return order;
    }

    /**
     * Saves a Taco associated with an order.
     * @param orderId the ID of the parent TacoOrder
     * @param orderKey the position of the taco in the order
     * @param taco the Taco to save
     * @return the generated ID of the saved Taco
     */
    private long saveTaco(Long orderId, int orderKey, Taco taco) {
        taco.setCreatedAt(new Date());

        PreparedStatementCreatorFactory pscf =
                new PreparedStatementCreatorFactory(
                        "insert into Taco " +
                                "(name, created_at, taco_order, taco_order_key) " +
                                "values (?, ?, ?, ?)",
                        Types.VARCHAR, Types.TIMESTAMP, Types.BIGINT, Types.BIGINT);
        pscf.setReturnGeneratedKeys(true);

        PreparedStatementCreator psc =
                pscf.newPreparedStatementCreator(Arrays.asList(
                        taco.getName(),
                        taco.getCreatedAt(),
                        orderId,
                        orderKey));

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcOperations.update(psc, keyHolder);
        long tacoId = keyHolder.getKey().longValue();
        taco.setId(tacoId);

        // Save ingredients for this taco
        saveIngredientRefs(tacoId, taco.getIngredients());

        return tacoId;
    }

    /**
     * Saves ingredient references for a taco.
     * @param tacoId the ID of the Taco
     * @param ingredientRefs the list of IngredientRef objects
     */
    private void saveIngredientRefs(long tacoId, List<IngredientRef> ingredientRefs) {
        int key = 0;
        for (IngredientRef ingredientRef : ingredientRefs) {
            jdbcOperations.update(
                    "insert into Ingredient_Ref (ingredient, taco, taco_key) " +
                            "values (?, ?, ?)",
                    ingredientRef.getIngredient(), tacoId, key++);
        }
    }

    /**
     * Finds a TacoOrder by its ID, including its tacos and ingredients.
     * @param id the ID of the TacoOrder
     * @return an Optional containing the TacoOrder if found
     */
    @Override
    public Optional<TacoOrder> findById(Long id) {
        try {
            TacoOrder order = jdbcOperations.queryForObject(
                    "select id, delivery_name, delivery_street, delivery_city, " +
                            "delivery_state, delivery_zip, cc_number, cc_expiration, " +
                            "cc_cvv, placed_at from Taco_Order where id=?",
                    (row, rowNum) -> {
                        TacoOrder tacoOrder = new TacoOrder();
                        tacoOrder.setId(row.getLong("id"));
                        tacoOrder.setDeliveryName(row.getString("delivery_name"));
                        tacoOrder.setDeliveryStreet(row.getString("delivery_street"));
                        tacoOrder.setDeliveryCity(row.getString("delivery_city"));
                        tacoOrder.setDeliveryState(row.getString("delivery_state"));
                        tacoOrder.setDeliveryZip(row.getString("delivery_zip"));
                        tacoOrder.setCcNumber(row.getString("cc_number"));
                        tacoOrder.setCcExpiration(row.getString("cc_expiration"));
                        tacoOrder.setCcCVV(row.getString("cc_cvv"));
                        tacoOrder.setPlacedAt(new Date(row.getTimestamp("placed_at").getTime()));
                        tacoOrder.setTacos(findTacosByOrderId(row.getLong("id")));
                        return tacoOrder;
                    }, id);
            return Optional.of(order);
        } catch (IncorrectResultSizeDataAccessException e) {
            return Optional.empty();
        }
    }

    /**
     * Finds all tacos associated with a given order ID.
     * @param orderId the ID of the TacoOrder
     * @return a list of Taco objects
     */
    private List<Taco> findTacosByOrderId(long orderId) {
        return jdbcOperations.query(
                "select id, name, created_at from Taco " +
                        "where taco_order=? order by taco_order_key",
                (row, rowNum) -> {
                    Taco taco = new Taco();
                    taco.setId(row.getLong("id"));
                    taco.setName(row.getString("name"));
                    taco.setCreatedAt(new Date(row.getTimestamp("created_at").getTime()));
                    taco.setIngredients(findIngredientsByTacoId(row.getLong("id")));
                    return taco;
                },
                orderId);
    }

    /**
     * Finds all ingredient references for a given taco ID.
     * @param tacoId the ID of the Taco
     * @return a list of IngredientRef objects
     */
    private List<IngredientRef> findIngredientsByTacoId(long tacoId) {
        return jdbcOperations.query(
                "select ingredient from Ingredient_Ref " +
                        "where taco = ? order by taco_key",
                (row, rowNum) -> new IngredientRef(row.getString("ingredient")),
                tacoId);
    }

}