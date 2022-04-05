// Agent sample_agent in project mas_auction

/* Initial beliefs and rules */

running_auction(false). //kann nur eine Auktion gleichzeitig geben

/* Initial goals */

+request(Item,Type)[source(Ag)] : true <- 	!check_auction(Item,Type,Ag);
											-request(Item,Type)[source(Ag)].
											
+bid(Value)[source(Ag)] : true <- !processBid(Value,Ag).

+isDone(true) <- !closeAuction.

+done <- !closeAuction.

+running_auction(false) <- !askParticipant.

+expired(true) <- !closeAuction.

/* Plans */


@askParticipants
+!askParticipant: nextParticipant(Np) <- 	if(NP == "Empty"){
											.print("Aktuell kein Verkäufer!!!!")
											.wait(2000)
											!askParticipant
											}else{
											.send(Np,tell,nextSeller(true))
											getNext
											}
											.

-!askParticipant <- .print("Aktuell kein Verkäufer!!!!")
					.wait(2000)
					!askParticipant.								


/* Checks if an Auction is already running */
+!check_auction(Item,Type,Ag) : running_auction(false) <-	-running_auction(false);
															+running_auction(true)
															//.print("gibt noch nix")
															//.print(Ag)
															.send(Ag,tell,auction_accepted(true))
															!announce(Item,Type)
															.											
	
															
@auction_announce															
+!announce(Item,Type) <- +auction(Item,Type)
						 !createArtifacts
						 .broadcast(tell,auction(Item,Type)) //weiß der auktionator an wen er die nachricht geschickt hat
						 .print("Neue Auktion für ", Item, " (",Type,")")
						 .	


@creteArtifacts					 
+!createArtifacts : auction(_,"SealedBid") <- 	.count(hi[_],Participants)
												makeArtifact("Counter","tools.Counter",[Participants-1],C_Id);
												focus(C_Id);
												makeArtifact("Urn_sb","tools.AuctionNote_sealedBid",[],Sb_Id);
												focus(Sb_Id).
															
+!createArtifacts : auction(_,"Vikery") 	<- 	.count(hi[_],Participants)
										   		makeArtifact("Counter","tools.Counter",[Participants-1],C_Id);
										   		focus(C_Id);
												makeArtifact("Urn_vik","tools.AuctionNote_vikery",[],Vik_Id);
												focus(Vik_Id).	
																	
+!createArtifacts : auction(_,"English") 	<- 	makeArtifact("Urn_en","tools.AuctionNote_english",[],En_Id);
												focus(En_Id);
												makeArtifact("Timer","tools.TimerController",[],T_Id);
												focus(T_Id).		 
		
		
@processBids						 
+!processBid(Value,Ag) : auction(_,"SealedBid") <- //.print("Type: SealedBid ", "(",Value, " Gebot von: ", Ag,")")
													.print("Neues Gebot von ",Ag,": ", Value)
													receiveBid(Ag,Value);
													-bid(Value)[source(Ag)]
													inc.
													
+!processBid(Value,Ag) : auction(_,"Vikery") 	<- //.print("Type: Vikery ", "(",Value, " Gebot von: ", Ag,")")
													.print("Neues Gebot von ",Ag,": ", Value)
													receiveBid(Ag,Value);
													-bid(Value)[source(Ag)]
													inc.

+!processBid(Value,Ag) : auction(_,"English") & winningBid(WinValue) 	<- 	//.print("Type: English ", "(",Value, " Gebot von: ", Ag,")")
																			.print("Neues Gebot von ",Ag,": ", Value)
												 							if(WinValue < Value){
												 							.broadcast(untell,highestBid(_))
												 							.broadcast(tell,highestBid(Value))
																			reset
												 							}
												 							receiveBid(Ag,Value);
												 							-bid(Value)[source(Ag)].
												 							
+!processBid(Value,Ag) 							<- .print("Nicht implementierte Auktion").


@closeAuction
+!closeAuction : winningBid(WinValue) & winner(WinAg) & auction(Item,_) <-	if(WinValue > 0){
																				.print("Gewinner ist: ",WinAg, " mit Gebot von: ", WinValue) 
														 						.broadcast(tell,result(WinAg,WinValue,Item))
														 					}else{
														 						.print("Es wurde kein Gebot abgegeben") 
														 					}
														 					!destroyArtifacts
														 					
														 					.print("---------------------------------------------")
														 					.print("Auktion beendet, neue kann angefordert werden")
														 					.print("---------------------------------------------")
														 					!removeBeliefs
														 					.
														 					
-!closeAuction : winningBid(WinValue) & winner(WinAg) & auction(Item,_) <- 	.print("Es wurde kein Gebot abgegeben") 
														 					!destroyArtifacts
														 					!removeBeliefs
														 					.print("---------------------------------------------")
														 					.print("Auktion beendet, neue kann angefordert werden")
														 					.print("---------------------------------------------")
														 					.

+!removeBeliefs : auction(Item,Type) <- 	.broadcast(untell,highestBid(_))			//<- Nur für English Auction
											
											/* Für Alle Auctions */
											.broadcast(untell,auction_accepted(true))
											.broadcast(untell,auction(Item,Type));
											-auction(Item,Type);
											.broadcast(untell,nextSeller(true));
											-running_auction(true);
											+running_auction(false);
											.
											
														 
@destroyArtifacts														 
+!destroyArtifacts : auction(_,"SealedBid") <- 	lookupArtifactByType("tools.Counter",C_Id);
					  							stopFocus(C_Id);
					  							disposeArtifact(C_Id);
					  							lookupArtifactByType("tools.AuctionNote_sealedBid",Sb_Id);
					  							stopFocus(Sb_Id);
					  							disposeArtifact(Sb_Id)
					  							.
					  							
+!destroyArtifacts : auction(_,"Vikery") <- 	lookupArtifactByType("tools.Counter",C_Id);
					  							stopFocus(C_Id);
					  							disposeArtifact(C_Id);
					  							lookupArtifactByType("tools.AuctionNote_vikery",Vik_Id);
					  							stopFocus(Vik_Id);
					  							disposeArtifact(Vik_Id).
					  							
+!destroyArtifacts : auction(_,"English") <-	lookupArtifactByType("tools.AuctionNote_english",En_Id);
					  							stopFocus(En_Id); 
					  							disposeArtifact(En_Id)
					  							lookupArtifactByType("tools.TimerController",T_Id);
					  							stopFocus(T_Id); 
					  							disposeArtifact(T_Id).
					  														
															

{ include("$jacamoJar/templates/common-cartago.asl") }
{ include("$jacamoJar/templates/common-moise.asl") }


// uncomment the include below to have an agent compliant with its organisation
//{ include("$moiseJar/asl/org-obedient.asl") }


