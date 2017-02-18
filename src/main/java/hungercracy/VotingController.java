package hungercracy;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class VotingController {
	
	UsersRepository usersRep = new UsersRepository();

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
				User user = usersRep.getByName(username);
				user.vote(restaurantName);
				model.addAttribute("voteResultOk", "true");
				model.addAttribute("voteResultMessage", "Voto registrado com sucesso para");
				model.addAttribute("restaurantName", " o restaurante " + restaurantName);
		    } catch (Exception e) {
		    	model.addAttribute("voteResultOk", "false");
		    	model.addAttribute("voteResultMessage", "Error: " + e.getMessage());
				model.addAttribute("restaurantName", "");
			}
		}
		
		Restaurants restaurants = new Restaurants();
		
		model.addAttribute("restaurants", restaurants.getAll());
		return "voting";
	}

}
