package hungercracy;

import java.util.List;

public interface UserService {
	
	public List<User> getAllUsers();
	public User getUserByName(String name);
	public User getUserById(Long id);
}
