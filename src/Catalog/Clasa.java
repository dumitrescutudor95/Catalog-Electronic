package Catalog;

//Importuri 
import java.awt.Color;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.*;
import java.io.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import static java.util.stream.Collectors.toList;

//jFrame
public class Clasa extends javax.swing.JFrame {

    //Fereastra de info ce contine detaliile aplicatiei, afisata la apasarea butonului "ALEGE CLASA"
    public info i;

    //Fisiere txt ce contin parolele profesorilor
    File parolaR = new File("parolaRomana.txt");
    File parolaM = new File("parolaMatematica.txt");
    File parolaE = new File("parolaEngleza.txt");
    File parolaF = new File("parolaFranceza.txt");

    // Scannere pentru citirea parolelor din fisier
    //Lista elevilor
    DefaultListModel model = new DefaultListModel();

    //Lista absentelor
    DefaultListModel model2 = new DefaultListModel();

    //Fisierul elevi.txt,in care se vor stoca fiecare elev si datele sale
    File file;

    //Lista elevilor (LinkedList)
    public LinkedList<Elev> clasaElevi = new LinkedList<>();

    //Butonul selectat (folosit la adaugarea absentelor pentru a afla materia)
    public String butonSelectat = "";

    //Constructorul jFrameului
    public Clasa() {

        initComponents();
        jList.setModel(model);
        jListaAbsente.setModel(model2);

        //Ascunderea elementelor vizuale pana la selectarea materiei cu pricina:
        ascundeEngleza();
        ascundeFranceza();
        ascundeMate();
        ascundeRomana();
        jParola.setVisible(false);
        parolaRomana.setVisible(false);
        parolaMate.setVisible(false);
        parolaEngleza.setVisible(false);
        parolaFranceza.setVisible(false);
        labelAbsente.setVisible(false);
        jListaAbsente.setVisible(false);
        bAdaugaAbsenta.setVisible(false);

        //Citirea parolelor din fisiere 
        try {
            Scanner sR = new Scanner(parolaR);
            Scanner sM = new Scanner(parolaM);
            Scanner sE = new Scanner(parolaE);
            Scanner sF = new Scanner(parolaF);
            Elev.setParolaRomana(sR.nextLine());
            Elev.setParolaMate(sM.nextLine());
            Elev.setParolaEngleza(sE.nextLine());
            Elev.setParolaFranceza(sF.nextLine());
            sR.close();
            sM.close();
            sE.close();
            sF.close();
        } catch (FileNotFoundException e) {
        }

        javax.swing.Timer timer = new javax.swing.Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                LocalDateTime ldt = LocalDateTime.now();

                labelTime.setText(String.valueOf(ldt.format(DateTimeFormatter.ofPattern("EEEE d.LLLL.uuu - HH:mm:ss", new Locale("ro", "RO")))));
            }
        });
        timer.start();

    }

    //Metoda de refresh a file'ului la modificarea oricarui element al vreunui elev
    //Se va folosi dupa fiecare adaugare de elev nou si adaugare de nota
    public void rescriereaFilei() {
        try {
            Formatter f = new Formatter(file);
            f.format("");
            FileOutputStream fo = new FileOutputStream(file);
            ObjectOutputStream output = new ObjectOutputStream(fo);
            for (Elev e : clasaElevi) {
                output.writeObject(e);
            }
            fo.close();
            output.close();
        } catch (FileNotFoundException exception) {
        } catch (IOException exception2) {
        }

    }

    //Metoda de initializere a obiectelor Elev,prin citirea lor din fisier
    public void startProgram() {
        int index = 0;  //index folosit pentru a stoca obiectele Elev inapoi in lista elevilor si in lista afisata
        try {
            FileInputStream fi = new FileInputStream(file);
            ObjectInputStream input = new ObjectInputStream(fi);
            try {
                while (true) {                                          //la fiecare obiect nou citit
                    Elev e = (Elev) input.readObject();                 //il vom incapsula 
                    clasaElevi.add(e);
                }
            } catch (EOFException e) {
                System.out.println("S-a incarcat cu succes");
            }
            Collections.sort(clasaElevi, (e1, e2) -> e1.getNume().compareTo(e2.getNume()));
        } catch (FileNotFoundException exc) {

            System.out.println(exc.toString());

        } catch (IOException excIO) {
            System.out.println(excIO.toString());

        } catch (ClassNotFoundException clsNF) {

            System.out.println(clsNF.toString());

        }

        //Ordonarea alfabetica a elevilor si punerea lor in lista
        Collections.sort(clasaElevi, (e1, e2) -> e1.getNume().compareTo(e2.getNume()));
        for (Elev e : clasaElevi) {
            model.addElement(index + 1 + ": " + e.toString());   //il afisam in lista jFrame'ului
            index++;
            //Adaugarea absentelor in tabel in functie de materia selectata
            if (butonSelectat.equals("R")) {
                for (String s : e.listaAbsenteR) {
                    model2.addElement(s);
                }
                index++;
            } else if (butonSelectat.equals("M")) {
                for (String s : e.listaAbsenteM) {
                    model2.addElement(s);
                }
                index++;
            } else if (butonSelectat.equals("E")) {
                for (String s : e.listaAbsenteE) {
                    model2.addElement(s);
                }
                index++;
            } else if (butonSelectat.equals("F")) {
                for (String s : e.listaAbsenteF) {
                    model2.addElement(s);
                }
            }
        }

    }

    //Metoda pentru ascunderea elementelor jFrame caracteristice materiei: limba romana
    private void ascundeRomana() {
        nR.setVisible(false);
        labelRomana.setVisible(false);
        adaugaR.setVisible(false);
        checkRomana.setVisible(false);
        bAdaugaRomana.setVisible(false);
        mR.setVisible(false);
        labelMedieRomana.setVisible(false);
        parolaRomana.setVisible(false);
    }

    //Metoda pentru ascunderea elementelor jFrame caracteristice materiei: matematica
    private void ascundeMate() {
        nM.setVisible(false);
        labelMate.setVisible(false);
        adaugaM.setVisible(false);
        checkMate.setVisible(false);
        bAdaugaMate.setVisible(false);
        mM.setVisible(false);
        labelMedieMate.setVisible(false);
        parolaMate.setVisible(false);
    }

    //Metoda pentru ascunderea elementelor jFrame caracteristice materiei: limba Engleza
    private void ascundeEngleza() {
        nE.setVisible(false);
        labelEngleza.setVisible(false);
        adaugaE.setVisible(false);
        checkEngleza.setVisible(false);
        bAdaugaEngleza.setVisible(false);
        mE.setVisible(false);
        labelMedieEngleza.setVisible(false);
        parolaEngleza.setVisible(false);
    }

    //Metoda pentru ascunderea elementelor jFrame caracteristice materiei: limba franceza
    private void ascundeFranceza() {
        nF.setVisible(false);
        labelFranceza.setVisible(false);
        adaugaF.setVisible(false);
        checkFranceza.setVisible(false);
        bAdaugaFranceza.setVisible(false);
        mF.setVisible(false);
        labelMedieFranceza.setVisible(false);
        parolaFranceza.setVisible(false);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        backgroundWood = new javax.swing.JLabel();
        alegetiClasa = new javax.swing.JButton();
        clasa9B = new javax.swing.JButton();
        clasa9D = new javax.swing.JButton();
        clasa9C = new javax.swing.JButton();
        clasa9A = new javax.swing.JButton();
        selecteazaClasa = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jNume = new javax.swing.JTextField();
        jAdd = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList = new javax.swing.JList<>();
        jLabel7 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        labelNume = new javax.swing.JLabel();
        nR = new javax.swing.JLabel();
        labelRomana = new javax.swing.JLabel();
        adaugaR = new javax.swing.JLabel();
        checkRomana = new javax.swing.JComboBox<>();
        bAdaugaRomana = new javax.swing.JButton();
        mR = new javax.swing.JLabel();
        labelMedieRomana = new javax.swing.JLabel();
        nM = new javax.swing.JLabel();
        labelMate = new javax.swing.JLabel();
        adaugaM = new javax.swing.JLabel();
        checkMate = new javax.swing.JComboBox<>();
        bAdaugaMate = new javax.swing.JButton();
        mM = new javax.swing.JLabel();
        labelMedieMate = new javax.swing.JLabel();
        nE = new javax.swing.JLabel();
        labelEngleza = new javax.swing.JLabel();
        adaugaE = new javax.swing.JLabel();
        checkEngleza = new javax.swing.JComboBox<>();
        bAdaugaEngleza = new javax.swing.JButton();
        mE = new javax.swing.JLabel();
        labelMedieEngleza = new javax.swing.JLabel();
        nF = new javax.swing.JLabel();
        mF = new javax.swing.JLabel();
        labelFranceza = new javax.swing.JLabel();
        labelMedieFranceza = new javax.swing.JLabel();
        adaugaF = new javax.swing.JLabel();
        checkFranceza = new javax.swing.JComboBox<>();
        bAdaugaFranceza = new javax.swing.JButton();
        checkEnd = new javax.swing.JCheckBox();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tabelFinal = new javax.swing.JTable();
        jLabel12 = new javax.swing.JLabel();
        bIncheiereAn = new javax.swing.JToggleButton();
        materieFranceza = new javax.swing.JButton();
        materieEngleza = new javax.swing.JButton();
        materieMate = new javax.swing.JButton();
        materieRomana = new javax.swing.JButton();
        jParola = new javax.swing.JLabel();
        parolaRomana = new javax.swing.JPasswordField();
        parolaMate = new javax.swing.JPasswordField();
        parolaEngleza = new javax.swing.JPasswordField();
        parolaFranceza = new javax.swing.JPasswordField();
        afisareParola = new javax.swing.JLabel();
        jPrenumeLabel = new javax.swing.JLabel();
        jPrenume = new javax.swing.JTextField();
        labelAbsente = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jListaAbsente = new javax.swing.JList<>();
        bAdaugaAbsenta = new javax.swing.JButton();
        bMotiveazaAbsente = new javax.swing.JButton();
        labelTime = new javax.swing.JLabel();
        bStergeElev = new javax.swing.JButton();
        bPreview = new javax.swing.JButton();
        labelBackgroundNote = new javax.swing.JLabel();
        jBackground = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu6 = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        changePasRomana = new javax.swing.JMenuItem();
        changePasMate = new javax.swing.JMenuItem();
        changePasFranceza = new javax.swing.JMenuItem();
        changePasEngleza = new javax.swing.JMenuItem();
        jMenu7 = new javax.swing.JMenu();
        jInfo = new javax.swing.JMenuItem();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(jTable1);

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane3.setViewportView(jTable2);

        backgroundWood.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Catalog/wood-889x593.jpg"))); // NOI18N

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        alegetiClasa.setBackground(new java.awt.Color(0, 0, 0));
        alegetiClasa.setFont(new java.awt.Font("Dialog", 0, 36)); // NOI18N
        alegetiClasa.setText("Alegeti clasa");
        alegetiClasa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                alegetiClasaActionPerformed(evt);
            }
        });
        getContentPane().add(alegetiClasa, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 540, 650, 50));

        clasa9B.setBackground(new java.awt.Color(0, 0, 0));
        clasa9B.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        clasa9B.setText("Clasa 9B");
        clasa9B.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clasa9BActionPerformed(evt);
            }
        });
        getContentPane().add(clasa9B, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 470, 170, 70));

        clasa9D.setBackground(new java.awt.Color(0, 0, 0));
        clasa9D.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        clasa9D.setText("Clasa 9D");
        clasa9D.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clasa9DActionPerformed(evt);
            }
        });
        getContentPane().add(clasa9D, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 470, 160, 70));

        clasa9C.setBackground(new java.awt.Color(0, 0, 0));
        clasa9C.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        clasa9C.setText("Clasa 9C");
        clasa9C.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clasa9CActionPerformed(evt);
            }
        });
        getContentPane().add(clasa9C, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 470, 160, 70));

        clasa9A.setBackground(new java.awt.Color(0, 0, 0));
        clasa9A.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        clasa9A.setText("Clasa 9A");
        clasa9A.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clasa9AActionPerformed(evt);
            }
        });
        getContentPane().add(clasa9A, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 470, 160, 70));

        selecteazaClasa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Catalog/1200px-Lincoln_Park_High_School.jpg"))); // NOI18N
        getContentPane().add(selecteazaClasa, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 650, 590));

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(204, 51, 0));
        jLabel1.setText("Adauga elev");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 30, -1, -1));

        jLabel2.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(153, 51, 0));
        jLabel2.setText("Nume:");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 70, -1, -1));
        getContentPane().add(jNume, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 60, 141, 28));

        jAdd.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jAdd.setForeground(new java.awt.Color(153, 51, 0));
        jAdd.setText("Adauga elev");
        jAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jAddActionPerformed(evt);
            }
        });
        getContentPane().add(jAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 130, -1, -1));

        jList.setBackground(new java.awt.Color(153, 51, 0));
        jList.setForeground(new java.awt.Color(0, 0, 0));
        jList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jListMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jList);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 60, 290, 106));

        jLabel7.setFont(new java.awt.Font("Dialog", 3, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(51, 0, 0));
        jLabel7.setText("Date elev:");
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 220, -1, -1));

        jLabel3.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(51, 0, 0));
        jLabel3.setText("Nume:");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 250, -1, -1));

        labelNume.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        labelNume.setForeground(new java.awt.Color(51, 0, 0));
        getContentPane().add(labelNume, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 250, 177, 15));

        nR.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        nR.setForeground(new java.awt.Color(51, 0, 0));
        nR.setText("Note Romana:");
        getContentPane().add(nR, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 290, 100, 20));

        labelRomana.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        labelRomana.setForeground(new java.awt.Color(51, 0, 0));
        getContentPane().add(labelRomana, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 290, 200, 20));

        adaugaR.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        adaugaR.setForeground(new java.awt.Color(51, 0, 0));
        adaugaR.setText("Adauga:");
        getContentPane().add(adaugaR, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 360, -1, -1));

        checkRomana.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        checkRomana.setForeground(new java.awt.Color(51, 0, 0));
        checkRomana.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" }));
        getContentPane().add(checkRomana, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 360, -1, -1));

        bAdaugaRomana.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        bAdaugaRomana.setForeground(new java.awt.Color(51, 0, 0));
        bAdaugaRomana.setText("Adauga");
        bAdaugaRomana.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bAdaugaRomanaActionPerformed(evt);
            }
        });
        getContentPane().add(bAdaugaRomana, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 360, -1, -1));

        mR.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        mR.setForeground(new java.awt.Color(51, 0, 0));
        mR.setText("Medie:");
        getContentPane().add(mR, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 320, 60, 20));

        labelMedieRomana.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        labelMedieRomana.setForeground(new java.awt.Color(51, 0, 0));
        getContentPane().add(labelMedieRomana, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 320, 50, 20));

        nM.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        nM.setForeground(new java.awt.Color(51, 0, 0));
        nM.setText("Note Mate:");
        getContentPane().add(nM, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 290, 80, 20));

        labelMate.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        labelMate.setForeground(new java.awt.Color(51, 0, 0));
        getContentPane().add(labelMate, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 290, 200, 20));

        adaugaM.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        adaugaM.setForeground(new java.awt.Color(51, 0, 0));
        adaugaM.setText("Adauga:");
        getContentPane().add(adaugaM, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 360, -1, -1));

        checkMate.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        checkMate.setForeground(new java.awt.Color(51, 0, 0));
        checkMate.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" }));
        getContentPane().add(checkMate, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 360, -1, -1));

        bAdaugaMate.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        bAdaugaMate.setForeground(new java.awt.Color(51, 0, 0));
        bAdaugaMate.setText("Adauga");
        bAdaugaMate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bAdaugaMateActionPerformed(evt);
            }
        });
        getContentPane().add(bAdaugaMate, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 360, -1, -1));

        mM.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        mM.setForeground(new java.awt.Color(51, 0, 0));
        mM.setText("Medie:");
        getContentPane().add(mM, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 320, 60, 20));

        labelMedieMate.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        labelMedieMate.setForeground(new java.awt.Color(51, 0, 0));
        getContentPane().add(labelMedieMate, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 320, 50, 20));

        nE.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        nE.setForeground(new java.awt.Color(51, 0, 0));
        nE.setText("Note Engleza:");
        getContentPane().add(nE, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 290, 100, 20));

        labelEngleza.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        labelEngleza.setForeground(new java.awt.Color(51, 0, 0));
        getContentPane().add(labelEngleza, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 290, 200, 20));

        adaugaE.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        adaugaE.setForeground(new java.awt.Color(51, 0, 0));
        adaugaE.setText("Adauga:");
        getContentPane().add(adaugaE, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 360, -1, -1));

        checkEngleza.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        checkEngleza.setForeground(new java.awt.Color(51, 0, 0));
        checkEngleza.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" }));
        getContentPane().add(checkEngleza, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 360, -1, -1));

        bAdaugaEngleza.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        bAdaugaEngleza.setForeground(new java.awt.Color(51, 0, 0));
        bAdaugaEngleza.setText("Adauga");
        bAdaugaEngleza.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bAdaugaEnglezaActionPerformed(evt);
            }
        });
        getContentPane().add(bAdaugaEngleza, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 360, -1, -1));

        mE.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        mE.setForeground(new java.awt.Color(51, 0, 0));
        mE.setText("Medie:");
        getContentPane().add(mE, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 320, 60, 20));

        labelMedieEngleza.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        labelMedieEngleza.setForeground(new java.awt.Color(51, 0, 0));
        getContentPane().add(labelMedieEngleza, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 320, 60, 20));

        nF.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        nF.setForeground(new java.awt.Color(51, 0, 0));
        nF.setText("Note Franceza:");
        getContentPane().add(nF, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 290, 110, 20));

        mF.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        mF.setForeground(new java.awt.Color(51, 0, 0));
        mF.setText("Medie:");
        getContentPane().add(mF, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 320, 60, 20));

        labelFranceza.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        labelFranceza.setForeground(new java.awt.Color(51, 0, 0));
        getContentPane().add(labelFranceza, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 290, 200, 20));

        labelMedieFranceza.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        labelMedieFranceza.setForeground(new java.awt.Color(51, 0, 0));
        getContentPane().add(labelMedieFranceza, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 320, 60, 20));

        adaugaF.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        adaugaF.setForeground(new java.awt.Color(51, 0, 0));
        adaugaF.setText("Adauga:");
        getContentPane().add(adaugaF, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 360, -1, -1));

        checkFranceza.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        checkFranceza.setForeground(new java.awt.Color(51, 0, 0));
        checkFranceza.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" }));
        getContentPane().add(checkFranceza, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 360, -1, -1));

        bAdaugaFranceza.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        bAdaugaFranceza.setForeground(new java.awt.Color(51, 0, 0));
        bAdaugaFranceza.setText("Adauga");
        bAdaugaFranceza.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bAdaugaFrancezaActionPerformed(evt);
            }
        });
        getContentPane().add(bAdaugaFranceza, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 360, -1, -1));

        checkEnd.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        checkEnd.setForeground(new java.awt.Color(153, 51, 0));
        checkEnd.setText("Da");
        checkEnd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkEndActionPerformed(evt);
            }
        });
        getContentPane().add(checkEnd, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 500, -1, -1));

        jLabel6.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(153, 51, 0));
        jLabel6.setText("Doresti sa inchei anul?");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 480, -1, -1));

        tabelFinal.setBackground(new java.awt.Color(153, 51, 0));
        tabelFinal.setForeground(new java.awt.Color(0, 0, 0));
        tabelFinal.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Nume", "Medie generala", "Numar Absente", "Promovat"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane4.setViewportView(tabelFinal);

        getContentPane().add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 490, -1, 104));

        jLabel12.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(204, 51, 0));
        jLabel12.setText("Selecteaza Elevul");
        getContentPane().add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 30, -1, -1));

        bIncheiereAn.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        bIncheiereAn.setForeground(new java.awt.Color(153, 51, 0));
        bIncheiereAn.setText("Incheiere an");
        bIncheiereAn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bIncheiereAnActionPerformed(evt);
            }
        });
        getContentPane().add(bIncheiereAn, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 560, -1, -1));

        materieFranceza.setForeground(new java.awt.Color(51, 0, 0));
        materieFranceza.setText("Franceza");
        materieFranceza.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                materieFrancezaActionPerformed(evt);
            }
        });
        getContentPane().add(materieFranceza, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 250, 130, -1));

        materieEngleza.setForeground(new java.awt.Color(51, 0, 0));
        materieEngleza.setText("Engleza");
        materieEngleza.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                materieEnglezaActionPerformed(evt);
            }
        });
        getContentPane().add(materieEngleza, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 250, 130, -1));

        materieMate.setForeground(new java.awt.Color(51, 0, 0));
        materieMate.setText("Matematica");
        materieMate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                materieMateActionPerformed(evt);
            }
        });
        getContentPane().add(materieMate, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 220, 130, -1));

        materieRomana.setForeground(new java.awt.Color(51, 0, 0));
        materieRomana.setText("Romana");
        materieRomana.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                materieRomanaActionPerformed(evt);
            }
        });
        getContentPane().add(materieRomana, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 220, 130, -1));

        jParola.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jParola.setForeground(new java.awt.Color(51, 0, 0));
        jParola.setText("Parola:");
        getContentPane().add(jParola, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 410, -1, -1));

        parolaRomana.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        parolaRomana.setForeground(new java.awt.Color(51, 0, 0));
        getContentPane().add(parolaRomana, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 410, 130, 20));

        parolaMate.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        parolaMate.setForeground(new java.awt.Color(51, 0, 0));
        getContentPane().add(parolaMate, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 410, 130, 20));

        parolaEngleza.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        parolaEngleza.setForeground(new java.awt.Color(51, 0, 0));
        getContentPane().add(parolaEngleza, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 410, 130, 20));

        parolaFranceza.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        parolaFranceza.setForeground(new java.awt.Color(51, 0, 0));
        getContentPane().add(parolaFranceza, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 410, 130, 20));

        afisareParola.setForeground(new java.awt.Color(51, 0, 0));
        getContentPane().add(afisareParola, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 440, 190, 20));

        jPrenumeLabel.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jPrenumeLabel.setForeground(new java.awt.Color(153, 51, 0));
        jPrenumeLabel.setText("Prenume:");
        getContentPane().add(jPrenumeLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, -1, -1));
        getContentPane().add(jPrenume, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 90, 140, 30));

        labelAbsente.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        labelAbsente.setForeground(new java.awt.Color(51, 0, 0));
        labelAbsente.setText("Absente");
        getContentPane().add(labelAbsente, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 300, -1, -1));

        jListaAbsente.setBackground(new java.awt.Color(153, 51, 0));
        jListaAbsente.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jListaAbsente.setForeground(new java.awt.Color(51, 0, 0));
        jListaAbsente.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                jListaAbsenteAncestorAdded(evt);
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        jScrollPane5.setViewportView(jListaAbsente);

        getContentPane().add(jScrollPane5, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 320, 290, 100));

        bAdaugaAbsenta.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        bAdaugaAbsenta.setForeground(new java.awt.Color(51, 0, 0));
        bAdaugaAbsenta.setText("Adauga absenta");
        bAdaugaAbsenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bAdaugaAbsentaActionPerformed(evt);
            }
        });
        getContentPane().add(bAdaugaAbsenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 430, 170, -1));

        bMotiveazaAbsente.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        bMotiveazaAbsente.setForeground(new java.awt.Color(51, 0, 0));
        bMotiveazaAbsente.setText("Motiveaza Absenta");
        bMotiveazaAbsente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bMotiveazaAbsenteActionPerformed(evt);
            }
        });
        getContentPane().add(bMotiveazaAbsente, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 430, -1, -1));

        labelTime.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        labelTime.setForeground(new java.awt.Color(204, 51, 0));
        getContentPane().add(labelTime, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 10, 290, 20));

        bStergeElev.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        bStergeElev.setForeground(new java.awt.Color(153, 51, 0));
        bStergeElev.setText("Sterge Elev");
        bStergeElev.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bStergeElevActionPerformed(evt);
            }
        });
        getContentPane().add(bStergeElev, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 170, 130, -1));

        bPreview.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
        bPreview.setForeground(new java.awt.Color(153, 51, 0));
        bPreview.setText("Preview");
        bPreview.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bPreviewActionPerformed(evt);
            }
        });
        getContentPane().add(bPreview, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 530, -1, -1));

        labelBackgroundNote.setForeground(new java.awt.Color(51, 0, 0));
        labelBackgroundNote.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Catalog/woods-600x385.jpg"))); // NOI18N
        labelBackgroundNote.setText("jLabel4");
        getContentPane().add(labelBackgroundNote, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 210, 600, 260));

        jBackground.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Catalog/wood-889x593.jpg"))); // NOI18N
        jBackground.setText("jLabel4");
        getContentPane().add(jBackground, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 650, 600));

        jMenu6.setText("Inapoi");
        jMenu6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenu6ActionPerformed(evt);
            }
        });

        jMenuItem2.setText("Inapoi la selectia claselor");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu6.add(jMenuItem2);

        jMenuBar1.add(jMenu6);

        jMenu2.setText("Schimba parola profesorului");
        jMenu2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenu2ActionPerformed(evt);
            }
        });

        changePasRomana.setText("Limba romana");
        changePasRomana.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                changePasRomanaActionPerformed(evt);
            }
        });
        jMenu2.add(changePasRomana);

        changePasMate.setText("Matematica");
        changePasMate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                changePasMateActionPerformed(evt);
            }
        });
        jMenu2.add(changePasMate);

        changePasFranceza.setText("Limba franceza");
        changePasFranceza.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                changePasFrancezaActionPerformed(evt);
            }
        });
        jMenu2.add(changePasFranceza);

        changePasEngleza.setText("Limba engleza");
        changePasEngleza.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                changePasEnglezaActionPerformed(evt);
            }
        });
        jMenu2.add(changePasEngleza);

        jMenuBar1.add(jMenu2);

        jMenu7.setText("READ ME!");

        jInfo.setText("Info");
        jInfo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jInfoActionPerformed(evt);
            }
        });
        jMenu7.add(jInfo);

        jMenuBar1.add(jMenu7);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

//Apasarea butonului de adaugare Elev

    private void jAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jAddActionPerformed

        Elev e = new Elev(jNume.getText(), jPrenume.getText());
        clasaElevi.add(e);

        model.addElement(clasaElevi.size() + "." + e.toString());

        jNume.setText("");
        jPrenume.setText("");

        rescriereaFilei();
    }//GEN-LAST:event_jAddActionPerformed

    //Metoda pentru adaugarea notelor la Matematica (la apasarea butonului)

    private void bAdaugaMateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bAdaugaMateActionPerformed
        if (Elev.getParolaMate().equals(parolaMate.getText())) {  //verificarea parolei

            //Stocarea elevului din lista intr-o variabila temporara
            int index = jList.getSelectedIndex();
            Elev temp = clasaElevi.get(index);
            clasaElevi.remove(index);

            int nota = checkMate.getSelectedIndex() + 1;

            //Adaugarea si afisarea notei
            temp.addNotaMate(nota);
            labelMate.setText(temp.getNoteMate());
            checkMate.setSelectedIndex(0);

            //Calcularea si afisarea mediei
            temp.calculeazaMedieMate();
            labelMedieMate.setText(temp.getMedieMate());
            afisareParola.setText("");
            parolaMate.setText("");

            //Reintroducerea elevului in lista(cu nota adaugata)
            clasaElevi.add(index, temp);

        } else {
            afisareParola.setText("PAROLA INCORECTA !");
        }

        rescriereaFilei();  //refresh

    }//GEN-LAST:event_bAdaugaMateActionPerformed

    //Metoda pentru adaugarea notelor la Limba Romana (la apasarea butonului)

    private void bAdaugaRomanaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bAdaugaRomanaActionPerformed
        //Stocarea elevului din lista intr-o variabila temporara
        int index = jList.getSelectedIndex();
        Elev temp = clasaElevi.get(index);
        clasaElevi.remove(index);
        int nota = checkRomana.getSelectedIndex() + 1;

        if (Elev.getParolaRomana().equals(parolaRomana.getText())) {  //verificarea parolei

            //Adaugarea si afisarea notei
            temp.addNotaRomana(nota);
            labelRomana.setText(temp.getNoteRomana());
            checkRomana.setSelectedIndex(0);

            //Calcularea si afisarea mediei
            temp.calculeazaMedieRomana();
            labelMedieRomana.setText(temp.getMedieRomana());

            afisareParola.setText("");
            parolaRomana.setText("");

            //Reintroducerea elevului in lista(cu nota adaugata)
            clasaElevi.add(index, temp);

        } else {
            afisareParola.setText("PAROLA INCORECTA !");
        }

        rescriereaFilei();


    }//GEN-LAST:event_bAdaugaRomanaActionPerformed

    //Metoda pentru selectarea + afisarea situatiei actuale a elevilor din lista.

    private void jListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jListMouseClicked
        //Incapsularea elevului selectat intr o referinta temporara
        int index = jList.getSelectedIndex();
        Elev temp = clasaElevi.get(index);

        //AFISARE :
        //Numele
        labelNume.setText(temp.getNume() + " " + temp.getPrenume());

        //Notele materiilor
        labelRomana.setText(temp.getNoteRomana());
        labelMate.setText(temp.getNoteMate());
        labelEngleza.setText(temp.getNoteEngleza());
        labelFranceza.setText(temp.getNoteFranceza());

        //Mediile materiilor
        labelMedieRomana.setText(temp.getMedieRomana());
        labelMedieMate.setText(temp.getMedieMate());
        labelMedieEngleza.setText(temp.getMedieEngleza());
        labelMedieFranceza.setText(temp.getMedieFranceza());

        //Afisarea optiunii absentelor
        labelAbsente.setVisible(true);
        jListaAbsente.setVisible(true);
        bAdaugaAbsenta.setVisible(true);

        //Afisarea absentelor in functie de materie selectata
        model2.removeAllElements();
        if (butonSelectat.equals("R")) {
            for (String s : temp.listaAbsenteR) {
                model2.addElement(s);
            }
        } else if (butonSelectat.equals("M")) {
            for (String s : temp.listaAbsenteM) {
                model2.addElement(s);
            }
        } else if (butonSelectat.equals("E")) {
            for (String s : temp.listaAbsenteE) {
                model2.addElement(s);
            }
        } else if (butonSelectat.equals("F")) {
            for (String s : temp.listaAbsenteF) {
                model2.addElement(s);
            }
        }

    }//GEN-LAST:event_jListMouseClicked

    // ?

    private void checkEndActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkEndActionPerformed

    }//GEN-LAST:event_checkEndActionPerformed

    //Metoda pentru incarcarea tabelului final
    public void incarcaTabel(){
        DefaultTableModel model = (DefaultTableModel) tabelFinal.getModel();
            
            //Golirea tabeluli actual
            model.getDataVector().removeAllElements();
            int id = 1;

            for (Elev e : clasaElevi) {

                String nume = e.getNume();
                String prenume = e.getPrenume();
                e.calculeazaMedieGenerala();
                String medieGen = e.getMedieGenerala();
                int numarAbsente = e.getNumarAbsente();
                Boolean adRe = e.getAdmisRespins();

                model.addRow(new Object[]{id, nume + " " + prenume, medieGen, numarAbsente, adRe});

                id++;
            } 
    }
    //Metoda pentru incheiarea anului:afisarea in tabelul final a fiecarui elev + situatia sa

    private void bIncheiereAnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bIncheiereAnActionPerformed

        if (checkEnd.isSelected()) {
            int i=JOptionPane.showConfirmDialog(null,"ATENTIE! Odata cu incheierea anului,toti elevii car nu au promovat vor fi exclusi din clasa.\nCei care au promovat vor trece in anul urmator\nSunteti sigur ca doriti sa incheiati anul?","Sunteti sigur?", JOptionPane.YES_NO_OPTION);
            if (i==JOptionPane.YES_OPTION) {
                
           incarcaTabel(); // incarca tabelul 
           
            //Incapsularea elevilor promovati intr o lista temporara
            List<Elev> temp=clasaElevi.stream().filter(e->e.getAdmisRespins()).collect(toList());
            //ale carei elemente va inlocui elementele din lista clasei
            clasaElevi.clear();
                for (Elev e:temp) {
                 e.resetareDate();
                 clasaElevi.add(e);
                }
                rescriereaFilei();
              
            }
           
        }
    }//GEN-LAST:event_bIncheiereAnActionPerformed

    //Metoda pentru adaugarea notelor la Limba Engleza (la apasarea butonului)

    private void bAdaugaEnglezaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bAdaugaEnglezaActionPerformed
        if (Elev.getParolaEngleza().equals(parolaEngleza.getText())) {  //verificarea parolei

            //Stocarea elevului din lista intr-o variabila temporara
            int index = jList.getSelectedIndex();
            Elev temp = clasaElevi.get(index);
            clasaElevi.remove(index);

            //Adaugarea si afisarea notei
            int nota = checkEngleza.getSelectedIndex() + 1;
            temp.addNotaEngleza(nota);
            labelEngleza.setText(temp.getNoteEngleza());
            checkEngleza.setSelectedIndex(0);

            //Calcularea si afisarea mediei
            temp.calculeazaMedieEngleza();
            labelMedieEngleza.setText(temp.getMedieEngleza());

            afisareParola.setText("");
            parolaEngleza.setText("");

            //Reintroducerea elevului in lista(cu nota adaugata)
            clasaElevi.add(index, temp);

        } else {
            afisareParola.setText("PAROLA INCORECTA !");
        }

        rescriereaFilei();  //refresh
    }//GEN-LAST:event_bAdaugaEnglezaActionPerformed

    //Metoda pentru adaugarea notelor la Limba Franceza (la apasarea butonului)

    private void bAdaugaFrancezaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bAdaugaFrancezaActionPerformed

        if (Elev.getParolaFranceza().equals(parolaFranceza.getText())) {  //verificarea parolei

            //Stocarea elevului din lista intr-o variabila temporara
            int index = jList.getSelectedIndex();
            Elev temp = clasaElevi.get(index);
            clasaElevi.remove(index);

            int nota = checkFranceza.getSelectedIndex() + 1;

            //Adaugarea si afisarea notei
            temp.addNotaFranceza(nota);
            labelFranceza.setText(temp.getNoteFranceza());
            checkFranceza.setSelectedIndex(0);

            //Calcularea si afisarea mediei
            temp.calculeazaMedieFranceza();
            labelMedieFranceza.setText(temp.getMedieFranceza());

            afisareParola.setText("");
            parolaFranceza.setText("");

            //Reintroducerea elevului in lista(cu nota adaugata)
            clasaElevi.add(index, temp);

        } else {
            afisareParola.setText("PAROLA INCORECTA !");
        }

        rescriereaFilei(); //refresh
    }//GEN-LAST:event_bAdaugaFrancezaActionPerformed

    //Metoda folosita la apasarea butonului pentru afisarea elementelor materiei:Romana

    private void materieRomanaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_materieRomanaActionPerformed
        butonSelectat = "R";

        materieRomana.setBackground(new Color(153, 51, 0));
        materieMate.setBackground(Color.gray);
        materieEngleza.setBackground(Color.gray);
        materieFranceza.setBackground(Color.gray);

        //Afisarea elementelor limbii romane
        jParola.setVisible(true);
        parolaRomana.setVisible(true);
        nR.setVisible(true);
        labelRomana.setVisible(true);
        adaugaR.setVisible(true);
        checkRomana.setVisible(true);
        bAdaugaRomana.setVisible(true);
        mR.setVisible(true);
        labelMedieRomana.setVisible(true);

        //Ascunderea restului de elemente
        ascundeMate();
        ascundeEngleza();
        ascundeFranceza();

        model2.clear();

        //Stocarea elevului din lista intr-o variabila temporara
        int index = jList.getSelectedIndex();
        Elev temp = clasaElevi.get(index);

        //Adaugarea absentelor in tabel
        model2.clear();
        for (String s : temp.listaAbsenteR) {
            model2.addElement(s);
        }

    }//GEN-LAST:event_materieRomanaActionPerformed

    //Metoda folosita la apasarea butonului pentru afisarea elementelor materiei:Matematica

    private void materieMateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_materieMateActionPerformed
        butonSelectat = "M";

        materieRomana.setBackground(Color.gray);
        materieMate.setBackground(new Color(153, 51, 0));
        materieEngleza.setBackground(Color.gray);
        materieFranceza.setBackground(Color.gray);

        //Afisarea elementelor matematicii
        jParola.setVisible(true);
        parolaMate.setVisible(true);
        nM.setVisible(true);
        labelMate.setVisible(true);
        adaugaM.setVisible(true);
        checkMate.setVisible(true);
        bAdaugaMate.setVisible(true);
        mM.setVisible(true);
        labelMedieMate.setVisible(true);

        //Ascunderea restului de elemente
        ascundeRomana();
        ascundeEngleza();
        ascundeFranceza();

        //Stocarea elevului din lista intr-o variabila temporara
        int index = jList.getSelectedIndex();
        Elev temp = clasaElevi.get(index);

        //Adaugarea absentelor in tabel
        model2.clear();
        for (String s : temp.listaAbsenteM) {
            model2.addElement(s);
        }
    }//GEN-LAST:event_materieMateActionPerformed

    //Metoda folosita la apasarea butonului pentru afisarea elementelor materiei:Engleza

    private void materieEnglezaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_materieEnglezaActionPerformed
        butonSelectat = "E";

        materieRomana.setBackground(Color.gray);
        materieMate.setBackground(Color.gray);
        materieEngleza.setBackground(new Color(153, 51, 0));
        materieFranceza.setBackground(Color.gray);

        //Afisarea elementelor limbii engleze
        jParola.setVisible(true);
        parolaEngleza.setVisible(true);
        nE.setVisible(true);
        labelEngleza.setVisible(true);
        adaugaE.setVisible(true);
        checkEngleza.setVisible(true);
        bAdaugaEngleza.setVisible(true);
        mE.setVisible(true);
        labelMedieEngleza.setVisible(true);

        //Ascunderea restului de elemente
        ascundeRomana();
        ascundeFranceza();
        ascundeMate();

        //Stocarea elevului din lista intr-o variabila temporara
        int index = jList.getSelectedIndex();
        Elev temp = clasaElevi.get(index);

        //Adaugarea absentelor in tabel
        model2.clear();
        for (String s : temp.listaAbsenteE) {
            model2.addElement(s);
        }
    }//GEN-LAST:event_materieEnglezaActionPerformed

    //Metoda folosita la apasarea butonului pentru afisarea elementelor materiei:Franceza

    private void materieFrancezaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_materieFrancezaActionPerformed
        butonSelectat = "F";

        materieRomana.setBackground(Color.gray);
        materieMate.setBackground(Color.gray);
        materieEngleza.setBackground(Color.gray);
        materieFranceza.setBackground(new Color(153, 51, 0));

        //Afisarea elementelor francezii
        jParola.setVisible(true);
        parolaFranceza.setVisible(true);
        nF.setVisible(true);
        labelFranceza.setVisible(true);
        adaugaF.setVisible(true);
        checkFranceza.setVisible(true);
        bAdaugaFranceza.setVisible(true);
        mF.setVisible(true);
        labelMedieFranceza.setVisible(true);

        //Ascunderea restului de elemente
        ascundeEngleza();
        ascundeMate();
        ascundeRomana();

        //Stocarea elevului din lista intr-o variabila temporara
        int index = jList.getSelectedIndex();
        Elev temp = clasaElevi.get(index);

        //Adaugarea absentelor in tabel
        model2.clear();
        for (String s : temp.listaAbsenteF) {
            model2.addElement(s);
        }
    }//GEN-LAST:event_materieFrancezaActionPerformed

    //Metoda pentru schimbarea parolei la Limba romana

    private void changePasRomanaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_changePasRomanaActionPerformed

        String parolaActuala = JOptionPane.showInputDialog("Introduceti parola actuala:", JOptionPane.PLAIN_MESSAGE);
        if (parolaActuala.equals(Elev.getParolaRomana())) {
            String parolaNoua = JOptionPane.showInputDialog("Introduceti noua parola");
            int x = JOptionPane.showConfirmDialog(null, "Sunteti sigur?", "Sigur doriti sa schimbati parola?", JOptionPane.YES_NO_OPTION);
            //Scrierea in fisier a noii parole
            if (x == JOptionPane.YES_OPTION) {
                try {
                    Formatter fR = new Formatter(parolaR);
                    fR.format("");
                    fR.close();
                    PrintWriter pwR = new PrintWriter(parolaR);
                    pwR.println(parolaNoua);
                    pwR.close();
                } catch (FileNotFoundException e) {
                }
                Elev.setParolaRomana(parolaNoua);
            }
        }
    }//GEN-LAST:event_changePasRomanaActionPerformed

    //Metoda pentru schimbarea parolei la Matematica

    private void changePasMateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_changePasMateActionPerformed

        String parolaActuala = JOptionPane.showInputDialog("Introduceti parola actuala:", JOptionPane.PLAIN_MESSAGE);
        if (parolaActuala.equals(Elev.getParolaMate())) {
            String parolaNoua = JOptionPane.showInputDialog("Introduceti noua parola");
            int x = JOptionPane.showConfirmDialog(null, "Sunteti sigur?", "Sigur doriti sa schimbati parola?", JOptionPane.YES_NO_OPTION);
            //Scrierea in fisier a noii parole
            if (x == JOptionPane.YES_OPTION) {
                try {
                    Formatter fM = new Formatter(parolaM);
                    fM.format("");
                    fM.close();
                    PrintWriter pwM = new PrintWriter(parolaM);
                    pwM.println(parolaNoua);
                    pwM.close();
                } catch (FileNotFoundException e) {
                }
                Elev.setParolaMate(parolaNoua);
            }
        }
    }//GEN-LAST:event_changePasMateActionPerformed

    //Metoda pentru schimbarea parolei la Limba Franceza

    private void changePasFrancezaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_changePasFrancezaActionPerformed

        String parolaActuala = JOptionPane.showInputDialog("Introduceti parola actuala:", JOptionPane.PLAIN_MESSAGE);
        if (parolaActuala.equals(Elev.getParolaFranceza())) {
            String parolaNoua = JOptionPane.showInputDialog("Introduceti noua parola");
            int x = JOptionPane.showConfirmDialog(null, "Sunteti sigur?", "Sigur doriti sa schimbati parola?", JOptionPane.YES_NO_OPTION);
            //Scrierea in fisier a noii parole
            if (x == JOptionPane.YES_OPTION) {
                try {
                    Formatter fF = new Formatter(parolaR);
                    fF.format("");
                    fF.close();
                    PrintWriter pwF = new PrintWriter(parolaF);
                    pwF.println(parolaNoua);
                    pwF.close();
                } catch (FileNotFoundException e) {
                }
                Elev.setParolaFranceza(parolaNoua);
            }
        }
    }//GEN-LAST:event_changePasFrancezaActionPerformed

    //Metoda pentru schimbarea parolei la Limba Engleza 

    private void changePasEnglezaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_changePasEnglezaActionPerformed

        String parolaActuala = JOptionPane.showInputDialog("Introduceti parola actuala:", JOptionPane.PLAIN_MESSAGE);
        if (parolaActuala.equals(Elev.getParolaEngleza())) {
            String parolaNoua = JOptionPane.showInputDialog("Introduceti noua parola");
            int x = JOptionPane.showConfirmDialog(null, "Sunteti sigur?", "Sigur doriti sa schimbati parola?", JOptionPane.YES_NO_OPTION);
            //Scrierea in fisier a noii parole
            if (x == JOptionPane.YES_OPTION) {
                try {
                    Formatter fE = new Formatter(parolaR);
                    fE.format("");
                    fE.close();
                    PrintWriter pwE = new PrintWriter(parolaE);
                    pwE.println(parolaNoua);
                    pwE.close();
                } catch (FileNotFoundException e) {
                }
                Elev.setParolaEngleza(parolaNoua);
            }
        }
    }//GEN-LAST:event_changePasEnglezaActionPerformed

    //Metoda pentru adaugarea absentelor la apasarea butonului

    private void bAdaugaAbsentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bAdaugaAbsentaActionPerformed

        //Stocarea elevului din lista intr-o variabila temporara
        int index = jList.getSelectedIndex();
        Elev temp = clasaElevi.get(index);
        clasaElevi.remove(index);

        temp.cresteNumarAbsente();

        //Stabilirea datii curente si adaugarea ei in lista absentelor 
        LocalDate ld = LocalDate.now();

        switch (butonSelectat) { //Stabilirea materiei selectate odata ce butonul va fi apasat
            case "R":
                temp.adaugaAbsentaR(ld.format(DateTimeFormatter.ofPattern("EEEE dd.MMM", new Locale("ro", "RO"))).toString());
                model2.addElement(ld.format(DateTimeFormatter.ofPattern("EEEE dd.MMM", new Locale("ro", "RO"))).toString());
                break;
            case "M":
                temp.adaugaAbsentaM(ld.format(DateTimeFormatter.ofPattern("EEEE dd.MMM", new Locale("ro", "RO"))).toString());
                model2.addElement(ld.format(DateTimeFormatter.ofPattern("EEEE dd.MMM", new Locale("ro", "RO"))).toString());

                break;
            case "E":
                temp.adaugaAbsentaE(ld.format(DateTimeFormatter.ofPattern("EEEE dd.MMM", new Locale("ro", "RO"))).toString());
                model2.addElement(ld.format(DateTimeFormatter.ofPattern("EEEE dd.MMM", new Locale("ro", "RO"))).toString());

                break;
            case "F":
                temp.adaugaAbsentaF(ld.format(DateTimeFormatter.ofPattern("EEEE dd.MMM", new Locale("ro", "RO"))).toString());
                model2.addElement(ld.format(DateTimeFormatter.ofPattern("EEEE dd.MMM", new Locale("ro", "RO"))).toString());

                break;
            default:
                System.out.println("Nici o materie selectata");
                ;
        }

        //Reintroducerea in lista a elevului,cu absenta adaugata
        clasaElevi.add(index, temp);

        rescriereaFilei(); //refresh


    }//GEN-LAST:event_bAdaugaAbsentaActionPerformed

    //Metoda pentru cresterea numarului de absente

    private void jListaAbsenteAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_jListaAbsenteAncestorAdded

    }//GEN-LAST:event_jListaAbsenteAncestorAdded

    private void jMenu2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenu2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenu2ActionPerformed

    //Metoda pentru motivarea absentelor

    private void bMotiveazaAbsenteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bMotiveazaAbsenteActionPerformed
        //Incapsularea elevului selectat intr-o referinta temporara
        int indexElev = jList.getSelectedIndex();
        Elev temp = clasaElevi.get(indexElev);
        clasaElevi.remove(indexElev);

        //Indexul absentei selectate
        int indexAbsenta = jListaAbsente.getSelectedIndex();

        //Stergerea absentei in functie de materia selectata 
        switch (butonSelectat) {
            case "R":                 //Materia selectata este Romana?daca da...
                if (parolaRomana.getText().equals(Elev.getParolaRomana())) {       //verificam parola
                    temp.listaAbsenteR.remove(indexAbsenta);
                    parolaRomana.setText("");
                    model2.clear();
                    for (String s : temp.listaAbsenteR) {
                        model2.addElement(s);
                    }
                } else {
                    afisareParola.setText("PAROLA INCORECTA!");
                }

                break;
            case "M":             //Materia selectata este Matematica?daca da...
                if (parolaMate.getText().equals(Elev.getParolaMate())) {           //verificam parola
                    temp.listaAbsenteM.remove(indexAbsenta);
                    parolaMate.setText("");
                    model2.clear();
                    for (String s : temp.listaAbsenteM) {
                        model2.addElement(s);
                    }
                } else {
                    afisareParola.setText("PAROLA INCORECTA!");
                }

                break;
            case "E":                //Materia selectata este Engleza?daca da...
                if (parolaEngleza.getText().equals(Elev.getParolaEngleza())) {     //verificam parola
                    temp.listaAbsenteE.remove(indexAbsenta);
                    parolaEngleza.setText("");
                    model2.clear();
                    for (String s : temp.listaAbsenteE) {
                        model2.addElement(s);
                    }
                } else {
                    afisareParola.setText("PAROLA INCORECTA!");
                }

                break;
            case "F":               //Materia selectata este Franceza?daca da...
                if (parolaFranceza.getText().equals(Elev.getParolaFranceza())) {   //verificam parola
                    temp.listaAbsenteF.remove(indexAbsenta);
                    parolaFranceza.setText("");
                    model2.clear();
                    for (String s : temp.listaAbsenteF) {
                        model2.addElement(s);
                    }
                } else {
                    afisareParola.setText("PAROLA INCORECTA!");
                }

                break;
        }
        temp.scadeNumarAbsente();
        clasaElevi.add(indexElev, temp);

        rescriereaFilei(); //Refresh
    }//GEN-LAST:event_bMotiveazaAbsenteActionPerformed

    //Metoda pentru stergerea elevului , la apasarea de buton

    private void bStergeElevActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bStergeElevActionPerformed
        //Incapsularea elevului selectat intr o referinta temporara si stabilirea indexului acestuia
        int index = jList.getSelectedIndex();
        Elev temp = clasaElevi.get(index);
        String s = JOptionPane.showInputDialog(null, "Sunteti sigur ca doriti sa stergeti elevul " + temp.getNume() + " " + temp.getPrenume() + "?\n\nParola profesorului:");
        if (s.equals(Elev.getParolaRomana())||
                s.equals(Elev.getParolaMate())||
                s.equals(Elev.getParolaEngleza())||
                s.equals(Elev.getParolaFranceza())) { //Daca parola este corecta
            //Elevul va fi sters din liste
            clasaElevi.remove(index);
            model.removeElementAt(index);
            JOptionPane.showMessageDialog(null, "Elevul a fost sters cu succes", "Sters", JOptionPane.PLAIN_MESSAGE);
        }
        rescriereaFilei(); //Refresh
    }//GEN-LAST:event_bStergeElevActionPerformed

    //Metoda pentru ascunderea meniului Alegeti clasa
    
    public void ascundeMeniul() {
        clasa9A.setVisible(false);
        clasa9B.setVisible(false);
        clasa9C.setVisible(false);
        clasa9D.setVisible(false);
        alegetiClasa.setVisible(false);
        selecteazaClasa.setVisible(false);
    }
    
    //METODE PENTRU SELECTAREA CLASEI
    
    private void clasa9CActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clasa9CActionPerformed
       file = new File("clasa9C.txt");
        startProgram();  //initializarea obiectelor Elev din clasa A ,prin citirea lor din fisier

        ascundeMeniul();
    }//GEN-LAST:event_clasa9CActionPerformed

    private void clasa9BActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clasa9BActionPerformed
       file = new File("clasa9B.txt");
        startProgram();  //initializarea obiectelor Elev din clasa A ,prin citirea lor din fisier

        ascundeMeniul();
    }//GEN-LAST:event_clasa9BActionPerformed

    private void alegetiClasaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_alegetiClasaActionPerformed
i.setVisible(true);
    }//GEN-LAST:event_alegetiClasaActionPerformed

    private void clasa9AActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clasa9AActionPerformed
        file = new File("clasa9A.txt");
        startProgram();  //initializarea obiectelor Elev din clasa A ,prin citirea lor din fisier

        ascundeMeniul();
    }//GEN-LAST:event_clasa9AActionPerformed

    private void clasa9DActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clasa9DActionPerformed
        file = new File("clasa9D.txt");
        startProgram();  //initializarea obiectelor Elev din clasa A ,prin citirea lor din fisier

        ascundeMeniul();
    }//GEN-LAST:event_clasa9DActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
       clasa9A.setVisible(true);
        clasa9B.setVisible(true);
        clasa9C.setVisible(true);
        clasa9D.setVisible(true);
        alegetiClasa.setVisible(true);
        selecteazaClasa.setVisible(true);
        
       
        model.removeAllElements();
        model2.removeAllElements();
        
        
        DefaultTableModel m=(DefaultTableModel)tabelFinal.getModel();
       m.getDataVector().removeAllElements();
        
         clasaElevi.removeAll(clasaElevi);
         
        System.out.println("Sunteti inapoi in meniu");
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenu6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenu6ActionPerformed

    }//GEN-LAST:event_jMenu6ActionPerformed

    //HELP
    
    private void jInfoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jInfoActionPerformed
i.setVisible(true);      
    }//GEN-LAST:event_jInfoActionPerformed

    
    //Metoda pentru apasarea butonului de preview
    private void bPreviewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bPreviewActionPerformed
       incarcaTabel();
    }//GEN-LAST:event_bPreviewActionPerformed

    public static void main(String args[]) {

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Clasa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Clasa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Clasa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Clasa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        Clasa cls = new Clasa();  //Instantierea jFrameului Catalog

        info in = new info();   //Instantierea jFrameului Info

        cls.i = in;

        cls.setVisible(true);

        cls.setTitle("Calculator electronic");

    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel adaugaE;
    private javax.swing.JLabel adaugaF;
    private javax.swing.JLabel adaugaM;
    private javax.swing.JLabel adaugaR;
    private javax.swing.JLabel afisareParola;
    private javax.swing.JButton alegetiClasa;
    private javax.swing.JButton bAdaugaAbsenta;
    private javax.swing.JButton bAdaugaEngleza;
    private javax.swing.JButton bAdaugaFranceza;
    private javax.swing.JButton bAdaugaMate;
    private javax.swing.JButton bAdaugaRomana;
    private javax.swing.JToggleButton bIncheiereAn;
    private javax.swing.JButton bMotiveazaAbsente;
    private javax.swing.JButton bPreview;
    private javax.swing.JButton bStergeElev;
    private javax.swing.JLabel backgroundWood;
    private javax.swing.JMenuItem changePasEngleza;
    private javax.swing.JMenuItem changePasFranceza;
    private javax.swing.JMenuItem changePasMate;
    private javax.swing.JMenuItem changePasRomana;
    private javax.swing.JCheckBox checkEnd;
    private javax.swing.JComboBox<String> checkEngleza;
    private javax.swing.JComboBox<String> checkFranceza;
    private javax.swing.JComboBox<String> checkMate;
    private javax.swing.JComboBox<String> checkRomana;
    private javax.swing.JButton clasa9A;
    private javax.swing.JButton clasa9B;
    private javax.swing.JButton clasa9C;
    private javax.swing.JButton clasa9D;
    private javax.swing.JButton jAdd;
    private javax.swing.JLabel jBackground;
    private javax.swing.JMenuItem jInfo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JList<String> jList;
    private javax.swing.JList<String> jListaAbsente;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu6;
    private javax.swing.JMenu jMenu7;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JTextField jNume;
    private javax.swing.JLabel jParola;
    private javax.swing.JTextField jPrenume;
    private javax.swing.JLabel jPrenumeLabel;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JLabel labelAbsente;
    private javax.swing.JLabel labelBackgroundNote;
    private javax.swing.JLabel labelEngleza;
    private javax.swing.JLabel labelFranceza;
    private javax.swing.JLabel labelMate;
    private javax.swing.JLabel labelMedieEngleza;
    private javax.swing.JLabel labelMedieFranceza;
    private javax.swing.JLabel labelMedieMate;
    private javax.swing.JLabel labelMedieRomana;
    private javax.swing.JLabel labelNume;
    private javax.swing.JLabel labelRomana;
    private javax.swing.JLabel labelTime;
    private javax.swing.JLabel mE;
    private javax.swing.JLabel mF;
    private javax.swing.JLabel mM;
    private javax.swing.JLabel mR;
    private javax.swing.JButton materieEngleza;
    private javax.swing.JButton materieFranceza;
    private javax.swing.JButton materieMate;
    private javax.swing.JButton materieRomana;
    private javax.swing.JLabel nE;
    private javax.swing.JLabel nF;
    private javax.swing.JLabel nM;
    private javax.swing.JLabel nR;
    private javax.swing.JPasswordField parolaEngleza;
    private javax.swing.JPasswordField parolaFranceza;
    private javax.swing.JPasswordField parolaMate;
    private javax.swing.JPasswordField parolaRomana;
    private javax.swing.JLabel selecteazaClasa;
    private javax.swing.JTable tabelFinal;
    // End of variables declaration//GEN-END:variables
}
