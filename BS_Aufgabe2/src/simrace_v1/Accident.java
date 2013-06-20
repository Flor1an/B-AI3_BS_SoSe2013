package simrace_v1;
import java.util.Random;


public class Accident extends Thread {


	public void run() {
		Random r = new Random();
		int tmp = r.nextInt(100*SimRace.ANZAHL_RUNDEN);
		//maximale Dauer des Rennens ermitteln = maximale Zeit in der es zu einem Unfall kommen kann 
		try {
			sleep(tmp);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
