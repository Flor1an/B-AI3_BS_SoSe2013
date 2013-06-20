package smoker;
import java.util.ArrayList;
import java.util.List;


public class Main {

	public static final int DURATION = 20000;
	
	public static void main(String[] args) {
		//Initialization
		//ITable table = new TableMonitor();	
		ITable table = new TableLock();
		
		List<Smoker> smokers = new ArrayList<Smoker>();
		smokers.add(new Smoker("1", "tobacco", table));
		smokers.add(new Smoker("2", "paper", table));
		smokers.add(new Smoker("3", "matches", table));
		
		System.out.println("Smokerproblem gestartet");
		
		for (Smoker smoker : smokers) {
			smoker.start();
		}
		Agent agent = new Agent(table);
			agent.start();
		
		//Wait
		try {
			Thread.sleep(DURATION);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("Smokerproblem wird beendet");
		//End
		for (Smoker smoker : smokers) {
			smoker.interrupt();
		}
		agent.interrupt();
		
		
		try {
			for (Smoker smoker : smokers) {
				smoker.join();
			}
			agent.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("Smokerproblem ist beendet");
	}

}
