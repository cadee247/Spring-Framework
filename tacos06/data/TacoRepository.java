package tacocloud.tacos06.data;

import org.springframework.data.repository.CrudRepository;
import tacocloud.tacos06.Taco;

/**
 * Repository interface for Taco entities.
 * Extends CrudRepository to provide basic CRUD operations.
 */
public interface TacoRepository extends CrudRepository<Taco, Long> {
}