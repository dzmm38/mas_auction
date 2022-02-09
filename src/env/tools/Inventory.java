package tools;

import java.util.concurrent.ThreadLocalRandom;

import cartago.*;

public class Inventory extends Artifact {
		
	
	void init() {
		defineObsProperty("Bier",random());
		defineObsProperty("Brot",random());
		defineObsProperty("Käse",random());
		defineObsProperty("Wurst",random());

	}
	
	private int random() {
		return ThreadLocalRandom.current().nextInt(0, 6);
	}
	
	@OPERATION
	void storeItem(String item) {
		ObsProperty property = getObsProperty(item);
		property.updateValue(property.intValue() + 1);
	}
	
	@OPERATION
	void retrieveItem(String item) {		
		ObsProperty property = getObsProperty(item);
		property.updateValue(property.intValue() - 1);						
	}
	
	@OPERATION
	void cntItem(String item, OpFeedbackParam<Integer> result) {
		ObsProperty p = getObsProperty(item);
		result.set(p.intValue());	
	}
}


