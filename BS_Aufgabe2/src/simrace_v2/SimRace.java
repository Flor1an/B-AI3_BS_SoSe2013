package simrace_v2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SimRace {

	private static final int FAHRZEUGE = 5;
	public static final int GESAMTRUNDEN = 4;
	public static final int MOEGLICHER_UNFALL_ZEITPUNKT = 400;
	public static final int MAX_RUNDENZEIT = 100;

	public static void main(String[] arg0) {

		List<Car> fahrerfeld = new ArrayList<>();
		Accident accident = new Accident(Thread.currentThread());

		for (int i = 1; i <= FAHRZEUGE; i++) {
			Car car = new Car("Wagen " + i);
			car.start();
			fahrerfeld.add(car);
		}
		accident.start();
		

		
		try {
			for (Car car : fahrerfeld) {
				car.join();
			}
			Collections.sort(fahrerfeld);

			System.out.println("************ RENNENDE ***********");
			int position = 1;
			for (Car car : fahrerfeld) {
				System.out.println(position++ + ". Platz: " + car.getWagenName() + " Zeit: " + car.getGesamtRundenZeit());
			}
		} catch (InterruptedException ex) {
			System.out.println("************ RENNABBRUCH DURCH UNFALL ***********");
			for (Car car : fahrerfeld) {
				car.interrupt();
			}
		}
	}


}
