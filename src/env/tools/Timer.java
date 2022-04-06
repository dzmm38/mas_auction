package tools;

public class Timer {
	
	int time;
	
	public Timer(int time) {
		this.time = time*1000;	//mit 1000 multiplizieren um auf sec zu kommen
	}
	
	boolean goTime() {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		//System.out.println("Timer Run out!!!!!");	//aktuell nur debug
		return true;
	}
}
