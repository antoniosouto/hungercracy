package hungercracy;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository UserRepository;
	
	
	@Override
	public List<User> getAllUsers() {
		return UserRepository.findAll();
	}

	@Override
	public User getUserByName(String name) {
		return UserRepository.findByName(name);
	}

	@Override
	public User getUserById(Long id) {
		return UserRepository.findOne(id);
	}	

}
