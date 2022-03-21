package tools;
import java.util.ArrayList;

import cartago.*;

public class AuctionQueue extends Artifact{
	
	ArrayList<String> auctionQueue;
	
	void init() {
		auctionQueue = new ArrayList<String>();
		defineObsProperty("nextParticipant","Empty");
	}
	
	@OPERATION
	void getNext() {
		auctionQueue.remove(0);
		if(auctionQueue.size()>0) {
			getObsProperty("nextParticipant").updateValue(auctionQueue.get(0));
		}else {
			getObsProperty("nextParticipant").updateValue("Empty");
		}
	}
	
	@OPERATION
	void enter(String name) {
		if(auctionQueue.contains(name)) {
			//System.out.println(name + " wurde NICHT hinzugefügt");
			return;
		}else {
			auctionQueue.add(name);
			getObsProperty("nextParticipant").updateValue(auctionQueue.get(0));
			//System.out.println(name + " wurde hinzugefügt");
		}
		/*
		System.out.println("-----------------------------------------------------");
		System.out.println(auctionQueue);
		System.out.println(getObsProperty("nextParticipant").stringValue());
		System.out.println("-----------------------------------------------------");
		*/
	}
	
}
