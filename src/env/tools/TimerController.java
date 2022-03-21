package tools;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import cartago.Artifact;
import cartago.OPERATION;
import tools.Timer.Updater;

public class TimerController extends Artifact{
	ScheduledThreadPoolExecutor stp;
	Timer timer;
	int time = 10;
	ScheduledFuture timerObjects;
	
	void init(){
		timerObjects = stp.schedule(timer, 10, TimeUnit.SECONDS);
	}
	
	@OPERATION
	void reset(){
		timerObjects.cancel(true);
		timerObjects = stp.schedule(timer, 10, TimeUnit.SECONDS);
	}
	
	class Timer implements Runnable {
		
		void timer(){
			
		}
		
		@Override
		public void run() {
			
		}
	}
}
