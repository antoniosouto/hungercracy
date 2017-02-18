package hungercracy;

public class UsersRepository {
	
	/* Get data from DB
	 * hardcoded data instead of fetching from DB by now.
	 * */
	public User getByName(String name) throws RuntimeException {
		//TODO: fetch from database
		if (name.equals("Antonio")) { // Antonio voted today
			return new User(name, DateTimeUtil.getCurrentZonedLocalDate());
		} else if (name.equals("Marcia")) { // Marcia voted yesterday
			return new User(name, DateTimeUtil.getCurrentZonedLocalDateMinusDays(1));		
		} else {
			throw new RuntimeException("Usuario nao encontrado no banco de dados!");
		}
	}
	
	public void updateUser(User user) throws RuntimeException {
		//TODO: persist
	}
}