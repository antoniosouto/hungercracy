package hungercracy;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {

    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    
    @Autowired
    RestaurantService restaurantService;
    
	@Autowired
	RestaurantRepository restaurantRepository;

	@Autowired
	UserService userService;

	
	@Scheduled(cron="0 30 11 * * 1-5") //Every weekday, at 11:30 AM
	public void closeVoting() {
		log.info("Closed Voting");
		log.info("State before closing:");
		showAllUsersAndRestaurants();
		List<Restaurant> restaurantList = restaurantService.getAllRestaurantsNotYetChoosenThisWeekSortedByNameAndByVotes();
		// Assume that the first one in the list is the most voted.
		// TODO: resolve even votes by tossing a coin
		restaurantList.get(0).setWon(true);
		restaurantList.get(0).setLastDateWon(DateTimeUtil.getCurrentZonedLocalDate());
		restaurantRepository.save(restaurantList);
		log.info("State after closing:");
		showAllUsersAndRestaurants();
	}
	
	@Scheduled(cron="0 30 8 * * 1-5") //Every weekday, at 8:30 AM
	public void openVoting() {
		log.info("Open Voting");
		log.info("State before opening:");
		showAllUsersAndRestaurants();
		List<Restaurant> restaurantList = restaurantService.getAllRestaurants();
		// Assume that the first one in the list is the most voted.
		// TODO: resolve even votes by tossing a coin
		restaurantList.forEach(restaurant-> restaurant.setVotes(0));
		restaurantList.forEach(restaurant-> restaurant.setWon(false));
		restaurantRepository.save(restaurantList);
		log.info("State after closing:");
		showAllUsersAndRestaurants();
	}
	
    public void showAllUsersAndRestaurants() {
        log.info("The time is now {}", dateFormat.format(new Date()));
     	log.info("Users found with getAllRestaurants():");
     	log.info("-------------------------------");
     	userService.getAllUsers().forEach(user-> log.info(user.toString()));
     	log.info("Restaurants found with getAllRestaurants():");
     	log.info("-------------------------------");
     	restaurantService.getAllRestaurants().forEach(restaurant-> log.info(restaurant.toString()));
     	log.info("");
    }
}