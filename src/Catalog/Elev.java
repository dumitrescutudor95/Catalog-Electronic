package Catalog;

import java.io.Serializable;
import java.util.*;

public class Elev implements Serializable {

    //Datele elevului
    //Nume si prenume
    private String nume;
    private String prenume;

    //Nota purtare
    private int notaPurtare = 10;

    //Numarul de absente
    private int numarAbsente;

    //Liste notelor fiecarei materie in parte
    private List<Integer> noteRomana = new ArrayList<>();
    private List<Integer> noteMate = new ArrayList<>();
    private List<Integer> noteEngleza = new ArrayList<>();
    private List<Integer> noteFranceza = new ArrayList<>();

    //Lista absentelor
    public List<String> listaAbsenteR = new LinkedList<>();
    public List<String> listaAbsenteM = new LinkedList<>();
    public List<String> listaAbsenteE = new LinkedList<>();
    public List<String> listaAbsenteF = new LinkedList<>();

    //Variabile pentru stocarea mediei fiecarei materii in parte + generala
    private double medieRomana;
    private double medieMate;
    private double medieEngleza;
    private double medieFranceza;
    private double medieGenerala;
    //Situatia finala a elevului,care va fi decisa in functie de mediile sale la incheiarea semestrului
    private boolean admisRespins = false;

    //Parolele profesorilor
    private static String parolaRomana = "limbaromana";     //Limba romana
    private static String parolaMate = "matematica";        //Matematica
    private static String parolaEngleza = "limbaengleza";   //Limba engleza
    private static String parolaFranceza = "limbafranceza"; //Limba franceza

//Constructori + toString + getteri
    public Elev(String nume, String prenume) {
        this.nume = nume;
        this.prenume = prenume;

    }

    @Override
    public String toString() {
        return nume + " " + prenume;
    }

    public String getNume() {
        return nume;
    }

    public String getPrenume() {
        return prenume;
    }

    public int getNotaPurtare() {
        return notaPurtare;
    }

    public String getNoteRomana() {
        String afisareNoteRomana = "";
        for (Integer i : noteRomana) {
            afisareNoteRomana += "   " + i;
        }
        return afisareNoteRomana;
    }

    public String getNoteMate() {
        String afisareNoteMate = "";
        for (Integer i : noteMate) {
            afisareNoteMate += "   " + i;
        }
        return afisareNoteMate;
    }

    public String getNoteEngleza() {
        String afisareNoteEngleza = "";
        for (Integer i : noteEngleza) {
            afisareNoteEngleza += "   " + i;
        }
        return afisareNoteEngleza;
    }

    public String getNoteFranceza() {
        String afisareNoteFranceza = "";
        for (Integer i : noteFranceza) {
            afisareNoteFranceza += "   " + i;
        }
        return afisareNoteFranceza;
    }

    public String getMedieGenerala() {

        return Double.toString(medieGenerala);
    }

    public String getMedieRomana() {
        return Double.toString(medieRomana);
    }

    public String getMedieMate() {
        return Double.toString(medieMate);
    }

    public String getMedieEngleza() {
        return Double.toString(medieEngleza);
    }

    public String getMedieFranceza() {
        return Double.toString(medieFranceza);
    }

    //Metode pentru adaugarea notelor
    public void addNotaRomana(int nota) {
        noteRomana.add(nota);
    }

    public void addNotaMate(int nota) {
        noteMate.add(nota);
    }

    public void addNotaEngleza(int nota) {
        noteEngleza.add(nota);
    }

    public void addNotaFranceza(int nota) {
        noteFranceza.add(nota);
    }

    //Metode pentru calcularea mediilor fiecarei materii
    public void calculeazaMedieRomana() {
        double suma = 0;
        int numarNote = noteRomana.size();
        for (Integer i : noteRomana) {
            suma += i;
        }
        medieRomana = suma / numarNote;
    }

    public void calculeazaMedieMate() {
        double suma = 0;
        int numarNote = noteMate.size();
        for (Integer i : noteMate) {
            suma += i;
        }
        medieMate = suma / numarNote;
    }

    public void calculeazaMedieEngleza() {
        double suma = 0;
        int numarNote = noteEngleza.size();
        for (Integer i : noteEngleza) {
            suma += i;
        }
        medieEngleza = suma / numarNote;
    }

    public void calculeazaMedieFranceza() {
        double suma = 0;
        int numarNote = noteFranceza.size();
        for (Integer i : noteFranceza) {
            suma += i;
        }
        medieFranceza = suma / numarNote;
    }

    public void calculeazaMedieGenerala() {

        if (numarAbsente >= 10 && numarAbsente < 20) {
            notaPurtare--;
        } else if (numarAbsente >= 20 && numarAbsente < 30) {
            notaPurtare -= 2;
        } else if (numarAbsente >= 30 && numarAbsente < 40) {
            notaPurtare -= 3;
        } else if (numarAbsente >= 40 && numarAbsente < 50) {
            notaPurtare -= 4;
        }

        medieGenerala = (medieRomana + medieMate + medieEngleza + medieFranceza + notaPurtare) / 5;

        if (medieGenerala >= 5
                && medieRomana >= 5
                && medieMate >= 5
                && medieEngleza >= 5
                && medieFranceza >= 5
                && numarAbsente < 50) {
            admisRespins = true;
        }

    }

    //Aflarea situatiei finale a elevului
    public boolean getAdmisRespins() {
        return admisRespins;
    }

    //Getteri pentru parolele fiecarei materie
    public static String getParolaRomana() {
        return parolaRomana;
    }

    public static String getParolaMate() {
        return parolaMate;
    }

    public static String getParolaEngleza() {
        return parolaEngleza;
    }

    public static String getParolaFranceza() {
        return parolaFranceza;
    }

    public static void setParolaRomana(String parolaRomana) {
        Elev.parolaRomana = parolaRomana;
    }

    public static void setParolaMate(String parolaMate) {
        Elev.parolaMate = parolaMate;
    }

    public static void setParolaEngleza(String parolaEngleza) {
        Elev.parolaEngleza = parolaEngleza;
    }

    public static void setParolaFranceza(String parolaFranceza) {
        Elev.parolaFranceza = parolaFranceza;
    }

    //Metoda pentru aflarea numarului de absente
    public int getNumarAbsente() {
        return numarAbsente;
    }

    //Adaugarea in lista a absentei sub forma de String - data+materie
    public void adaugaAbsentaR(String s) {
        listaAbsenteR.add(s);
    }

    public void adaugaAbsentaM(String s) {
        listaAbsenteM.add(s);
    }

    public void adaugaAbsentaE(String s) {
        listaAbsenteE.add(s);
    }

    public void adaugaAbsentaF(String s) {
        listaAbsenteF.add(s);
    }

//Metoda pentru cresterea numarului absentelor
    public void cresteNumarAbsente() {
        numarAbsente++;
    }

    //Metoda pentru scaderea numarului absentelor(La motivarea acestora)
    public void scadeNumarAbsente() {
        numarAbsente--;
    }

    //Metoda pentru resetarea notelor si absentelpr
    public void resetareDate() {
        noteRomana.clear();
        noteMate.clear();
        noteEngleza.clear();
        noteFranceza.clear();
        listaAbsenteR.clear();
        listaAbsenteM.clear();
        listaAbsenteE.clear();
        listaAbsenteF.clear();
        numarAbsente=0;
    }
}
