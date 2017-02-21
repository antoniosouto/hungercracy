package hungercracy;

import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.IsoFields;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.thymeleaf.util.Validate;

@Entity
public class Restaurant {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
	private String name;
	private Integer votes = 0;
	private boolean won = false;
	private Date lastDateWon = Date.valueOf("2000-12-25");

	
	protected Restaurant() {}
	
	public Restaurant (final String name) {
		Validate.notNull(name, "Restaurant name cannot be null");
		this.name = name;
		lastDateWon = Date.valueOf("2000-12-25");
	}
	
	public Restaurant(final String name, final LocalDate lastDateWon) {
		Validate.notNull(name, "Restaurant name cannot be null");
		Validate.notNull(lastDateWon, "Restaurant name cannot be null");
		this.name = name;
		this.lastDateWon = Date.valueOf(lastDateWon);
	}
	
	public Restaurant(final String name, final Integer votes, final boolean won, final LocalDate lastDateWon) {
		Validate.notNull(name, "Restaurant name cannot be null");
		Validate.notNull(lastDateWon, "Restaurant name cannot be null");
		Validate.notNull(votes, "Restaurant votes cannot be null");
		this.name = name;
		this.votes = votes;
		this.won = won;
		this.lastDateWon = Date.valueOf(lastDateWon);
	}

	public String getName() {
		return name;
	}
	
	public void setName(final String name) {
		this.name = name;
	}
	
	public Integer getVotes() {
		return votes;
	}
	
	public void setVotes(int votes) {
		this.votes = votes;
	}
	
	public void vote() {
		this.votes++;
	}
	
	public boolean getWon() {
		return won;
	}
	
	public boolean isWinner() {
		return won;
	}
	
	public void setWon(final boolean won) {
		this.won = won;
	}
	
	public LocalDate getLastDateWon() {
		return lastDateWon.toLocalDate();
	}
	
	public void setLastDateWon(LocalDate lastVotingDate) {
		this.lastDateWon = Date.valueOf(lastVotingDate);
	}
	
	public boolean alreadyChoosenThisWeek() {
		 LocalDate now = LocalDate.now();
		 return !(lastDateWon.toLocalDate().get(IsoFields.WEEK_BASED_YEAR) != now.get(IsoFields.WEEK_BASED_YEAR)
		      || lastDateWon.toLocalDate().get(IsoFields.WEEK_OF_WEEK_BASED_YEAR) != 
		                       now.get(IsoFields.WEEK_OF_WEEK_BASED_YEAR));
	}
	
	@Override
	public String toString() {
		return String.format("[id=%d, name=%s, votes=%d, won=%b, last time won=%s]",
				id, name, votes, won, lastDateWon.toString());
	}

}
