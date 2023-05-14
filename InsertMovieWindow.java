import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.*;
import java.net.Socket;
import java.io.ObjectInputStream;

//Κλάση για την δημιουργία παραθύρου που περιλαμβάνει την εισαγωγή νέων ταινιών στο σύστημα μέσω γραφικής διεπαφής
public class InsertMovieWindow {
    JFrame frame = new JFrame("Movies+"); //Τίτλος πλαισίου
    private JPanel row1,row2,row3,row4,row5,row6;
    // Δημιουργία ετικετών
    private JLabel label1,label2,label3,label4,label5;
    //JTextField, είναι ένα component το οποίο μας επιτρέπει να γράψουμε σε μία γραμμή κειμένου
    private JTextField name1,name2,name3,name4,name5;

    // Δημιουργία των 2 κουμπιών
    JButton jbtn_insert = new JButton("Insert Movie");
    JButton jbtn_reset = new JButton("Reset");

    Gui gui; //περέχει όλα τα δεδομένα του object gui

    InsertMovieWindow(Gui gui){ //είσοδος του object gui διαμέσο του Constructor
        this.gui = gui;

        frame.setLayout(null);
        frame.setVisible(true); // Το πλαίσιο να είναι ορατό
        frame.setLocationRelativeTo(null); //κεντράρει το παράθυρο στην οθόνη

        row1 = new JPanel();
        label1 = new JLabel("Insert the title of the movie: ", JLabel.RIGHT);
        name1 = new JTextField(40); // Δημιουργία χώρου για να γράψουμε με μήκος 40 χαρακτήρες

        row2 = new JPanel();
        label2 = new JLabel("Insert the director of the movie: ", JLabel.RIGHT);
        name2 = new JTextField(40); // Δημιουργία χώρου για να γράψουμε με μήκος 40 χαρακτήρες

        row3 = new JPanel();
        label3 = new JLabel("Insert the genre of the movie: ", JLabel.RIGHT);
        name3 = new JTextField(40); // Δημιουργία χώρου για να γράψουμε με μήκος 40 χαρακτήρες

        row4 = new JPanel();
        label4 = new JLabel("Insert the length of the movie: ", JLabel.RIGHT);
        name4 = new JTextField(40); // Δημιουργία χώρου για να γράψουμε με μήκος 40 χαρακτήρες

        row5 = new JPanel();
        label5 = new JLabel("Insert the description of the movie: ", JLabel.RIGHT);
        name5 = new JTextField(40); // Δημιουργία χώρου για να γράψουμε με μήκος 40 χαρακτήρες

        //Δημιουργία Panel για το Button jbtn_insert
        row6 = new JPanel();
        jbtn_insert.setBounds(100,160,200,40);
        jbtn_insert.setFocusable(false);

        //καθοριμός του κώδικα που θα εκτελεστεί όταν θα πατηθεί το button και μέσω της Action Performed
        jbtn_insert.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String strin, strout;
                strout = "RQ_INSERT";
                try {
                    // Δημιουργία αντικειμένου ServerSocket
                    Socket sock = new Socket("localhost", 5555);
                    // Δημιουργία Streams για διάβασμα και αποστολή μηνυμάτων κειμένου
                    ObjectInputStream instream = new ObjectInputStream(sock.getInputStream());
                    ObjectOutputStream outstream = new ObjectOutputStream(sock.getOutputStream());

                    strout = "BEGIN";
                    //Στέλνουμε στον Server το strout -> "BEGIN"
                    outstream.writeObject(strout);
                    outstream.flush();
                    //Ανάγνωση μηνύνατος που προέρχεται από τον server
                    strin = (String) instream.readObject();
                    //Αν ο Server έχει στείλει "LISTENING"
                    if (strin.equals("LISTENING")){
                        //τότε στο strout εγχώρησε το "RQ_INSERT"
                        strout = "RQ_INSERT";
                        //Στέλνουμε στον Server το strout
                        outstream.writeObject(strout);
                        outstream.flush();
                        //Εγχωρούμε στο strout τον τίτλο της ταινίας
                        strout = name1.getText();
                        //Στέλνουμε στον Server το strout -> name1(Τίτλος ταινίας)
                        outstream.writeObject(strout);
                        outstream.flush();

                        //Εγχωρούμε στο strout τον σκηνοθέτη της ταινίας
                        strout = name2.getText();
                        //Στέλνουμε στον Server το strout -> name2(Σκηνοθέτης ταινίας)
                        outstream.writeObject(strout);
                        outstream.flush();

                        //Εγχωρούμε στο strout το είδος της ταινίας
                        strout = name3.getText();
                        //Στέλνουμε στον Server το strout -> name3(είδος ταινίας)
                        outstream.writeObject(strout);
                        outstream.flush();

                        //Εγχωρούμε στο strout το μήκος της ταινίας
                        strout = name4.getText();
                        //Στέλνουμε στον Server το strout -> name4(μήκος ταινίας)
                        outstream.writeObject(strout);
                        outstream.flush();

                        //Εγχωρούμε στο strout την περιγραφή της ταινίας
                        strout = name4.getText();
                        //Στέλνουμε στον Server το strout -> name5(περιγραφή ταινίας)
                        outstream.writeObject(strout);
                        outstream.flush();

                        //Ανάγνωση μηνύνατος που προέρχεται από τον server
                        strin = (String) instream.readObject();
                        System.out.println(strin);
                        //Αν ο Server έχει στείλει "OK"
                        if (strin.equals("OK")){
                            //τότε εμφάνισε ένα παράθυρο διαλόγου με το μήνυμα "Successful Insertion!"
                            JOptionPane.showMessageDialog(null,"Successful Insertion!");
                        }
                    }
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(null,"Problem in Sockets");
                    e.printStackTrace();
                } catch (ClassNotFoundException ex) {
                    ex.printStackTrace();
                }
            }
        });

        //Δημιουργία Panel για το Button jbtn_reset
        jbtn_reset.setBounds(100,160,200,40);
        jbtn_reset.setFocusable(false);
        jbtn_reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                name1.setText("");
                name2.setText("");
                name3.setText("");
                name4.setText("");
                name5.setText("");
            }
        });

        Container pane = frame.getContentPane(); // Δημιουργία υποδοχέα

        // Ο grid layout manager παρέχει ευελιξία στην τοποθέτηση components.
        // Δημιουργείτε ο διαχειριστής τοποθέτησης δίνοντας το πλήθος των
        // γραμμών και των στηλών. Τα components θα γεμίσουν τα κελιά που
        // ορίζει ο διαχειριστής.
        GridLayout layout = new GridLayout(5,2);
        pane.setLayout(layout);
        FlowLayout flo = new FlowLayout(); // Δημιουργία διαχειριστή διάταξης

        //Καθορισμός του layout για την εμφάνιση των labels και των text fields
        row1.setLayout(flo); //1η σειρά
        row1.add(label1);
        row1.add(name1);
        pane.add(row1);

        row2.setLayout(flo); //2η σειρά
        row2.add(label2);
        row2.add(name2);
        pane.add(row2);

        row3.setLayout(flo); //3η σειρά
        row3.add(label3);
        row3.add(name3);
        pane.add(row3);

        row4.setLayout(flo); //4η σειρά
        row4.add(label4);
        row4.add(name4);
        pane.add(row4);

        row5.setLayout(flo); //5η σειρά
        row5.add(label5);
        row5.add(name5);
        pane.add(row5);

        row6.setLayout(flo); //6η σειρά
        row6.add(jbtn_insert);
        row6.add(jbtn_reset);
        pane.add(row6);

        frame.setContentPane(pane); // Τοποθέτηση υποδοχέα στο πλαίσιο

        // Γέμισμα του πλαισίου
        // Η μέθοδος pack() αυτόματα δίνει διαστάσεις στο JFrame ανάλογα με
        // τις διαστάσεις των components που βάζουμε μέσα του και ανάλογα την
        // θέση στην οποία βάζουμε καθένα από τα components
        frame.pack();
    }
}
