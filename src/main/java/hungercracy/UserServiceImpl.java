package hungercracy;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepository;
	
	
	@Override
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	@Override
	public User getUserByName(String name) {
		return userRepository.findByName(name);
	}

	@Override
	public User getUserById(Long id) {
		return userRepository.findOne(id);
	}	
}
