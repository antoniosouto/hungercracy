/**
 * 
 */
package hungercracy;

import org.junit.Before;
/**
 * @author Antonio
 *
 */
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.mockito.Mockito.when;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static hungercracy.DateTimeUtil.getCurrentZonedLocalDate;
import static hungercracy.DateTimeUtil.getCurrentZonedLocalDateMinusDays;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
	
	private static final Logger log = LoggerFactory.getLogger(Application.class);

	//@Mock


	@Autowired
	UserRepository userRepository;
	
    @Autowired
    private MockMvc mockMvc;

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
    public void contextLoads() throws Exception {
    }
    
    @Test
    public void shouldReturnSuccessMessage() throws Exception {
		
		/*when(userRepository.findByName("Just Voted")).thenReturn(mockedUserJustVoted);
		when(userRepository.findByName("Voted Yesterday")).thenReturn(mockedUserVotedYesterday);
		when(userRepository.findByName("Never Voted")).thenReturn(mockedUserNeverVoted);
		when(userRepository.findByName("NeverVoted2")).thenReturn(mockedUserNeverVoted2);
		*///TODO: inserir usuarios de teste no banco, excutar os testes e remove-los?  Mocar dependencias?
        this.mockMvc.perform(get("/voting?username=Voted Yesterday&restaurantName=Fogo de Chao")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Voto registrado com sucesso para o restaurante Fogo de Chao")));
    }
   
    @Test
    public void shouldReturnAlredyVotedMessage() throws Exception {
		//TODO: inserir usuarios de teste no banco, excutar os testes e remove-los? Mocar dependencias?
        this.mockMvc.perform(get("/voting?username=Just Voted&restaurantName=Fogo de Chao")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Usuario Just Voted ja votou hoje")));
    }
    
    @Test
    public void shouldReturnUserNotFoundMessage() throws Exception {
        this.mockMvc.perform(get("/voting?username=NaoExiste&restaurantName=Fogo de Chao")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Usuario NaoExiste nao encontrado no banco de dados")));
    }
}

