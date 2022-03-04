package tools;

import java.util.concurrent.ThreadLocalRandom;

import cartago.*;

public class Inventory extends Artifact {
		
	
	void init() {
		defineObsProperty("Geld", 10);
		
		defineObsProperty("Bier",random());
		defineObsProperty("Brot",random());
		defineObsProperty("Käse",random());
		defineObsProperty("Wurst",random());
		
		defineObsProperty("DBier",random());
		defineObsProperty("DBrot",random());
		defineObsProperty("DKäse",random());
		defineObsProperty("DWurst",random());


	}
	
	private int random() {
		return ThreadLocalRandom.current().nextInt(0, 4);
	}
	
	@OPERATION
	void addMoney(int money) {
		ObsProperty property = getObsProperty("Geld");
		property.updateValue(property.intValue() + money);
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
	
	@OPERATION
	void nextItemToSell(OpFeedbackParam<String> result) {
		if(checkItem("Bier")) {
			result.set("Bier");
			return;
		} else if (checkItem("Brot")){
			result.set("Brot");
			return;
		} else if (checkItem("Käse")){
			result.set("Käse");
			return;
		} else if (checkItem("Wurst")){
			result.set("Wurst");
			return;
		} else {
			result.set(null);
		}
	}

	private boolean checkItem(String item) {
		ObsProperty prop = getObsProperty(item);
		int cnt = prop.intValue();
		ObsProperty dprop = getObsProperty("D"+item);
		int dcnt = dprop.intValue();
		
		if(cnt > dcnt) return true;
		return false;
	}
	
}


