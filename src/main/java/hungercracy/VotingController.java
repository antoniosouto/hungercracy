package hungercracy;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class VotingController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RestaurantService restaurantService;
	
	@Autowired
	private RestaurantRepository restaurantRepository;
	
	@Autowired
	private ScheduledTasks scheduledTasks;

	@RequestMapping("/voting")
	public String voting(@RequestParam(value="username", required=false, defaultValue="none") String username,
			@RequestParam(value="restaurantName", required=false, defaultValue="none") String restaurantName,
			@RequestParam(value="test", required=false, defaultValue="none") String test,
			Model model) {
		if (test.equalsIgnoreCase("closeVoting")) {
			scheduledTasks.closeVoting();
			model.addAttribute("isVotingClosed", true);
		} else if  (test.equalsIgnoreCase("openVoting")) {
			scheduledTasks.openVoting();
			model.addAttribute("isVotingClose", false);
		} else if (username.equalsIgnoreCase("none")) {
			//TODO: create a vote object to handle vote result info and add it to the model
			model.addAttribute("voteResultOk", "");
			model.addAttribute("voteResult", "none");
			model.addAttribute("restaurantName", "");			
		} else {
			try {
				User user = userService.getUserByName(username);
				if (null == user) {
					model.addAttribute("voteResultOk", "false");
					model.addAttribute("voteResultMessage", "Usuario " + username + " nao encontrado no banco de dados");
					model.addAttribute("restaurantName", "");
				} else {
					vote(userService.getUserByName(username), restaurantService.getRestaurantByName(restaurantName));
					model.addAttribute("voteResultOk", "true");
					model.addAttribute("voteResultMessage", "Voto registrado com sucesso para");
					model.addAttribute("restaurantName", " o restaurante " + restaurantName);
				}
		    } catch (Exception e) {
		    	model.addAttribute("voteResultOk", "false");
		    	model.addAttribute("voteResultMessage", "Error: " + e.getMessage());
				model.addAttribute("restaurantName", "");
			}
		}
		
		model.addAttribute("users", userService.getAllUsers());
		model.addAttribute("restaurants", restaurantService.getAllRestaurantsNotYetChoosenThisWeekSortedByNameAndByVotes());
		if (!model.containsAttribute("isVotingClosed"))
			model.addAttribute("isVotingClosed", DateTimeUtil.isPastLocalTime(11,30));
		return "voting";
	}
	
	public void vote(User user, Restaurant restaurant) {
			final LocalDate currentVotingDate = DateTimeUtil.getCurrentZonedLocalDate();
			if (!currentVotingDate.isEqual(user.getLastVotingDate())) {
				user.setLastVotingDate(currentVotingDate);
				restaurant.vote();
				restaurantRepository.save(restaurant);
				userRepository.save(user);
			} else {
				throw new RuntimeException("Usuario " + user.getName() + " ja votou hoje!");
			}
	}
}
