package tools;

import java.util.concurrent.CompletableFuture;
import cartago.Artifact;
import cartago.INTERNAL_OPERATION;
import cartago.OPERATION;

public class TimerController extends Artifact{
	Timer timer;
	int voteTime;
	CompletableFuture<Boolean> future;
	
	void init(){
		defineObsProperty("isDone", false);
		voteTime = 2;
		startTimer();
	}
	
	void startTimer() {
		//System.out.println("(Re)Starting Timer ...");
		
		Timer timer = new Timer(voteTime);
		future = CompletableFuture.supplyAsync(timer::goTime);
		future.thenAccept(isDone -> execInternalOp("notifyAuctioneer"));	//--> Dieser Teil wird anscheinend nur ausgeführt wenn auch der Thread nich abgebrochen wird 
																			//    jedoch läuft der Thread trotzdem durch !!!!
																			//execInteralOp um dann mithilfe von cartago die Methode richtig ausführen zu könenn
	}
	
	@INTERNAL_OPERATION			//Internal op damit kein lock problem entsteht
	void notifyAuctioneer() {
		//System.out.println("Aution ended ...");
		//defineObsProperty("isDone", true);
		signal("done");	
	}
	
	@OPERATION
	void reset() {
		boolean canceled;
		canceled = future.cancel(true);
		//System.out.println("Cancel Timer ... : "+ canceled);
		startTimer();
	}
	
}
