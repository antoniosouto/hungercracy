package hungercracy;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("restaurantService")
public class RestaurantServiceImpl implements RestaurantService{

	@Autowired
	private RestaurantRepository restaurantRepository;
	
	
	@Override
	public List<Restaurant> getAllRestaurants() {
		return restaurantRepository.findAll();
	}

	@Override
	public Restaurant getRestaurantByName(String name) {
		return restaurantRepository.findByName(name);
	}

	@Override
	public Restaurant getRestaurantById(Long id) {
		return restaurantRepository.findOne(id);
	}
	
	public List<Restaurant> getAllRestaurantsNotYetChoosenThisWeek() {
		List<Restaurant> restList = getAllRestaurants();
		restList.removeIf(rest -> rest.notYetChoosenThisWeek()); // Java 8
		return restList;
	}
	
	

}
