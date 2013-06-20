package smoker;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class TableMonitor implements ITable {

	List<String> zutaten;
	
	public TableMonitor() {
		this.zutaten = new ArrayList<String>();
	}
	
	@Override
	public synchronized void putOnTable(List<String> zutaten) throws InterruptedException {
		while(!this.zutaten.isEmpty()) {
	//		System.out.println("Auf dem Tisch liegen Zutaten. Der Agent wartet");
			wait();
		}
		//Der Agent kann Zutaten auf den Tisch legen
		System.out.println("Der Agent legt "+zutaten.get(0)+" und "+zutaten.get(1)+" auf den Tisch");
		this.zutaten = zutaten;
		notifyAll();
	}
	
	private synchronized boolean isCorrectIngredient(String ingredient) {
		return !zutaten.isEmpty() && !zutaten.get(0).equals(ingredient) && !zutaten.get(1).equals(ingredient);
	}
	
	@Override
	public synchronized void smoke(Smoker smoker) throws InterruptedException {
		while (!isCorrectIngredient(smoker.getIngredient())) {
		//	System.out.println("Smoker "+smoker.name+" hat nicht die richtige Zutat und wartet weiter");
			wait();
		}
		//Smoker kann rauchen
		this.zutaten.clear();
		Random random = new Random();
		
		System.out.println("Smoker "+smoker.getSmokerId()+" beginnt zu rauchen" );
		Thread.sleep(Smoker.MIN_TIME_TO_SMOKE + random.nextInt(Smoker.MAX_TIME_TO_SMOKE - Smoker.MIN_TIME_TO_SMOKE));
		System.out.println("Smoker "+smoker.getSmokerId()+" ist fertig");
		notifyAll();
	}
	
	

}
