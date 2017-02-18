/**
 * 
 */
package hungercracy;

/**
 * @author Antonio
 *
 */
public class Restaurant {
	
	private String name;
	
	Restaurant (String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	@Override
	public String toString() {
		return getName();
	}
}
