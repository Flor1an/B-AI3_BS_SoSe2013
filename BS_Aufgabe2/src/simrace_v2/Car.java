package simrace_v2;

import java.util.Random;

public class Car extends Thread implements Comparable<Car> {

    private String name;
    private int gesamtRundenZeit;
    
	public Car(String name) {
	   this.name = name;
	}
 
    @Override
    public void run() {

        for (int i = 0; i < SimRace.GESAMTRUNDEN; i++) {
            if (!isInterrupted()) {
                Random r = new Random();
                int rundenZeit = r.nextInt(SimRace.MAX_RUNDENZEIT);
                gesamtRundenZeit += rundenZeit;
                
                try {
                    Thread.sleep(rundenZeit);
                } catch (InterruptedException e) {
                    this.interrupt();
                }
            } else {
                return;
            }
        }
    }

   

    public String getWagenName() {
        return this.name;
    }

    public int getGesamtRundenZeit() {
        return this.gesamtRundenZeit;
    }


    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public int compareTo(Car other) {
        return this.gesamtRundenZeit - other.gesamtRundenZeit;
    }
}
