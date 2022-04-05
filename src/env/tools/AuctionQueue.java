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
		System.out.println("N�chste/r Verk�ufer/in ist: " + getObsProperty("nextParticipant").stringValue());
		auctionQueue.remove(0);
		if(auctionQueue.size()>0) {
			getObsProperty("nextParticipant").updateValue(auctionQueue.get(0));
			System.out.println("Aktuelle Auction Queue f�r Verk�ufer ist: " + auctionQueue);
		}else {
			getObsProperty("nextParticipant").updateValue("Empty");
		}
	}
	
	@OPERATION
	void enter(String name) {
		if(auctionQueue.contains(name)) {
			//System.out.println(name + " wurde NICHT hinzugef�gt");
			return;
		}else {
			auctionQueue.add(name);
			getObsProperty("nextParticipant").updateValue(auctionQueue.get(0));
			//System.out.println(name + " wurde hinzugef�gt");
		}
		/*
		System.out.println("-----------------------------------------------------");
		System.out.println(auctionQueue);
		System.out.println(getObsProperty("nextParticipant").stringValue());
		System.out.println("-----------------------------------------------------");
		*/
	}
	
}
