package tacocloud.tacos06.data;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import tacocloud.tacos06.TacoOrder;
import tacocloud.tacos06.User;

import java.util.List;

/**
 * Repository interface for TacoOrder entities.
 * Extends CrudRepository to provide basic CRUD operations.
 * Adds custom query to fetch orders by user, sorted by placement time.
 */
public interface OrderRepository extends CrudRepository<TacoOrder, Long> {

    List<TacoOrder> findByUserOrderByPlacedAtDesc(User user, Pageable pageable);

    // Optional: non-paginated version
    // List<TacoOrder> findByUserOrderByPlacedAtDesc(User user);
}