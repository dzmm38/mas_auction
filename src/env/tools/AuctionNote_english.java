
package tools;
import cartago.*;

public class AuctionNote_english extends Artifact {
	int countdown;
	
	void init() {
		countdown = 15;
		
		defineObsProperty("winningBid",0.0f);
		defineObsProperty("winner","");
		
		//float currentHighestBid = 0;
		//String currentHighestBidder;
		//int counter;
	}

	@OPERATION void receiveBid(String bidder,float bid) {
		float currentHighestBid = getObsProperty("winningBid").floatValue();
		if(bid>currentHighestBid) {
			getObsProperty("winningBid").updateValue(bid);
			getObsProperty("winner").updateValue(bidder);
			
			//System.out.println("Gebot von "+ bidder + " ist das höchste");
		
		}else {
			//System.out.println("Gebot von "+ bidder + " nicht höher als vorhandenes");
		}
	}
	
	@OPERATION void clearNote() {
		getObsProperty("winningBid").updateValue(0.0f);
		getObsProperty("winner").updateValue("");
	}
}

