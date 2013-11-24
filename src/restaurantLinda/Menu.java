package restaurantLinda;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Menu {
	private Map<String, Integer> foodList = new HashMap<String,Integer>();
	
	public Menu(){
		foodList.put("Steak", 1599);
		foodList.put("Chicken", 1099);
		foodList.put("Salad", 599);
		foodList.put("Pizza", 899);
	}
	
	public Menu(List<String> exclusionList){
		if (!exclusionList.contains("Steak"))
			foodList.put("Steak", 1599);
		if (!exclusionList.contains("Chicken"))
			foodList.put("Chicken", 1099);
		if (!exclusionList.contains("Salad"))
			foodList.put("Salad", 599);
		if (!exclusionList.contains("Pizza"))
			foodList.put("Pizza", 899);
	}
	
	public Map<String,Integer> getMenu(){
		return foodList;
	}
	
	public String toString(){
		return foodList.toString();
	}
}
