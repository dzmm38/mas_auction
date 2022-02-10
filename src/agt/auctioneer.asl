
// Agent sample_agent in project mas_auction


/* Initial beliefs and rules */

running_auction(false). //kann nur eine Auktion gleichzeitig geben

/* Initial goals */

+request(Item,Type)[source(Ag)] : true <- !check_auction(Item,Type,Ag).
+bid(Value)[source(Ag)] : true <- !processBid(Value,Ag).

+isDone(true) <- !closeAuction.

/* Plans */

/* Checks if an Auction is already running */
+!check_auction(Item,Type,Ag) : running_auction(false) <-	-running_auction(false);
															+running_auction(true)
															.print("gibt noch nix")
															.print(Ag)
															.send(Ag,tell,auction_accepted(true))
															!announce(Item,Type)
															.
												
+!check_auction(Item,Type,Ag) : running_auction(true) <- 	.print("Gibt schon was!")
															.print(Ag)
															.send(Ag,tell,running_auction(true)).
@auction_announce															
+!announce(Item,Type) <- +auction(Item,Type)
						 !createArtifacts
						 .broadcast(tell,auction(Item,Type)) //weiß der auktionator an wen er die nachricht geschickt hat
						 .print("auction with")
						 .print(Item)
						 .print(Type)
						 .	

@creteArtifacts					 
+!createArtifacts : auction(_,"SealedBid") <- 	.count(hi[_],Participants)
												makeArtifact("Counter","tools.Counter",[Participants-1],C_Id);
												focus(C_Id);
												makeArtifact("Urn_sb","tools.AuctionNote_sealedBid",[],Sb_Id);
												focus(Sb_Id);
												.
															
+!createArtifacts : auction(_,"Vikery") <- 	.count(hi[_],Participants)
										   	makeArtifact("Counter","tools.Counter",[Participants-1],C_Id);
										   	focus(C_Id);
											makeArtifact("Urn_vik","tools.AuctionNote_vikery",[],Vik_Id);
											focus(Vik_Id);
											.	
																	
+!createArtifacts : auction(_,"English") <- makeArtifact("Urn_en","AuctionNote_english",[],En_Id);
											/* Hier muss noch ein Countdown Artifact erstellt werden */
											.						 
		
@processBids						 
+!processBid(Value,Ag) : auction(_,"SealedBid") <- .print("Type: SealedBid", Value, Ag)
													receiveBid(Ag,Value)
													inc;
													.
													
+!processBid(Value,Ag) : auction(_,"Vikery") <- .print("Type: Vikery")
												receiveBid(Ag,Value)
												inc;
												.

+!processBid(Value,Ag) : auction(_,"English") <- .print("Type: English")
												 recieveBid(Ag,Value).
												 
+!processBid(Value,Ag) <- .print("Nicht implementierte Auktion").

@closeAuction
+!closeAuction : winningBid(WinValue) & winner(WinAg) <- .print(WinValue) 
														 .print(WinAg)
														 .broadcast(tell,result(WinAg,WinValue))
														 .
															

{ include("$jacamoJar/templates/common-cartago.asl") }
{ include("$jacamoJar/templates/common-moise.asl") }


// uncomment the include below to have an agent compliant with its organisation
//{ include("$moiseJar/asl/org-obedient.asl") }

