import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

//Κλάση για την δημιουργία παραθύρου που περιλαμβάνει την αναζήτηση ταινιών στο σύστημα μέσω γραφικής διεπαφής
public class SearchMovieWindow {
    JFrame frame = new JFrame("Movies+"); //Τίτλος πλαισίου
    private JPanel row1,row2,row3,row4,row5,row6;
    // Δημιουργία ετικετών
    private JLabel label1,label2,label3,label4,label5;
    //JTextField, είναι ένα component το οποίο μας επιτρέπει να γράψουμε σε μία γραμμή κειμένου
    private JTextField name1,name2,name3,name4,name5;

    // Δημιουργία των 2 κουμπιών
    JButton jbtn_search = new JButton("Search Movie");
    JButton jbtn_reset = new JButton("Reset");

    Gui gui; //περέχει όλα τα δεδομένα του object gui

    SearchMovieWindow(Gui gui){ //είσοδος του object gui διαμέσο του Constructor
        this.gui = gui;

        frame.setLayout(null);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null); //κεντράρει το παράθυρο στην οθόνη

        row1 = new JPanel();
        label1 = new JLabel("Insert the title or the director of the movie: ", JLabel.RIGHT);
        name1 = new JTextField(40); // Δημιουργία χώρου για να γράψουμε με μήκος 40 χαρακτήρες

        //Δημιουργία Panel για το Button jbtn_search
        row2 = new JPanel();
        //Δημιουργία ενός popup menu με επιλογές (Title ή Director)
        //Αναζήτηση με βάση τον τίτλο ή τον σκηνοθέτη της ταινίας
        String s[] = {"Title","Director"};
        JComboBox titleordirector = new JComboBox(s);
        titleordirector.setBounds(40, 160, 200, 40);
        row2.add(titleordirector);

        jbtn_search.setBounds(100,160,200,40);
        jbtn_search.setFocusable(false);

        //καθοριμός του κώδικα που θα εκτελεστεί όταν θα πατηθεί το button και μέσω της Action Performed
        jbtn_search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String strin, strout;
                strout = "RQ_SEARCH";
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
                        //Υλοποίηση του ComboBox αναλόγως την επιλογη του χρήστη (client) για αναζήτηση με βάση τον τίτλο της ταινίας(RQ_SEARCH) ή
                        //με βάση τον σκηνοθέτη της ταινίας(RQD_SEARCH).
                        if (titleordirector.getSelectedIndex()==0)
                            strout = "RQ_SEARCH";
                        else
                            strout = "RQD_SEARCH";
                        //Στέλνουμε στον Server το strout, το οποίο θα είναι είτε "RQ_SEARCH" είτε "RQD_SEARCH" -> (αναλόγως την επιλογή του χρήστη απο το ComboBox)
                        outstream.writeObject(strout);
                        outstream.flush();

                        //εγχωρούμε στο strout τον τίτλο ή τον σκηνοθέτη της ταινίας (αναλόγως την επιλογή που κάναμε στο ComboBox)
                        strout = name1.getText();
                        //Στέλνουμε στον Server το strout
                        outstream.writeObject(strout);
                        outstream.flush();

                        ///Ανάγνωση μηνύνατος που προέρχεται από τον server
                        strin = (String) instream.readObject();
                        //Εμφανίζει ένα παράθυρο διαλόγου
                        JOptionPane.showMessageDialog(null,"Results:\n"+strin);
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
            }
        });

        Container pane = frame.getContentPane(); // Δημιουργία υποδοχέα

        // Ο grid layout manager παρέχει ευελιξία στην τοποθέτηση components.
        // Δημιουργείτε o διαχειριστής τοποθέτησης δίνοντας το πλήθος των
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
        row2.add(jbtn_search);
        row2.add(jbtn_reset);
        pane.add(row2);

        frame.setContentPane(pane); // Τοποθέτηση υποδοχέα στο πλαίσιο

        // Γέμισμα του πλαισίου
        // Η μέθοδος pack() αυτόματα δίνει διαστάσεις στο JFrame ανάλογα με
        // τις διαστάσεις των components που βάζουμε μέσα του και ανάλογα την
        // θέση στην οποία βάζουμε καθένα από τα components
        frame.pack();
    }
}

