package tacocloud.tacos06.data;

import org.springframework.data.repository.CrudRepository;
import tacocloud.tacos06.User;

/**
 * Repository interface for User entities.
 * Provides CRUD operations and a custom finder for authentication.
 */
public interface UserRepository extends CrudRepository<User, Long> {

    User findByUsername(String username);

}