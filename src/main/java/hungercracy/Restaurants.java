/**
 * 
 */
package hungercracy;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Antonio
 *
 */
public class Restaurants {
	List<Restaurant> restaurants;
	
	Restaurants () {
		restaurants = new ArrayList<Restaurant>();
	}
	
	public ArrayList<Restaurant> getAll() {
		//TODO: fetch data from database
		restaurants.add(new Restaurant("Sabor Familia"));
		restaurants.add(new Restaurant("Churrascaria Sangue na Roupa"));
		restaurants.add(new Restaurant("Rodizio de Salada"));
		restaurants.sort((a,b) -> a.getName().compareToIgnoreCase(b.getName()));
		return (ArrayList<Restaurant>) restaurants;
	}
}
