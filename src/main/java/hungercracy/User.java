package hungercracy;

import java.time.LocalDate;


public class User {
	
	private LocalDate lastVotingDate/* = LocalDate.of(2000, Month.DECEMBER, 25)*/;
	private String name;
	
	public User (final String name) {
		this.name = name;
	}
	
	public User (final String name, final LocalDate lastVotingDate) {
		this.name = name;
		this.lastVotingDate = lastVotingDate;
	}
	
	String getName() {
		return name;
	}
	
	void setName (final String name) {
		this.name = name;
	}
	
	public LocalDate getLastVotingDate() {
		return lastVotingDate;
	}
	
	void setLastVotingDate(LocalDate lastVotingDate) {
		this.lastVotingDate = lastVotingDate;
	}
	
	public void vote (final String restaurantName) throws RuntimeException {
		final LocalDate currentVotingDate = DateTimeUtil.getCurrentZonedLocalDate();
		if (null == lastVotingDate || !currentVotingDate.isEqual(lastVotingDate)) {
			lastVotingDate = currentVotingDate;
			new UsersRepository().updateUser(this);
			// TODO: persist the vote on the restaurant
		} else {
			throw new RuntimeException("Usario " + name + " ja votou hoje!");
		}
	}
	
	private void persist() throws RuntimeException {
		// TODO: persist User Object into DB
	}
	
}