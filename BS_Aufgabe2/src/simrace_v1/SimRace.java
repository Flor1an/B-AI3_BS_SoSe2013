package simrace_v1;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SimRace {
	static int ANZAHL_RUNDEN = 10;
	static int ANZAHL_AUTOS= 5;
	Set<Car> fahrerfeld=new HashSet<>();
	Accident a = new Accident();
	boolean unfallFrei=true;
	
	/**
	 * Initialisierung der Rennstrecke = Fahrzeuge auf die Strecke
	 */
	public SimRace() {
	startAufstellungEinnehmen();
	}
	
	
	/**
	 * Fahrzeuge Startpositionen einnehmen lassen
	 */
	private void startAufstellungEinnehmen(){
		for (int i = ANZAHL_AUTOS; i > 0; i--) {
			Car c = new Car(i);
			fahrerfeld.add(c);
		}
		System.out.println("****** ALLE FAHRER BEREIT *******\n");
	}
	
	/**
	 * Ampel schaltet auf Grün
	 */
	public void startSchuss(){
		System.out.println("*************** GO **************");
		for (Car c : fahrerfeld) {
			c.start();
		}
		
		
		a.start();//möglichekeit eines Unfalls ist nun gegeben.
		
	}
	
	/**
	 * Zieleinlauf beobachten / warten das alle ins Ziel kommen
	 */
	public void wartenAufZieleinlauf(){
		for (Car c : fahrerfeld) {
			
			if (a.isAlive()){ //Accidentmöglichkeit noch nicht zuende = bisher kein unfall
			
				try
				{
					c.join(); //Auf Zielüberquerung warten
					System.out.println("Wagen " + c.getWagenNummer() + " ist im Ziel!");
				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			}else{ // Unfall
				unfallFrei=false;
			}
		}
		if (unfallFrei){
			System.out.println("************ RENNENDE ***********\n");
		}else{
			System.out.println("\n\n************ RENNABBRUCH DURCH UNFALL ***********\n");
			for (Car c : fahrerfeld) {
				c.interrupt();
			}
		}
	}
	
	
	/**
	 * Ergebnis bekannt geben
	 */
	public void siegerehrung(){
		if(unfallFrei){
			System.out.println("********** SIEGEREHRUNG *********");
			System.out.println("Offizielles Endergebnis:");
			
			
			List<Car> platzierungen = new ArrayList<>(fahrerfeld);
			Collections.sort(platzierungen);
			int i=1;
			for (Car c : platzierungen) {
				System.out.println("Platz " + i++ + ": Wagen " + c.getWagenNummer() + " Gesamtzeit: " +  c.getGesamtRundenZeit()+ "ms");
			}
		}
	}
	

}
