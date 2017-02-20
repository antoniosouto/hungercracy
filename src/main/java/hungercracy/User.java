package hungercracy;

import java.sql.Date;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.thymeleaf.util.Validate;

@Entity
public class User {
	
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    //@Column(name="id")
    private Long id;
	//@Column(name="local_date")
	//@Temporal(TemporalType.DATE)
	//private LocalDate lastVotingDate/* = LocalDate.of(2000, Month.DECEMBER, 25)*/;
	private Date lastVotingDate = Date.valueOf("2000-12-25");
	//@Column(name="name")
	private String name;
	
	protected User() {}
	
	public User (final String name) {
		Validate.notNull(name, "User name cannot be null");
		this.name = name;
		lastVotingDate = Date.valueOf("2000-12-25");
	}
	
	public User (final String name, final LocalDate lastVotingDate) {
		Validate.notNull(name, "User name cannot be null");
		Validate.notNull(lastVotingDate, "User lastVotingDate cannot be null");
		this.name = name;
		this.lastVotingDate = Date.valueOf(lastVotingDate);
	}
	
	public String getName() {
		return name;
	}
	
	public void setName (final String name) {
		this.name = name;
	}
	
	public LocalDate getLastVotingDate() {
		return lastVotingDate.toLocalDate();
	}
	
	public void setLastVotingDate(LocalDate lastVotingDate) {
		Validate.notNull(lastVotingDate, "User lastVotingDate cannot be null");
		this.lastVotingDate = Date.valueOf(lastVotingDate);
	}
	
	public void vote (final String restaurantName) throws RuntimeException {
		final LocalDate currentVotingDate = DateTimeUtil.getCurrentZonedLocalDate();
		if (!currentVotingDate.isEqual(lastVotingDate.toLocalDate())) {
			setLastVotingDate(currentVotingDate);
			// TODO: persist the state change in user table
			// TODO: persist the vote on the restaurant
		} else {
			throw new RuntimeException("Usuario " + name + " ja votou hoje!");
		}
	}
	
	private void persist() throws RuntimeException {
		// TODO: persist User Object into DB
	}
	
	@Override
	public String toString() {
		return "Name: " + name + "; Last Voting Date:" + lastVotingDate.toString() + ".";
	}
	
}