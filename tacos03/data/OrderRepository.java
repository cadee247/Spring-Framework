package tacos.tacos03.data;

import java.util.Optional;

import tacos.tacos03.TacoOrder;

/**
 * OrderRepository defines the contract for persisting and retrieving TacoOrder entities.
 * Implementations may use JDBC, JPA, or other data access strategies.
 */
public interface OrderRepository {

    /**
     * Saves a TacoOrder to the data store.
     * @param order the TacoOrder to save
     * @return the saved TacoOrder, potentially with a generated ID
     */
    TacoOrder save(TacoOrder order);

    /**
     * Finds a TacoOrder by its unique ID.
     * @param id the ID of the TacoOrder
     * @return an Optional containing the TacoOrder if found, or empty if not
     */
    Optional<TacoOrder> findById(Long id);

}