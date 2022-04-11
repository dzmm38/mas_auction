package tools;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import cartago.*;

public class Inventory extends Artifact {

	void init() {
		defineObsProperty("Geld", 40);

		defineObsProperty("Bier", random());
		defineObsProperty("Brot", random());
		defineObsProperty("Käse", random());
		defineObsProperty("Wurst", random());

		defineObsProperty("DBier", random());
		defineObsProperty("DBrot", random());
		defineObsProperty("DKäse", random());
		defineObsProperty("DWurst", random());

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
		ObsProperty vorhandeneItems = getObsProperty(item);
		ObsProperty beduerfnis = getObsProperty("D" + item);
		// ObsProperty geld = getObsProperty("Geld");
		int diff = beduerfnis.intValue() - vorhandeneItems.intValue();
		if (diff > 0) {
			result.set(10);
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
			result.set(null);
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
