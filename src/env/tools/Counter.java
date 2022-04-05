// CArtAgO artifact code for project mas_auction

package tools;

import cartago.*;

public class Counter extends Artifact {
	int targetValue;
	
	void init(int targetValue) {
		defineObsProperty("count", 0);
		defineObsProperty("isDone", false);
		this.targetValue = targetValue;
		
		//System.out.println("created counter with targetValue: " + targetValue);
	}

	@OPERATION
	void inc() {
		ObsProperty prop = getObsProperty("count");
		prop.updateValue(prop.intValue()+1);
		
		if(prop.intValue() == targetValue) {
			getObsProperty("isDone").updateValue(true);
			
			System.out.println("Es haben alle Teilneher ein gebot abgegeben.");
		}
		
		//ObsProperty prop = getObsProperty("count");
		//signal("tick"); ????
	}
}

