
package tools;
import cartago.*;

public class AuctionNote_vikery extends Artifact {
	
	float highestBid;
	String highestBidder;
	
	void init() {
		highestBid = 0;
		highestBidder = "";
		
		defineObsProperty("secondhighestBid",0.0f);
		defineObsProperty("secondhighestBidder","");
		
		//float currentHighestBid = 0;
		//String currentHighestBidder;
		//int counter;
	}

	@OPERATION void receiveBid(String bidder,float bid) {
		float secondhighestBid = getObsProperty("secondhighestBid").floatValue();
		
		if(highestBid == 0) {
			highestBid = bid;
			highestBidder = bidder;
			return;
		}
		
		if(bid>highestBid) {
			getObsProperty("secondhighestBid").updateValue(highestBid);
			getObsProperty("secondhighestBidder").updateValue(highestBidder);
			
			highestBid = bid;
			highestBidder = bidder;
			
			System.out.println("Gebot von "+ bidder + " ist das höchste");
		
		}else if(bid>secondhighestBid) {
			getObsProperty("secondhighestBid").updateValue(bid);
			getObsProperty("secondhighestBidder").updateValue(bidder);
			
		}else {
			System.out.println("Gebot von "+ bidder + " nicht höher als vorhandenes");
		}
	}
	
	@OPERATION void clearNote() {
		highestBid = 0;
		highestBidder = "";
		getObsProperty("secondhighestBid").updateValue(0.0f);
		getObsProperty("secondhighestBidder").updateValue("");
		
	}
}

