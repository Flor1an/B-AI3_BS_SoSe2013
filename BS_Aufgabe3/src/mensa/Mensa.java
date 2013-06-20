package mensa;

import java.util.ArrayList;
import java.util.List;

public class Mensa {

    private static int KASSEN_ANZAHL = 3;
    private static int STUDENTEN_ANZAHL = 9;
    private static List<Kasse> kassenListe = new ArrayList<Kasse>();
    private static List<Student> studentenListe = new ArrayList<Student>();

    public static void main(String[] args) throws InterruptedException {
        // Kassen erzeugen
        for (int i = 1; i <= KASSEN_ANZAHL; i++) {
        	Kasse tempKasse = new Kasse("Kasse " + (i));
            kassenListe.add(tempKasse);
        }

        // Studenten erzeugen und beleben
        for (int i = 1; i <= STUDENTEN_ANZAHL; i++) {
        	Student tempStudent = new Student("Student " + (i), kassenListe);
            studentenListe.add(tempStudent);
            tempStudent.start();
        }
        
        
        Thread.sleep(5000);//Gesamtöffnungszeit
     
        // Studenten anhalten
        for (Student student : studentenListe) {
            student.interrupt();
        }


    }


}
