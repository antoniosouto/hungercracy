package hungercracy;

import java.sql.Date;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.thymeleaf.util.Validate;

@Entity
public class User {
	
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
	private String name;
	private Date lastVotingDate = Date.valueOf("2000-12-25");
	
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
	
	@Override
	public String toString() {
		return "Name: " + name + "; Last Voting Date:" + lastVotingDate.toString() + ".";
	}
	
}