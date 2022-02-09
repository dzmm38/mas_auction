
// Agent participant in project mas_auction

/* Initial beliefs and rules */

seller(false).
wurst(2).
brot(2).
kaese(2).
bier(2).

is_more_than(R)
	:- brot(C) & (R - C) > 0.
	
	

/* Initial goals */

!setup_inventory.
!sell_item.



/* Plans */

+!setup_inventory : true <-	.my_name(Me)
								makeArtifact(Me, "tools.Inventory", [], ID)													
								.
//WIP
+!sell_item <- 	!find_item(Item)
				!request_auction(Item)
				.
//WIP
+!find_item <- cntItem("Brot",R)
				is_more_than(R)
.

+!request_auction(Item) <- 	.send(auctioneer,tell,request(Item,"SealedBid"))
						.
					 

+auction_accepted(true) : true <- 	-seller(false);
									+seller(true)
									.print("Ich bin Verkäufer").

+running_auction(true) : true <- 	.print("OK").

+auction(Item,Type) : seller(false) <- !bid(10).


+!bid(X) <- .send(auctioneer,tell,bid(X)).





{ include("$jacamoJar/templates/common-cartago.asl") }
{ include("$jacamoJar/templates/common-moise.asl") }

// uncomment the include below to have an agent compliant with its organisation
//{ include("$moiseJar/asl/org-obedient.asl") }

