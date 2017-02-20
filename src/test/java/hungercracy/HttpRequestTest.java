/**
 * 
 */
package hungercracy;

import static hungercracy.DateTimeUtil.getCurrentZonedLocalDate;
import static hungercracy.DateTimeUtil.getCurrentZonedLocalDateMinusDays;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class HttpRequestTest {
	
	private static final Logger log = LoggerFactory.getLogger(Application.class);


    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;
    
    @Autowired
	UserRepository userRepository;
    
    @Before
    public void setup() {
    	User mockedUserJustVoted = new User("Just Voted", getCurrentZonedLocalDate());
		User mockedUserVotedYesterday = new User("Voted Yesterday", getCurrentZonedLocalDateMinusDays(1));
		User mockedUserNeverVoted = new User("Never Voted");
		userRepository.deleteAll();
		userRepository.save(mockedUserJustVoted);
		userRepository.save(mockedUserVotedYesterday);
		userRepository.save(mockedUserNeverVoted);
		// fetch all customers
		log.info("Customers found with findAll():");
		log.info("-------------------------------");
		for (User user : userRepository.findAll()) {
			log.info(user.toString());
		}
		log.info("");
    }

    @Test
    public void greetingShouldReturnDefaultMessage() throws Exception {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/voting",
                String.class)).contains("Insira o seu nome de usuario:");
    }
    
    @Test
    public void userVotedYesterdayShouldReturnSuccessMessage() throws Exception {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/voting?username=Voted Yesterday&restaurantName=Fogo de Chao",
                String.class)).contains("Voto registrado com sucesso para o restaurante Fogo de Chao");
    }
}

