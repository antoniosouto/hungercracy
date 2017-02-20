package hungercracy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class VotingController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	private RestaurantService restaurantService;

	@RequestMapping("/voting")
	public String voting(@RequestParam(value="username", required=false, defaultValue="none") String username,
			@RequestParam(value="restaurantName", required=false, defaultValue="none") String restaurantName,
			Model model) {
		if (username.equalsIgnoreCase("none")) {
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
					user.vote(restaurantName);
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
		
		model.addAttribute("restaurants", restaurantService.getAllRestaurantsNotYetChoosenThisWeek());
		return "voting";
	}

}
