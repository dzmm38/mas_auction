package tools;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import cartago.Artifact;
import cartago.OPERATION;

public class TimerOld extends Artifact{
	
	ScheduledThreadPoolExecutor stp;
	Updater task;
	int time = 10;
	ScheduledFuture f;
	
	void init() {
		defineObsProperty("isDone",false);
		
		System.out.println("Setting Timer!!!!!!!!!!!!!!!!!!!!!!!!");
		
		stp = new ScheduledThreadPoolExecutor(1);
		task = new Updater();
		
		f = stp.schedule(task, time, TimeUnit.SECONDS);
		
	}
	
	@OPERATION
	void reset(){
		
		System.out.println("Resetting Timer!!!!!!!!!!!!!!!!!!!!!!!!");
		
		f.cancel(true);
		//stp.remove(task);
		f = stp.schedule(task, time, TimeUnit.SECONDS);
		
		check();
	}
	
	void check() {
		boolean done;
		try {
			done = (boolean) f.get();
			if(done) {
				System.out.println(done);
				getObsProperty("isDone").updateValue(true);
			}
		} catch (InterruptedException | ExecutionException e) {
			System.out.println("Hoffentliche sehen wir das nie !!!!!");
			e.printStackTrace();
		}
	}
	
	
	class Updater implements Callable<Boolean>{
		@Override
		public Boolean call() throws Exception {
			System.out.println("Timer Expired !!!!!!!!!!!!!!!!!!!!!!!!!!!");
			return true;
		}
	}
	
}
