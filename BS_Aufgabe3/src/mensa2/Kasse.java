package mensa2;

import java.util.concurrent.Semaphore;

public class Kasse extends Semaphore {

	private static final long serialVersionUID = -6892863064749209179L;
	private int queueLength = 0;
	private String name = null;

	public Kasse(String name) {
		super(Constants.MAX_CUSTOMERS_ALLOWED, true);
		this.name = name;
	}

	public int getSchlangenLength() {
		return queueLength;

	}

	public String toString() {
		return this.name;
	}

	public void incr() {
		//assert (queueLength >= 0);
		++queueLength;
	}

	public void decr() {
		--queueLength;
		//assert (queueLength >= 0);
	}

}