package smoker;
public class Smoker extends Thread {

	String smokerId;
	String zutat;
	ITable table;
	
	public final static int  MIN_TIME_TO_SMOKE = 500;
	public final static int  MAX_TIME_TO_SMOKE = 2000;
	
	public Smoker(String id, String zutat, ITable table) {
		this.smokerId = id;
		this.zutat = zutat;
		this.table = table;
	}
	
	@Override
	public void run() {
		while(!isInterrupted()) {
			try {
				table.smoke(this);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}
		System.out.println("Smoker "+smokerId+" hat den Tisch verlassen");
	}

	public String getSmokerId() {
		return smokerId;
	}

	public String getIngredient() {
		return zutat;
	}
	
	
}
