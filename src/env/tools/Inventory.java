package tools;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import cartago.*;

public class Inventory extends Artifact {

	void init() {
		initInventoryByName();
		
//		defineObsProperty("Geld", 10);
//
//		defineObsProperty("Bier", random());
//		defineObsProperty("Brot", random());
//		defineObsProperty("K�se", random());
//		defineObsProperty("Wurst", random());
//
//		defineObsProperty("DBier", random());
//		defineObsProperty("DBrot", random());
//		defineObsProperty("DK�se", random());
//		defineObsProperty("DWurst", random());
//
//		defineObsProperty("VBier", random10());
//		defineObsProperty("VBrot", random10());
//		defineObsProperty("VK�se", random10());
//		defineObsProperty("VWurst", random10());

	}

	private int random() {
		return ThreadLocalRandom.current().nextInt(0, 4);
	}

	private int random10() {
		return ThreadLocalRandom.current().nextInt(0, 10);
	}

	// TODO: values f�r den Testcase definieren
	@OPERATION
	void initInventoryByName() {
		String creatorId = getCreatorId().toString();
		switch (creatorId) {
		// ***********************************************
		// DETERMINISTIC CASE
		// ***********************************************
//		case "klaus":
//			defineObsProperty("Geld", 1);
//			defineObsProperties("inventory", 1, 0, 0, 0);
//			defineObsProperties("demand", 0, 1, 0, 0);
//			defineObsProperties("value", 0, 1, 0, 0);
//			break;
//		case "bob":
//			defineObsProperty("Geld", 1);
//			defineObsProperties("inventory", 0, 1, 0, 0);
//			defineObsProperties("demand", 1, 0, 0, 0);
//			defineObsProperties("value", 1, 0, 0, 0);
//			break;
//		case "max":
//			defineObsProperty("Geld", 1);
//			defineObsProperties("inventory", 0, 0, 1, 0);
//			defineObsProperties("demand", 0, 0, 0, 1);
//			defineObsProperties("value", 0, 0, 0, 1);
//			break;
//		case "alice":
//			defineObsProperty("Geld", 1);
//			defineObsProperties("inventory", 0, 0, 0, 1);
//			defineObsProperties("demand", 0, 0, 1, 0);
//			defineObsProperties("value", 0, 0, 1, 0);
//			break;

			// ***********************************************
			// NOTDETERMINISTIC CASE
			// ***********************************************
//		case "klaus":
//			defineObsProperty("Geld", 1);
//			defineObsProperties("inventory", 1, 0, 0, 0);
//			defineObsProperties("demand", 0, 1, 0, 1);
//			defineObsProperties("value", 0, 1, 0, 1);
//			break;
//		case "bob":
//			defineObsProperty("Geld", 1);
//			defineObsProperties("inventory", 0, 1, 0, 0);
//			defineObsProperties("demand", 1, 0, 1, 0);
//			defineObsProperties("value", 1, 0, 1, 0);
//			break;
//		case "max":
//			defineObsProperty("Geld", 1);
//			defineObsProperties("inventory", 0, 0, 1, 0);
//			defineObsProperties("demand", 1, 0, 0, 1);
//			defineObsProperties("value", 1, 0, 0, 1);
//			break;
//		case "alice":
//			defineObsProperty("Geld", 1);
//			defineObsProperties("inventory", 0, 0, 0, 1);
//			defineObsProperties("demand", 0, 1, 1, 0);
//			defineObsProperties("value", 0, 1, 1, 0);
//			break;
		
			// ***********************************************
			// Oversupply
			// ***********************************************
//		case "klaus":
//			defineObsProperty("Geld", 10);
//			defineObsProperties("inventory", 2, 0, 3, 2);
//			defineObsProperties("demand", 2, 1, 0, 0);
//			defineObsProperties("value", 2, 4, 0, 0);
//			break;
//		case "bob":
//			defineObsProperty("Geld", 10);
//			defineObsProperties("inventory", 3, 0, 1, 1);
//			defineObsProperties("demand", 1, 1, 0, 0);
//			defineObsProperties("value", 6, 2, 0, 0);
//			break;
//		case "max":
//			defineObsProperty("Geld", 10);
//			defineObsProperties("inventory", 1, 1, 0, 0);
//			defineObsProperties("demand", 1, 1, 1, 2);
//			defineObsProperties("value", 5, 2, 1, 4);
//			break;
//		case "alice":
//			defineObsProperty("Geld", 10);
//			defineObsProperties("inventory", 1, 2, 0, 1);
//			defineObsProperties("demand", 0, 0, 1, 1);
//			defineObsProperties("value", 0, 0, 2, 1);
//			break;
			
			
			// ***********************************************
			// Undersupply
			// ***********************************************
//		case "klaus":
//			defineObsProperty("Geld", 10);
//			defineObsProperties("inventory", 5, 1, 0, 1);
//			defineObsProperties("demand", 1, 4, 0, 3);
//			defineObsProperties("value", 2, 4, 0, 2);
//			break;
//		case "bob":
//			defineObsProperty("Geld", 10);
//			defineObsProperties("inventory", 0, 2, 0, 1);
//			defineObsProperties("demand", 3, 1, 0, 1);
//			defineObsProperties("value", 6, 2, 0, 1);
//			break;
//		case "max":
//			defineObsProperty("Geld", 10);
//			defineObsProperties("inventory", 0, 1, 1, 0);
//			defineObsProperties("demand", 2, 2, 2, 2);
//			defineObsProperties("value", 5, 1, 1, 4);
//			break;
//		case "alice":
//			defineObsProperty("Geld", 10);
//			defineObsProperties("inventory", 0, 1, 1, 2);
//			defineObsProperties("demand", 1, 2, 1, 0);
//			defineObsProperties("value", 1, 3, 2, 0);
//			break;
		
		// ***********************************************
		// Exact Supply
		// ***********************************************
		case "klaus":
		defineObsProperty("Geld", 20);
		defineObsProperties("inventory", 0, 0, 6, 0);
		defineObsProperties("demand", 0, 1, 1, 2);
		defineObsProperties("value", 0, 2, 3, 4);
		break;
	case "bob":
		defineObsProperty("Geld", 20);
		defineObsProperties("inventory", 3, 3, 0, 2);
		defineObsProperties("demand", 0, 1, 3, 0);
		defineObsProperties("value", 1, 2, 5, 3);
		break;
	case "max":
		defineObsProperty("Geld", 20);
		defineObsProperties("inventory", 0, 0, 0, 1);
		defineObsProperties("demand", 2, 0, 0, 4);
		defineObsProperties("value", 3, 0, 0, 5);
		break;
	case "alice":
		defineObsProperty("Geld", 20);
		defineObsProperties("inventory", 0, 1, 0, 4);
		defineObsProperties("demand", 1, 2, 2, 1);
		defineObsProperties("value", 4, 6, 5, 2);
		break;
		
		}
	}

	void defineObsProperties(String mode, int brot, int k�se, int bier, int wurst) {
		switch (mode) {
		case "inventory":
			defineObsProperty("Brot", brot);
			defineObsProperty("K�se", k�se);
			defineObsProperty("Bier", bier);
			defineObsProperty("Wurst", wurst);
			break;
		case "demand":
			defineObsProperty("DBrot", brot);
			defineObsProperty("DK�se", k�se);
			defineObsProperty("DBier", bier);
			defineObsProperty("DWurst", wurst);
			break;
		case "value":
			defineObsProperty("VBrot", brot);
			defineObsProperty("VK�se", k�se);
			defineObsProperty("VBier", bier);
			defineObsProperty("VWurst", wurst);
			break;
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

			if (type.equals("English")) {
				result.set(calculateStartingBid(item));
				return;
			} else {
				int value = getObsProperty("V" + item).intValue();
				if (myMoney.intValue() >= value) {
					result.set(value);
				} else {
					result.set(myMoney.intValue());
				}
			}
		} else {
			result.set(0);
		}
	}

	public int calculateStartingBid(String item) {
		int myMoney = getObsProperty("Geld").intValue();
		int itemValue = getObsProperty("V" + item).intValue();
		if (myMoney > itemValue / 2) {
			return itemValue / 2;
		}
		return 0;
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
		if (checkItem("K�se")) {
			items.add("K�se");
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
		} else if (checkItem("K�se")) {
			result.set(true);
			return;
		} else if (checkItem("Wurst")) {
			result.set(true);
			return;
		} else {
			result.set(false);
		}
	}

	@OPERATION
	public void calculateNextBid(int bid, String item, String agent, OpFeedbackParam<Integer> result) {

		ObsProperty myItems = getObsProperty(item);
		ObsProperty demandItem = getObsProperty("D" + item);
		ObsProperty myMoney = getObsProperty("Geld");
		int demand = demandItem.intValue() - myItems.intValue();

		if (demand > 0) {
			int itemValue = getObsProperty("V" + item).intValue();
			if ((bid < itemValue) && ((bid + 1) <= myMoney.intValue())) {
				result.set(bid + 1);
			} else {
				result.set(0);
			}
		} else {
			result.set(0);
		}
	}

	@OPERATION
	public void happiness(OpFeedbackParam<String> result) {
		StringBuilder sb = new StringBuilder();

		sb.append("Bier: " + getObsProperty("Bier").intValue() + "|");
		sb.append("Wurst: " + getObsProperty("Wurst").intValue() + "|");
		sb.append("K�se: " + getObsProperty("K�se").intValue() + "|");
		sb.append("Brot: " + getObsProperty("Brot").intValue() + "|");

		sb.append("Geld: " + getObsProperty("Geld").intValue() + "|");

		sb.append("Happiness: " + calcHappiness());

		result.set(sb.toString());
	}

	private float calcHappiness() {
		int bierHappiness = calcItemHappiness("Bier");
		int brotHappiness = calcItemHappiness("Brot");
		int wurstHappiness = calcItemHappiness("Wurst");
		int kaeseHappiness = calcItemHappiness("K�se");
		float money = getObsProperty("Geld").intValue();

		return bierHappiness + brotHappiness + wurstHappiness + kaeseHappiness + (money / 4);
	}

	private int calcItemHappiness(String item) {

		int value = getObsProperty("V" + item).intValue();
		int demand = getObsProperty("D" + item).intValue();
		int count = getObsProperty(item).intValue();

		if (demand < count) {
			return value * demand;
		} else {
			return value * count;
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
