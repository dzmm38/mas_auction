
// Agent sample_agent in project mas_auction


/* Initial beliefs and rules */

running_auction(false). //kann nur eine Auktion gleichzeitig geben

/* Initial goals */

+request(Item,Type)[source(Ag)] : true <- !check_auction(Item,Type,Ag).
+bid(Value)[source(Ag)] : true <- !processBid(Value,Ag).

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
						 .broadcast(tell,auction(Item,Type)) //weiß der auktionator an wen er die nachricht geschickt hat
						 .print("auction with")
						 .print(Item)
						 .print(Type)
						 .	
						 
+!processBid(Value,Ag) : auction(_,"SealedBid") <- .print("Type: SealedBid", Value, Ag)
													receiveBid(Ag,Value)
													.



+!processBid(Value,Ag) : auction(_,"English") <- .print("Type: English").
+!processBid(Value,Ag) : auction(_,"Vikery") <- .print("Type: Vikery").
+!processBid(Value,Ag) <- .print("Nicht implementierte Auktion"). 													
															

{ include("$jacamoJar/templates/common-cartago.asl") }
{ include("$jacamoJar/templates/common-moise.asl") }


// uncomment the include below to have an agent compliant with its organisation
//{ include("$moiseJar/asl/org-obedient.asl") }

