package hungercracy;

import static hungercracy.DateTimeUtil.getCurrentZonedLocalDate;
import static hungercracy.DateTimeUtil.getCurrentZonedLocalDateMinusDays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {
	
	private static final Logger log = LoggerFactory.getLogger(Application.class);

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
		log.info("Application started");
	}

	@Bean
	public CommandLineRunner demo(UserRepository userRepository) {
		return (args) -> {
			User userJustVoted = new User("Antonio", getCurrentZonedLocalDate());
			User userVotedYesterday = new User("Marcia", getCurrentZonedLocalDateMinusDays(1));
			User userNeverVoted = new User("Vinicius");
			userRepository.deleteAll();
			userRepository.save(userJustVoted);
			userRepository.save(userVotedYesterday);
			userRepository.save(userNeverVoted);
			// fetch all customers
			log.info("Customers found with findAll():");
			log.info("-------------------------------");
			for (User user : userRepository.findAll()) {
				log.info(user.toString());
			}
			log.info("");
		};
	}
}
