package simrace_v2;

import java.util.Random;

public class Accident extends Thread {
	Thread parent;

	public Accident(Thread parent) {
		this.parent = parent;
	}

	@Override
	public void run() {
		Random rand = new Random();
		int random = rand.nextInt(SimRace.MOEGLICHER_UNFALL_ZEITPUNKT);

		try {
			Thread.sleep(random);
		} catch (InterruptedException e) {
			this.interrupt();
		}
		parent.interrupt();
	}

}
