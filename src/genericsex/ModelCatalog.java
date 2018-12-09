
package genericsex;


import java.util.*;


public class ModelCatalog {
public HashMap<Integer,Elev> clasaB = new HashMap<>();
     
   

    }
    

class Elev{
    private static int id;
    private String nume;
    private List<Integer> noteRomana=new ArrayList<>();
    private List<Integer> noteMate=new ArrayList<>();
    private double medieRomana;
    private double medieMate;
    private double medieGenerala;
    private boolean admisRespins=false;
   
    public Elev(String nume) {
        this.nume = nume;
        id++;
    }
   
    @Override
    public String toString() {
        return nume ;
    }
    
    public int getId(){
        return id;
    }
    public String getNume(){
        return nume;
    }
    
    public String getNoteRomana(){
        String afisareNoteRomana="";
        for (Integer i: noteRomana) {
            afisareNoteRomana+="   " + i;
        }
        return afisareNoteRomana;
    }
    
    public String getNoteMate(){
        String afisareNoteMate="";
        for (Integer i: noteMate) {
            afisareNoteMate+="   " + i;
        }
        return afisareNoteMate;
    }
    
    public String getMedieGenerala(){
       
        return Double.toString(medieGenerala);
    }
    
    public String getMedieRomana(){
        return Double.toString(medieRomana);
    }
    public String getMedieMate(){
        return Double.toString(medieMate);
    }
    
    public void addNotaRomana(int nota){
        noteRomana.add(nota);
    }
    public void addNotaMate(int nota){
        noteMate.add(nota);
    }
    
    public void calculeazaMedieRomana(){
        double suma=0;
        int numarNote=noteRomana.size();
        for(Integer i:noteRomana){
            suma+=i;
        }
        medieRomana=suma/numarNote;
    }
    public void calculeazaMedieGenerala(){
        medieGenerala=(medieRomana+medieMate)/2;
        if (medieGenerala>=5) {
            admisRespins=true;
        }
    }
    
    public void calculeazaMedieMate(){
        double suma=0;
        int numarNote=noteMate.size();
        for( Integer i:noteMate){
            suma+=i;
        }
        medieMate=suma/numarNote;
    }
    
    public boolean getAdmisRespins(){
        return admisRespins;
    }
    
    
}

   



