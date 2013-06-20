package smoker;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class TableLock implements ITable {

	List<String> zutaten;
	final Lock lock = new ReentrantLock();
	final Condition waitForEmptyTable = lock.newCondition();
	final Condition waitForRightIngredient = lock.newCondition();

	public TableLock() {
		this.zutaten = new ArrayList<String>();
	}
	
	@Override
	public void putOnTable(List<String> zutaten) throws InterruptedException {
		try {
		
		lock.lock();
		
			while(!this.zutaten.isEmpty()) {
					//	System.out.println("Auf dem Tisch liegen Zutaten. Der Agent wartet");
						this.waitForEmptyTable.await();
					}
					//Der Agent kann Zutaten auf den Tisch legen
					System.out.println("Der Agent legt "+zutaten.get(0)+" und "+zutaten.get(1)+" auf den Tisch");
					this.zutaten = zutaten;
					this.waitForRightIngredient.signalAll();
		} catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return;
        } finally {
			lock.unlock();
		}
	}
	
	private boolean isCorrectIngredient(String ingredient) {
		return !zutaten.isEmpty() && !zutaten.get(0).equals(ingredient) && !zutaten.get(1).equals(ingredient);
	}
	
	@Override
	public void smoke(Smoker smoker) throws InterruptedException {
		try {
			lock.lock();

			while (!isCorrectIngredient(smoker.getIngredient())) {
				//	System.out.println("Smoker "+smoker.getSmokerId()+" hat nicht die richtige Zutat und wartet weiter");
					this.waitForRightIngredient.await();
				}
				//Smoker kann rauchen
				this.zutaten.clear();
				Random random = new Random();
				
				System.out.println("Smoker "+smoker.getSmokerId()+" hat "+smoker.getIngredient()+" und beginnt zu rauchen" );
				Thread.sleep(Smoker.MIN_TIME_TO_SMOKE + random.nextInt(Smoker.MAX_TIME_TO_SMOKE - Smoker.MIN_TIME_TO_SMOKE));
				System.out.println("Smoker "+smoker.getSmokerId()+" ist fertig");
				this.waitForEmptyTable.signal();
		}  catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return;
		} finally {
			lock.unlock();
		}
	}


}
