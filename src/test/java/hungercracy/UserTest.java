package hungercracy;
import static hungercracy.DateTimeUtil.getCurrentZonedLocalDate;
import static hungercracy.DateTimeUtil.getCurrentZonedLocalDateMinusDays;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import hungercracy.User;




//@RunWith(MockitoJUnitRunner.class)
public class UserTest {
	
	/*
	@Mock
	private UsersRepository usersRepository;
	*/
	@Autowired
	private UserService userService;
	
    @Before
    public void setUp() throws Exception {
       // MockitoAnnotations.initMocks(this);
    }
	
	@Test
	public void voteTest() throws IOException {
		User mockedUserJustVoted = new User("Just Voted", getCurrentZonedLocalDate());
		User mockedUserVotedYesterday = new User("Voted Yesterday", getCurrentZonedLocalDateMinusDays(1));
		User mockedUserNeverVoted = new User("Never Voted");
		/*
		when(usersRepository.getByName("Just Voted")).thenReturn(mockedUserJustVoted);
		when(usersRepository.getByName("Voted Yesterday")).thenReturn(mockedUserVotedYestaerday);
		when(usersRepository.getByName("Never Voted")).thenReturn(mockedUserNeverVoted);
		when(usersRepository.getByName("NeverVoted2")).thenReturn(mockedUserNeverVoted2);
		*/
		
		try {
			try {
				assertTrue(mockedUserJustVoted.getLastVotingDate().isEqual(getCurrentZonedLocalDate()));
			} catch (AssertionError ae) {
				Assert.fail();
			}
			mockedUserJustVoted.vote("restaurant name");
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
			mockedUserVotedYesterday.vote("restaurant name");
			assertTrue(mockedUserVotedYesterday.getLastVotingDate().isEqual(getCurrentZonedLocalDate()));
		} catch (Exception e) {
			Assert.fail();
		}
		
		try {
			assertTrue(mockedUserNeverVoted.getLastVotingDate().isEqual(LocalDate.of(2000, Month.DECEMBER, 25)));
			mockedUserNeverVoted.vote("restaurant name");
			assertTrue(mockedUserNeverVoted.getLastVotingDate().isEqual(getCurrentZonedLocalDate()));
		} catch (Exception e) {
			Assert.fail();
		}
	}
}