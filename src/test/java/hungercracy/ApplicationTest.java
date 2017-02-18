/**
 * 
 */
package hungercracy;

/**
 * @author Antonio
 *
 */
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
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

	@Mock
	private UsersRepository usersRepository;
	
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void contextLoads() throws Exception {
    }
    
    @Test
    public void shouldReturnSuccessMessage() throws Exception {
		User mockedUserJustVoted = new User("Just Voted", getCurrentZonedLocalDate());
		User mockedUserVotedYesterday = new User("Voted Yesterday", getCurrentZonedLocalDateMinusDays(1));
		User mockedUserNeverVoted = new User("Never Voted");
		User mockedUserNeverVoted2 = new User("Never Voted 2", null);
		when(usersRepository.getByName("Just Voted")).thenReturn(mockedUserJustVoted);
		when(usersRepository.getByName("Voted Yesterday")).thenReturn(mockedUserVotedYesterday);
		when(usersRepository.getByName("Never Voted")).thenReturn(mockedUserNeverVoted);
		when(usersRepository.getByName("NeverVoted2")).thenReturn(mockedUserNeverVoted2);
		//TODO: inserir usuarios de teste no banco, excutar os testes e remove-los?  Mocar dependencias?
        this.mockMvc.perform(get("/voting?username=Marcia&restaurantName=Fogo de Chao")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Voto registrado com sucesso para o restaurante Fogo de Chao")));
    }
   
    @Test
    public void shouldReturnAlredyVotedMessage() throws Exception {
		//TODO: inserir usuarios de teste no banco, excutar os testes e remove-los? Mocar dependencias?
        this.mockMvc.perform(get("/voting?username=Antonio&restaurantName=Fogo de Chao")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Usario Antonio ja votou hoje")));
    }
    
    @Test
    public void shouldReturnUserNotFoundMessage() throws Exception {
        this.mockMvc.perform(get("/voting?username=NaoExiste&restaurantName=Fogo de Chao")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Usuario nao encontrado no banco de dados")));
    }
}

