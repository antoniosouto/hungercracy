package hungercracy;

import java.util.List;

public interface RestaurantService {
	
	public List<Restaurant> getAllRestaurants();
	public Restaurant getRestaurantByName(String name);
	public Restaurant getRestaurantById(Long id);
	public List<Restaurant> getAllRestaurantsNotYetChoosenThisWeek();
	public List<Restaurant> getAllRestaurantsNotYetChoosenThisWeekSortedByNameAndByVotes();
}
