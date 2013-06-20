package mensa2;

import java.util.Random;
import java.util.Set;
import java.util.UUID;

public class Student extends Thread {
	private Mensa mensa = null;
	private String name = null;
	private Random rg = new Random();

	public Student(Mensa mensa) {
		this.mensa = mensa;
		this.name = this.getName(); // UUID.randomUUID().toString();
	}

	@Override
	public void run() {
		while (!Thread.currentThread().isInterrupted()) {

			// anstellen + bezahlen
			try {
				Kasse kurzeKasse = mensa.gibKuerzesteKasse();

				mensa.bezahle(kurzeKasse);

			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				gehNachhause();
			}
		
			// essen
			try {
				System.out.println("Student " + name + " isst");
				Thread.sleep(rg.nextInt(Constants.MAX_EATING_TIME));
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				gehNachhause();
			}
			

			// tablet abgeben und co
			try {
				System.out.println("Student " + name + " macht irgendwas");
				Thread.sleep(rg.nextInt(Constants.ZWISCHEN_TIME));
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				gehNachhause();
			}

		}
	}

	private void gehNachhause() {
		System.out.println("Student " + name + " geht nachhause");
	}

}
