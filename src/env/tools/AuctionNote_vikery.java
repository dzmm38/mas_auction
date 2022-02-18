
package tools;
import cartago.*;

public class AuctionNote_vikery extends Artifact {
	
	float highestBid;
	String highestBidder;
	
	void init() {
		highestBid = 0;
		highestBidder = "";
		
		defineObsProperty("winningBid",0.0f);
		defineObsProperty("winner","");
		
		//float currentHighestBid = 0;
		//String currentHighestBidder;
		//int counter;
	}

	@OPERATION void receiveBid(String bidder,float bid) {
		float secondhighestBid = getObsProperty("winningBid").floatValue();
		
		if(highestBid == 0) {
			highestBid = bid;
			highestBidder = bidder;
			return;
		}
		
		if(bid>highestBid) {
			getObsProperty("winningBid").updateValue(highestBid);
			getObsProperty("winner").updateValue(highestBidder);
			
			highestBid = bid;
			highestBidder = bidder;
			
			//System.out.println("Gebot von "+ bidder + " ist das höchste");
		
		}else if(bid>secondhighestBid) {
			getObsProperty("winningBid").updateValue(bid);
			getObsProperty("winner").updateValue(bidder);
			
		}else {
			//System.out.println("Gebot von "+ bidder + " nicht höher als vorhandenes");
		}
	}
	
	@OPERATION void clearNote() {
		highestBid = 0;
		highestBidder = "";
		getObsProperty("winningBid").updateValue(0.0f);
		getObsProperty("Winner").updateValue("");
		
	}
}

