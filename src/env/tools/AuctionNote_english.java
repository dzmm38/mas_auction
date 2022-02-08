
package tools;
import cartago.*;

public class AuctionNote_english extends Artifact {
	int countdown;
	
	void init() {
		countdown = 15;
		
		defineObsProperty("highestBid",0.0f);
		defineObsProperty("highestBidder","");
		
		//float currentHighestBid = 0;
		//String currentHighestBidder;
		//int counter;
	}

	@OPERATION void receiveBid(String bidder,float bid) {
		float currentHighestBid = getObsProperty("highestBid").floatValue();
		if(bid>currentHighestBid) {
			getObsProperty("highestBid").updateValue(bid);
			getObsProperty("highestBidder").updateValue(bidder);
			
			System.out.println("Gebot von "+ bidder + " ist das höchste");
		
		}else {
			System.out.println("Gebot von "+ bidder + " nicht höher als vorhandenes");
		}
	}
	
	@OPERATION void clearNote() {
		getObsProperty("highestBid").updateValue(0.0f);
		getObsProperty("highestBidder").updateValue("");
	}
}

