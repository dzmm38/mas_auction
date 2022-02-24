package tools;
import java.util.ArrayList;

import cartago.*;

public class AuctionQueue extends Artifact{
	
	ArrayList<String> auctionQueue;
	
	void init() {
		defineObsProperty("nextParticipant","");
	}
	
	@OPERATION
	void getNext() {
		auctionQueue.remove(0);
		if(auctionQueue.size()>0) {
			getObsProperty("nextParticipant").updateValue(auctionQueue.get(0));
		}else {
			getObsProperty("nextParticipant").updateValue("");
		}
	}
	
	@OPERATION
	void enter(String name) {
		if(auctionQueue.contains(name)) {
			return;
		}else {
			auctionQueue.add(name);
			getObsProperty("nextParticipant").updateValue(auctionQueue.get(0));
		}
	}
	
}
