package tools;

import java.util.HashMap;
import java.util.Random;
import cartago.*;

public class Inventory extends Artifact {
	
	private HashMap<String, Integer> inventory;
	
	void init() {
		inventory = new HashMap<String, Integer>();
		inventory.put("BROT", 0);
		inventory.put("BIER", 0);
		inventory.put("WURST", 0);
		inventory.put("KÄSE", 0);
	}
	
	@OPERATION
	void put(String item) {
		inventory.put(item, inventory.get(item) + 1 );
	}
	
	@OPERATION
	void get(String item) {
		inventory.put(item, inventory.get(item) - 1 );
	}
}


