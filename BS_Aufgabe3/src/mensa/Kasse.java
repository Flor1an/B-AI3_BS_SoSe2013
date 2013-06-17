package mensa;

import java.util.concurrent.Semaphore;

public class Kasse {

    private String name;
    private Integer laenge;
    private final Semaphore semaphore;

    Kasse(String name) {
        this.name = name;
        this.laenge = 0;
        this.semaphore = new Semaphore(1, true); //true = fair ^= queue
        
    }

    public String getName() {
        return this.name;
    }

    public Semaphore getSemaphore() {
        return this.semaphore;
    }

    public int getLaenge(){
        return this.laenge;
    }

    public int increaseLaenge(){
        return this.laenge++;
    }

    public int decreaseLaenge(){
        return this.laenge--;
    }

    public void bezahlen(Student student) {
    	decreaseLaenge();
       System.err.println(student.getName() + " bezahlt an " + this.getName());
    }
}
