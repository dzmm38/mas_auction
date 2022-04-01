
// Agent participant in project mas_auction

/* Initial beliefs and rules */

seller(false).

is_more_than(R)
	:- brot(C) & (R - C) > 0.
	
	

/* Initial goals */

!sayHello.
!setup_inventory_demands.

//!request_auction("Brot","SealedBid").

/*!sell_item.*/



/* Plans */

+!sayHello <- .send(auctioneer, tell, hi);
			  .print("HALLO").

+!setup_inventory_demands : true <-	.my_name(Me)
							makeArtifact(Me, "tools.Inventory", [], ID)
							!enterQueue.

							
 																				
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
									.print("Ich bin Verk�ufer").
									
-auction_accepted(true) : true <- 	-seller(true);
								  	+seller(false)
									.print("Ich bin nicht mehr Verk�ufer").

+running_auction(true) : true <- 	.print("OK").

+auction(Item,Type) : seller(false) <- !bid(Item, Type).

/*
-auction(Item,Type) <- !request_auction("Bier","Vikery").
*/
-auction(Item,Type): true <- .print("Entering Queue!!!!!")
								!enterQueue.
/*-running_auction(true): true <- .print("Entering Queue!!!!!")
								!enterQueue.*/
								
+result(WinAg,WinValue,Item): seller(true) <- !calculateSeller(WinAg, WinValue, Item).	

+auctionWinner(Agent,WinValue,Item): true <- !calculateBuyer(Agent, WinValue, Item).											

+!calculateSeller(Agent, WinValue, Item) <- .print("Bekomme Geld")
											.send(Agent,tell,auctionWinner(Agent, WinValue, Item))
											getMoney(Money)
											.print("Money: ",Money)
											addMoney(WinValue)
											getMoney(Money2)
											.print("Money: ",Money2)
											cntItem(Item, ItemCount)
											.print(Item, ": ", ItemCount)
											retrieveItem(Item)
											cntItem(Item, ItemCount2)
											.print(Item, ": ", ItemCount2).
											
+!calculateBuyer(Agent, WinValue, Item) <- .print("Gewinner Gewinner Gewinner")
											addMoney(-WinValue)
											cntItem(Item, ItemCount)
											.print(Item, ": ",ItemCount)
											storeItem(Item)
											cntItem(Item, ItemCount2)
											.print(Item, ": ",ItemCount2)
											.
														
															


+!bid(Item, Type) <- 	bidMoney(Item, Type ,X)
						.send(auctioneer,tell,bid(X)).

+!enterQueue: .my_name(Name) <- doWeHaveItemsToSell(ItemsToSell)
								if(ItemsToSell){
									.print("Ich gehe in die Queue...................")
									enter(Name)
								}
								.



{ include("$jacamoJar/templates/common-cartago.asl") }
{ include("$jacamoJar/templates/common-moise.asl") }

// uncomment the include below to have an agent compliant with its organisation
//{ include("$moiseJar/asl/org-obedient.asl") }

