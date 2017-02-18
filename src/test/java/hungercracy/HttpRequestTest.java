/**
 * 
 */
package hungercracy;

import static hungercracy.DateTimeUtil.getCurrentZonedLocalDate;
import static hungercracy.DateTimeUtil.getCurrentZonedLocalDateMinusDays;
import static org.assertj.core.api.Assertions.assertThat;

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

    @Test
    public void greetingShouldReturnDefaultMessage() throws Exception {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/voting",
                String.class)).contains("Insira o seu nome de usuario:");
    }
    
    @Test
    public void userVotedYesterdayShouldReturnSuccessMessage() throws Exception {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/voting?username=Marcia&restaurantName=Fogo de Chao",
                String.class)).contains("Voto registrado com sucesso para o restaurante Fogo de Chao");
    }
}

