package smoker;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Agent extends Thread {

	List<String> zutaten;
	ITable table;
	
	public Agent(ITable table) {
		zutaten = new ArrayList<String>(3);
		zutaten.add("tobacco");
		zutaten.add("paper");
		zutaten.add("matches");
		
		this.table = table;
	}
	
	@Override
	public void run() {
		while(!isInterrupted()) {
			Random random = new Random();
			List<String> zweiZutaten = new ArrayList<String>(zutaten);
			zweiZutaten.remove(random.nextInt(3));
			try {
				table.putOnTable(zweiZutaten);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}
		System.out.println("Agent hat den Tisch verlassen");
	}

}
