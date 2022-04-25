package tools;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import cartago.*;

public class Inventory extends Artifact {

	void init() {
		//defineObsProperty("Geld", 40);

		initInventoryByName();
		
//		defineObsProperty("Bier", random());
//		defineObsProperty("Brot", random());
//		defineObsProperty("Käse", random());
//		defineObsProperty("Wurst", random());

//		defineObsProperty("DBier", random());
//		defineObsProperty("DBrot", random());
//		defineObsProperty("DKäse", random());
//		defineObsProperty("DWurst", random());

		defineObsProperty("VBier", random10());
		defineObsProperty("VBrot", random10());
		defineObsProperty("VKäse", random10());
		defineObsProperty("VWurst", random10());

	}

	private int random() {
		return ThreadLocalRandom.current().nextInt(0, 4);
	}

	private int random10() {
		return ThreadLocalRandom.current().nextInt(0, 10);
	}
	
	//TODO: values für den Testcase definieren
	@OPERATION
	void initInventoryByName() {
		String creatorId = getCreatorId().toString();
		switch(creatorId) {
	//***********************************************
	//	SIM3
	//***********************************************	
//		case "bob":
//			defineObsProperty("Geld", 20);
//			defineObsProperties("inventory", 5, 1, 2, 1);
//			defineObsProperties("demand", 1, 4, 2, 3);
//			defineObsProperties("value", 1, 2, 3, 4);
//		case "alice":
//			defineObsProperty("Geld", 20);
//			defineObsProperties("inventory", 0, 3, 0, 1);
//			defineObsProperties("demand", 3, 2, 0, 1);
//			defineObsProperties("value", 1, 2, 3, 4);
//		case "max":
//			defineObsProperty("Geld", 20);
//			defineObsProperties("inventory", 0, 1, 1, 0);
//			defineObsProperties("demand", 2, 2, 2, 2);
//			defineObsProperties("value", 1, 2, 3, 4);
//		case "klaus":
//			defineObsProperty("Geld", 20);
//			defineObsProperties("inventory", 0, 1, 1, 2);
//			defineObsProperties("demand", 1, 2, 1, 0);
//			defineObsProperties("value", 1, 2, 3, 4);
			
	//***********************************************
	//	SIM2
	//***********************************************		
		case "bob":
			defineObsProperty("Geld", 10);
			defineObsProperties("inventory", 2, 2, 3, 2);
			defineObsProperties("demand", 2, 3, 0, 0);
			//defineObsProperties("value", 1, 2, 3, 4);
		case "alice":
			defineObsProperty("Geld", 10);
			defineObsProperties("inventory", 4, 2, 1, 1);
			defineObsProperties("demand", 2, 3, 0, 0);
			//defineObsProperties("value", 1, 2, 3, 4);
		case "max":
			defineObsProperty("Geld", 10);
			defineObsProperties("inventory", 1, 1, 1, 1);
			defineObsProperties("demand", 1, 1, 2, 3);
			//defineObsProperties("value", 1, 2, 3, 4);
		case "klaus":
			defineObsProperty("Geld", 10);
			defineObsProperties("inventory", 1, 2, 3, 1);
			defineObsProperties("demand", 0, 0, 4, 1);
			//defineObsProperties("value", 1, 2, 3, 4);
			
		//***********************************************
		//	SIM1
		//***********************************************	
//		case "bob":
//			defineObsProperty("Geld", 10);
//			defineObsProperties("inventory", 2, 2, 0, 2);
//			defineObsProperties("demand", 3, 2, 3, 1);
//			defineObsProperties("value", 1, 2, 3, 4);
//		case "alice":
//			defineObsProperty("Geld", 10);
//			defineObsProperties("inventory", 4, 0, 1, 2);
//			defineObsProperties("demand", 2, 0, 0, 2);
//			defineObsProperties("value", 1, 2, 3, 4);
//		case "max":
//			defineObsProperty("Geld", 10);
//			defineObsProperties("inventory", 0, 1, 1, 2);
//			defineObsProperties("demand", 2, 2, 0, 0);
//			defineObsProperties("value", 1, 2, 3, 4);
//		case "klaus":
//			defineObsProperty("Geld", 10);
//			defineObsProperties("inventory", 2, 1, 2, 1);
//			defineObsProperties("demand", 1, 0, 1, 4);
//			defineObsProperties("value", 1, 2, 3, 4);
		}
	}
	
	void defineObsProperties(String mode, int bier, int brot, int käse, int wurst) {
		switch(mode) {
			case "inventory":
				defineObsProperty("Bier", bier);
				defineObsProperty("Brot", brot);
				defineObsProperty("Käse", käse);
				defineObsProperty("Wurst", wurst);
			case "demand":
				defineObsProperty("DBier", bier);
				defineObsProperty("DBrot", brot);
				defineObsProperty("DKäse", käse);
				defineObsProperty("DWurst", wurst);
			case "value":
				defineObsProperty("VBier", bier);
				defineObsProperty("VBrot", brot);
				defineObsProperty("VKäse", käse);
				defineObsProperty("VWurst", wurst);
		}
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
	void getMoney(OpFeedbackParam<Integer> result) {
		ObsProperty p = getObsProperty("Geld");
		result.set(p.intValue());
	}

	@OPERATION
	void bidMoney(String item, String type, OpFeedbackParam<Integer> result) {
		ObsProperty myItems = getObsProperty(item);
		ObsProperty demandItem = getObsProperty("D" + item);
		ObsProperty myMoney = getObsProperty("Geld");
		int demand = demandItem.intValue() - myItems.intValue();
		if (demand > 0) {
			int value = getObsProperty("V"+item).intValue();
			if(myMoney.intValue() >= value) {
				result.set(value);
			} else {
				result.set(myMoney.intValue());
			}
		} else {
			result.set(0);
		}
	}

	@OPERATION
	void nextItemToSell(OpFeedbackParam<String> result) {
		List<String> items = new ArrayList<String>();
		if (checkItem("Bier")) {
			items.add("Bier");
		}
		if (checkItem("Brot")) {
			items.add("Brot");
		}
		if (checkItem("Käse")) {
			items.add("Käse");
		}
		if (checkItem("Wurst")) {
			items.add("Wurst");
		}
		if (items.size() == 0) {
			result.set("nix");
		} else {
			int randomInt = ThreadLocalRandom.current().nextInt(0, items.size());
			result.set(items.get(randomInt));
		}
	}

	@OPERATION
	void doWeHaveItemsToSell(OpFeedbackParam<Boolean> result) {
		if (checkItem("Bier")) {
			result.set(true);
			return;
		} else if (checkItem("Brot")) {
			result.set(true);
			return;
		} else if (checkItem("Käse")) {
			result.set(true);
			return;
		} else if (checkItem("Wurst")) {
			result.set(true);
			return;
		} else {
			result.set(false);
		}
	}

	private boolean checkItem(String item) {
		ObsProperty prop = getObsProperty(item);
		int cnt = prop.intValue();
		ObsProperty dprop = getObsProperty("D" + item);
		int dcnt = dprop.intValue();

		if (cnt > dcnt)
			return true;
		return false;
	}

}
