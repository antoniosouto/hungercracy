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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class HttpRequestTest {
	
	@LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;
    
    @Autowired
	UserRepository userRepository;
    
    @Autowired
    RestaurantRepository restaurantRepository;
    
    @Before
    public void setup() {
    	User mockedUserJustVoted = new User("Just Voted", getCurrentZonedLocalDate());
		User mockedUserVotedYesterday = new User("Voted Yesterday", getCurrentZonedLocalDateMinusDays(1));
		User mockedUserNeverVoted = new User("Never Voted");
		userRepository.deleteAll();
		userRepository.save(mockedUserJustVoted);
		userRepository.save(mockedUserVotedYesterday);
		userRepository.save(mockedUserNeverVoted);
		restaurantRepository.deleteAll();
		Restaurant mockedRestaurant1 = new Restaurant("Fogo de Chao");
		restaurantRepository.save(mockedRestaurant1);
    }
    
    @Test
    public void userVotedYesterdayShouldReturnSuccessMessage() throws Exception {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/voting?username=Voted Yesterday&restaurantName=Fogo de Chao",
                String.class)).contains("Voto registrado com sucesso para o restaurante Fogo de Chao");
    }
}

