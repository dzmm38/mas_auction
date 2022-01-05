// Agent participant in project mas_auction

/* Initial beliefs and rules */

/* Initial goals */

!request_auction.

/* Plans */

+!request_auction <- .send(auctioneer,tell,request("Brot","SealedBid")).

+running_auction(true) : true <- .print("OK").

+auction(Item,Type) : true <- !bid(10).


+!bid(X) <- .send(auctioneer,tell,bid(X)).





{ include("$jacamoJar/templates/common-cartago.asl") }
{ include("$jacamoJar/templates/common-moise.asl") }

// uncomment the include below to have an agent compliant with its organisation
//{ include("$moiseJar/asl/org-obedient.asl") }
