
// Agent participant in project mas_auction

/* Initial beliefs and rules */

seller(false).

is_more_than(R)
	:- brot(C) & (R - C) > 0.
	
	

/* Initial goals */

!sayHello.
!setup_inventory_demands.
!enterQueue.

//!request_auction("Brot","SealedBid").

/*!sell_item.*/



/* Plans */

+!sayHello <- .send(auctioneer, tell, hi);
			  .print("HALLO").

+!setup_inventory_demands : true <-	.my_name(Me)
							makeArtifact(Me, "tools.Inventory", [], ID).

							
 																				
+nextSeller(true): true <- 	nextItemToSell(Item)
							!request_auction(Item,"SealedBid").

//WIP
/*
+!sell_item <- 	!find_item(Item)
				!request_auction(Item)
				.
*/

//WIP

+!find_item <- cntItem("Brot",R)
				is_more_than(R)
.


//+!request_auction(Item) <- 	.send(auctioneer,tell,request(Item,"SealedBid"))

+!request_auction(Item,Type) <-		-nextSeller(true)
									.send(auctioneer,tell,request(Item,Type)).
					 

+auction_accepted(true) : true <- 	-seller(false);
									+seller(true)
									.print("Ich bin Verkäufer").
									
-auction_accepted(true) : true <- 	-seller(true);
								  	+seller(false)
									.print("Ich bin nicht mehr Verkäufer").

+running_auction(true) : true <- 	.print("OK").

+auction(Item,Type) : seller(false) <- !bid(10).

/*
-auction(Item,Type) <- !request_auction("Bier","Vikery").
*/
-auction(Item,Type): true <- .print("Entering Queue!!!!!")
								!enterQueue.
/*-running_auction(true): true <- .print("Entering Queue!!!!!")
								!enterQueue.*/


+!bid(X) <- .send(auctioneer,tell,bid(X)).

+!enterQueue: .my_name(Name) <- enter(Name).



{ include("$jacamoJar/templates/common-cartago.asl") }
{ include("$jacamoJar/templates/common-moise.asl") }

// uncomment the include below to have an agent compliant with its organisation
//{ include("$moiseJar/asl/org-obedient.asl") }

