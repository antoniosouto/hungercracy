/**
 * 
 */
package hungercracy;

import static hungercracy.DateTimeUtil.getCurrentZonedLocalDate;
import static hungercracy.DateTimeUtil.getCurrentZonedLocalDateMinusDays;
import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;

import org.junit.Assert;
import org.junit.Before;
/**
 * @author Antonio
 *
 */
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ApplicationTest {
	
	@Autowired
	private VotingController vc;
	
	private User mockedUserJustVoted;
	private User mockedUserVotedYesterday;
	private User mockedUserNeverVoted;
	private Restaurant mockedRestaurant1;
	
	//@Mock


	@Autowired
	UserRepository userRepository;
	@Autowired
	RestaurantRepository restaurantRepository;
	
	
    @Autowired
    private MockMvc mockMvc;

    @Before
    public void setup() {
    	mockedUserJustVoted = new User("Just Voted", getCurrentZonedLocalDate());
		mockedUserVotedYesterday = new User("Voted Yesterday", getCurrentZonedLocalDateMinusDays(1));
		mockedUserNeverVoted = new User("Never Voted");
		userRepository.deleteAll();
		userRepository.save(mockedUserJustVoted);
		userRepository.save(mockedUserVotedYesterday);
		userRepository.save(mockedUserNeverVoted);
		restaurantRepository.deleteAll();
		mockedRestaurant1 = new Restaurant("Fogo de Chao");
		restaurantRepository.save(mockedRestaurant1);
	}
    
    @Test
    public void contextLoads() throws Exception {
    }
    
    @Test
    public void shouldReturnSuccessMessage() throws Exception {
		this.mockMvc.perform(get("/voting?username=Voted Yesterday&restaurantName=Fogo de Chao")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Voto registrado com sucesso para o restaurante Fogo de Chao")));
    }
   
    @Test
    public void shouldReturnAlredyVotedMessage() throws Exception {
        this.mockMvc.perform(get("/voting?username=Just Voted&restaurantName=Fogo de Chao")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Usuario Just Voted ja votou hoje")));
    }
    
    @Test
    public void shouldReturnUserNotFoundMessage() throws Exception {
        this.mockMvc.perform(get("/voting?username=NaoExiste&restaurantName=Fogo de Chao")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Usuario NaoExiste nao encontrado no banco de dados")));
    }
    
	@Test
	public void voteTest() throws IOException {
		try {
			try {
				assertTrue(mockedUserJustVoted.getLastVotingDate().isEqual(getCurrentZonedLocalDate()));
			} catch (AssertionError ae) {
				Assert.fail();
			}
			vc.vote(mockedUserJustVoted, mockedRestaurant1);
			Assert.fail();
		} catch (Exception e) {
			//Expected
			try {
				assertTrue(mockedUserJustVoted.getLastVotingDate().isEqual(getCurrentZonedLocalDate()));
			} catch (AssertionError ae) {
				Assert.fail();
			}
		}
		
		try {
			assertTrue(mockedUserVotedYesterday.getLastVotingDate().isEqual(getCurrentZonedLocalDateMinusDays(1)));
			vc.vote(mockedUserVotedYesterday, mockedRestaurant1);
			assertTrue(mockedUserVotedYesterday.getLastVotingDate().isEqual(getCurrentZonedLocalDate()));
		} catch (Exception e) {
			Assert.fail();
		}
		
		try {
			assertTrue(mockedUserNeverVoted.getLastVotingDate().isEqual(LocalDate.of(2000, Month.DECEMBER, 25)));
			vc.vote(mockedUserNeverVoted, mockedRestaurant1);
			assertTrue(mockedUserNeverVoted.getLastVotingDate().isEqual(getCurrentZonedLocalDate()));
		} catch (Exception e) {
			Assert.fail();
		}
	}
}

