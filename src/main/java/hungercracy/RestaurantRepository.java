/**
 * 
 */
package hungercracy;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface RestaurantRepository extends CrudRepository<Restaurant, Long>{
	
	Restaurant findByName(String name);
	List<Restaurant> findAll();
	Restaurant findById(Long id);
}
