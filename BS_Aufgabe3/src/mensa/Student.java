package mensa;

import java.util.List;
import java.util.Random;

public class Student extends Thread {

    private Random r = new Random();
    private List<Kasse> kassenListe;


    Student(String name, List<Kasse> kassenListe) {
        super(name);
        this.kassenListe = kassenListe;
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            try {
                Kasse ausgewaehlteKasse = kuerzesteWarsteschlange();

                ausgewaehlteKasse.getSemaphore().acquire();

                sleep(r.nextInt(100));	//bezahlprozess

                ausgewaehlteKasse.bezahlen(this);

                ausgewaehlteKasse.getSemaphore().release();

              	sleep(r.nextInt(500));  // essenszeit warten

                sleep(r.nextInt(2000)); // Tablett abgeben...
            } catch (InterruptedException ex) {
                currentThread().interrupt();
            }
        }
    }

    private Kasse kuerzesteWarsteschlange() {
        Kasse tempKasse = kassenListe.get(0);
 
	        for (Kasse kasse : kassenListe) {
	            if (kasse.getLaenge() < tempKasse.getLaenge()) {
	                tempKasse = kasse;
	            }
	        }
	        
	        tempKasse.increaseLaenge();
	
	        System.err.println(getName() + " stellt sich an " + tempKasse.getName() + " (" + tempKasse.getLaenge() + ") an.");
        
     
        
        
        return tempKasse;
    }


}
