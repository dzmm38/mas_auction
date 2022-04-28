
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
			  .

+!setup_inventory_demands : true <-	.my_name(Me)
							makeArtifact(Me, "tools.Inventory", [], ID)
							!enterQueue.

							
 																				
+nextSeller(true): true <- 	nextItemToSell(Item)
							!request_auction(Item,"English"). //"SealedBid"		"Vickery"		"English"

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
									//.print("Ich bin nicht mehr Verkäufer")
									.

+running_auction(true) : true <- 	.print("OK").

+auction(Item,Type) : seller(false) <- !bid(Item, Type).

/*
-auction(Item,Type) <- !request_auction("Bier","Vikery").
*/
-auction(Item,Type): true <- //.print("Entering Queue!!!!!")
								!enterQueue.
								
/*-running_auction(true): true <- .print("Entering Queue!!!!!")
								!enterQueue.*/
								
 +highestBid(Value, CurrentItem, Ag) <- !keepBidding(Value, CurrentItem, Ag).							
								
+result(WinAg,WinValue,Item): seller(true) <- 	!calculateSeller(WinAg, WinValue, Item);
												-result(_,_,_)[source(_)].
												
+result(WinAg,WinValue,Item): seller(false) <- -result(_,_,_)[source(_)].										


+auctionWinner(Agent,WinValue,Item): true <- !calculateBuyer(Agent, WinValue, Item).
											 -acutionWinner(_,_,_)[source(_)].
											 
+simulationDone <- .print("Mache nichts mehr: Simulation Fertig").

//-result(_,_,_) <- send("auctioneer",untell, traidingDone).											

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
											.print(Item, ": ", ItemCount2)
											//.send("auctioneer",tell,traidingDone)
											.
											
+!calculateBuyer(Agent, WinValue, Item) <- .print("Gewinner Gewinner Gewinner")
											addMoney(-WinValue)
											cntItem(Item, ItemCount)
											.print(Item, ": ",ItemCount)
											storeItem(Item)
											cntItem(Item, ItemCount2)
											.print(Item, ": ",ItemCount2)
											.
														
															


/*Richtige!!!!!! */
+!bid(Item, Type) <- 	bidMoney(Item, Type ,X)
						.send(auctioneer,tell,bid(X)).												
						
/*Testversion 
+!bid(X) <- 	.wait(math.random * 5000 + 2000)
				.send(auctioneer,tell,bid(X)).				
*/

+!keepBidding(Value, CurrentItem, Ag) <- .my_name(Name)
										 .print(Name, Ag, "Keep bidding")
										 if(Name \== Ag){
										 	calculateNextBid(Value, CurrentItem, Ag, X)
										 	.send(auctioneer, tell, bid(X))
										 }.
										 


+!enterQueue: .my_name(Name) <- doWeHaveItemsToSell(ItemsToSell)
								if(ItemsToSell){
									.print("Ich gehe in die Queue...................", ItemsToSell)
									enter(Name)
								}else{
									.print("Skipping q, nothing to sell ", ItemsToSell )
								}
								.



{ include("$jacamoJar/templates/common-cartago.asl") }
{ include("$jacamoJar/templates/common-moise.asl") }

// uncomment the include below to have an agent compliant with its organisation
//{ include("$moiseJar/asl/org-obedient.asl") }

