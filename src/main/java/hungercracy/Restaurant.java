package hungercracy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.thymeleaf.util.Validate;

@Entity
public class Restaurant {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="id")
    private Long id;
    @Column(name="name")
	private String name;
	
	protected Restaurant() {}
	
	public Restaurant (final String name) {
		Validate.notNull(name, "Restaurant name cannot be null");
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(final String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return name;
	}
}
