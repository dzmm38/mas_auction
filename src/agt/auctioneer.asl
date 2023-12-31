// Agent sample_agent in project mas_auction

/* Initial beliefs and rules */

running_auction(false). //kann nur eine Auktion gleichzeitig geben
nullRunde(0).
emptyQueue(0).

/* Initial goals */

+request(Item,Type)[source(Ag)] : true <- 	-request(Item,Type)[source(Ag)]
											if(Item == "nix"){
											//.print("Item ist nicht vorhanden !!!!!!!: ", Item)
											!askParticipant
											}else{
											!check_auction(Item,Type,Ag);
											}.
											
+bid(Value)[source(Ag)] : true <- !processBid(Value,Ag).

+isDone(true) <- !closeAuction.

+done <- !closeAuction.

+running_auction(false) <- !askParticipant.

+expired(true) <- !closeAuction.

+nullRunde(8) <- !terminate.

+emptyQueue(5) <- !terminate.

//+traidingDone <- broadcast(untell,result(_,_,_)).

/* Plans */


@askParticipants
+!askParticipant: nextParticipant(NP) & nullRunde(Mcount) & emptyQueue(Qcount) <- 	if(Mcount < 8){
																						if(Qcount < 5){
																							if(NP == "Empty"){	
																								-emptyQueue(_)
																								+emptyQueue(Qcount+1)
																								.print("Aktuell kein Verk�ufer!!!!")
																								.wait(2000)
																								!askParticipant
																							}else{
																								
																								-emptyQueue(_)
																								+emptyQueue(0)																			
																								.send(NP,tell,nextSeller(true))
																								getNext
																							}
																						}		
																					}
																					.

-!askParticipant : emptyQueue(Qcount) <-.print("Aktuell kein Verk�ufer!!!!")
										.wait(2000);
										-emptyQueue(_);
										+emptyQueue(Qcount+1)
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
						 .broadcast(tell,auction(Item,Type)) //wei� der auktionator an wen er die nachricht geschickt hat
						 .print("Neue Auktion f�r ", Item, " (",Type,")")
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
													if(Value > 0){
														.print("Neues Gebot von ",Ag,": ", Value)
													}
													receiveBid(Ag,Value);
													-bid(Value)[source(Ag)]
													inc.
													
+!processBid(Value,Ag) : auction(_,"Vikery") 	<- //.print("Type: Vikery ", "(",Value, " Gebot von: ", Ag,")")
													if(Value > 0){
														.print("Neues Gebot von ",Ag,": ", Value)
													}
													receiveBid(Ag,Value);
													-bid(Value)[source(Ag)]
													inc.

+!processBid(Value,Ag) : auction(CurrentItem,"English") & winningBid(WinValue) 	<- 	//.print("Type: English ", "(",Value, " Gebot von: ", Ag,")")
																			if(Value > 0){
																				.print("Neues Gebot von ",Ag,": ", Value)
																			}
												 							if(WinValue < Value){
												 							.broadcast(untell,highestBid(_,_,_))
												 							.broadcast(tell,highestBid(Value,CurrentItem,Ag))
																			reset
												 							}
												 							receiveBid(Ag,Value);
												 							-bid(Value)[source(Ag)].
												 							
+!processBid(Value,Ag) 							<- .print("Nicht implementierte Auktion").


@closeAuction
+!closeAuction : winningBid(WinValue) & winner(WinAg) & auction(Item,_) & nullRunde(Ncount) <-	if(WinValue > 0){
																								.print("Gewinner ist: ",WinAg, " mit Gebot von: ", WinValue) 
														 										.broadcast(tell,result(WinAg,WinValue,Item));
														 										-nullRunde(_);
														 										+nullRunde(0)
														 										}else{
														 										.print("Es wurde kein Gebot abgegeben");
														 										-nullRunde(_);
														 										+nullRunde(Ncount+1)
														 										}
														 										.wait(2000);		//Delay zur besseren anschauung
														 										!destroyArtifacts
														 					
														 										.print("---------------------------------------------")
														 										.print("Auktion beendet, neue kann angefordert werden")
														 										.print("---------------------------------------------")
														 										!removeBeliefs
														 										.
														 					
-!closeAuction : winningBid(WinValue) & winner(WinAg) & auction(Item,_) & nullRunde(Ncount) <- 	.print("Es wurde kein Gebot abgegeben");
														 										-nullRunde(_);
														 										+nullRunde(Ncount+1)
														 										!destroyArtifacts
														 										!removeBeliefs
														 										.print("---------------------------------------------")
														 										.print("Auktion beendet, neue kann angefordert werden")
														 										.print("---------------------------------------------")
														 										.

+!removeBeliefs : auction(Item,Type) <- 	.broadcast(untell,highestBid(_,_,_))			//<- Nur f�r English Auction
											
											/* F�r Alle Auctions */
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
					  							
+!terminate <- 	.broadcast(tell,simulationDone).													

{ include("$jacamoJar/templates/common-cartago.asl") }
{ include("$jacamoJar/templates/common-moise.asl") }


// uncomment the include below to have an agent compliant with its organisation
//{ include("$moiseJar/asl/org-obedient.asl") }


