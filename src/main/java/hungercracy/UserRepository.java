package hungercracy;

import java.util.List;

import org.springframework.data.repository.CrudRepository;


public interface UserRepository  extends CrudRepository<User, Long>{
	
	/*public User getByName(String name) {
		//TODO: fetch from database
		if (name.equals("Antonio")) { // Antonio voted today
			return new User(name, DateTimeUtil.getCurrentZonedLocalDate());
		} else if (name.equals("Marcia")) { // Marcia voted yesterday
			return new User(name, DateTimeUtil.getCurrentZonedLocalDateMinusDays(1));		
		} else {
			throw new RuntimeException("Usuario nao encontrado no banco de dados!");
		}
	}*/
	
	/*public void updateUser(User user) throws RuntimeException {
		//TODO: persist
	}*/
	
	User findByName(String name);
	User findById(Long id);
	List<User> findAll();
	
}