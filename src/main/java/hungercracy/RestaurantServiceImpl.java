package hungercracy;

import java.util.Iterator;
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
		for (Iterator<Restaurant> iter = restList.listIterator(); iter.hasNext(); ) {
			Restaurant restaurant = iter.next();
			if (restaurant.alreadyChoosenThisWeek() && !restaurant.isWinner()) {
				iter.remove();
				}
			}
		return restList;
	}
	
	public List<Restaurant> getAllRestaurantsNotYetChoosenThisWeekSortedByNameAndByVotes() {
		List<Restaurant> restList = getAllRestaurantsNotYetChoosenThisWeek();
		restList.sort((a,b)->a.getName().compareTo(b.getName()));
		restList.sort(((a,b)->b.getVotes()-a.getVotes())); //reversed order
		return restList;
	}
}
