package mensa2;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.Semaphore;

public class Mensa extends Thread {
	private Set<Kasse> kassen = null;
	private Set<Thread> studenten = null;
	private Semaphore kassenLock = null;

	public Mensa(int number_students, int number_counters) {
		kassen = new HashSet<Kasse>();
		studenten = new HashSet<Thread>();

		// Initialisiere Kassen
		for (int i = 0; i < number_counters; i++) {
			kassen.add(new Kasse("Kasse " + i));
		}

		// Initialisiere Studenten
		for (int i = 0; i < number_students; i++) {
			studenten.add(new Student(this));
		}

		// Initialisiere Semaphore
		kassenLock = new Semaphore(1, true);

	}

	/**
	 * Die Mensa öffnet
	 */
	private void oeffnen() {
		for (Thread s : studenten) {
			s.start();
		}
	}

	/**
	 * Die Studenten werden interrupted
	 */
	private void schliessen() {
		for (Thread s : studenten) {
			s.interrupt();
		}
	}

	public Set<Kasse> getKassen() {
		return kassen;
	}

	public Kasse gibKuerzesteKasse() throws InterruptedException {
		kassenLock.acquire();
			Kasse currentMinKasse = null;
			int currentMinLength = Integer.MAX_VALUE;
			for (Kasse k : kassen) {
				if (k.getSchlangenLength() < currentMinLength) {
					currentMinKasse = k;
					currentMinLength = k.getSchlangenLength();
				}
			}
			currentMinKasse.incr();
			System.err.println("Student " + Thread.currentThread().getName()+ " stellt sich an Kasse " + currentMinKasse.toString()	+ " als " + currentMinKasse.getSchlangenLength() + "ter");
		kassenLock.release();
		return currentMinKasse;
	}

	public void bezahle(Kasse k) throws InterruptedException {

		// bezahlen
		Random rg = new Random();
		k.acquire();
			sleep(rg.nextInt(Constants.MAX_PAY_TIME));
		k.release();

		// synchronisiere mit Schlangenabfragen
		kassenLock.acquire();
			k.decr();
		kassenLock.release();

	}

	/**
	 * Den Betrieb aufnehmen (Methoden 'oeffnen()' und 'schliessen()')
	 */
	@Override
	public void run() {
		oeffnen();
		try {
			Thread.sleep(Constants.OEFFNUNGS_DAUER);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		schliessen();
	}

	/**
	 * @param args
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException {
		Thread cafete = new Mensa(Constants.NUMBER_STUDENTS,Constants.NUMBER_COUNTERS);
		cafete.start();
	}
}