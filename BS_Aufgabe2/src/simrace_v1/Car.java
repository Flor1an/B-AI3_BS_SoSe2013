package simrace_v1;
import java.util.Random;

public class Car extends Thread implements Comparable<Car> {
	private int wagenNummer = 0;
	private int rundenZaehler = 1;
	private int gesamtRundenZeit = 0;
	private int aktuelleRundenZeit = 0;

	/**
	 * Fahrzeug für Rennen vorbereiten
	 * @param wagenNummer
	 */
	public Car(int wagenNummer) {
		this.wagenNummer = wagenNummer;
	}

	/**
	 * Fahrzeug starten und während der Runden die Zeiten messen
	 */
	public void run() {
		System.err.println("Wagen " + wagenNummer + " gestartet...");
		Random r = new Random();

		do {
			aktuelleRundenZeit = r.nextInt(100); // Rundenzeit "messen"
			gesamtRundenZeit += aktuelleRundenZeit;
			try {
				sleep(aktuelleRundenZeit);
				System.err.println("-Wagen " + wagenNummer + ": Runde " + rundenZaehler + " in " + aktuelleRundenZeit + "ms" );
			} catch (InterruptedException e) {
				interrupt();
			}
			rundenZaehler++;

		} while (rundenZaehler <= SimRace.ANZAHL_RUNDEN);

	}
	
	public int getGesamtRundenZeit(){
		return gesamtRundenZeit;
	}
	
	public int getWagenNummer(){
		return wagenNummer;
	}

	@Override
	public int compareTo(Car other) {
		return gesamtRundenZeit - other.getGesamtRundenZeit();
	}
}
