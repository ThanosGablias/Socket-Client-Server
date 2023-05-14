import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//(κύρια) Κλάση για την υλοποίηση της γραφικής διεπαφής
public class Gui implements ActionListener{

    private JPanel row1,row2;
    JFrame frame = new JFrame("Movies+"); //Τίτλος πλαισίου

    // Δημιουργία των 2 κουμπιών
    JButton button1 = new JButton("Insert new movies");
    JButton button2 = new JButton("Search a movie (title or director)");

    //Constructor
    public Gui() {
        frame.setLocationRelativeTo(null); //κεντράρει το παράθυρο στην οθόνη

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Κλείνει η εφαρμογή όταν κάνουμε κλικ στο κουμπί «Κλείσιμο» της γραμμής τίτλου

        frame.setVisible(true); // Το πλαίσιο να είναι ορατό

        Container pane = frame.getContentPane(); //Δημιουργία υποδοχέα

        FlowLayout flo = new FlowLayout(); // Δημιουργία διαχειριστή διάταξης

        pane.setLayout(flo); // Σύνδεση διαχειριστή διάταξης με τον υποδοχέα

        //Δημιουργία Panel για το Button1
        row1 = new JPanel();
        button1.setBounds(300,480,600,120);
        button1.setFocusable(false);
        button1.addActionListener(this);

        //Δημιουργία Panel για το Button2
        row2 = new JPanel();
        button2.setBounds(300,480,600,120);
        button2.setFocusable(false);
        button2.addActionListener(this);

        // Πρώτη γραμμή
        row1.setLayout(flo);
        row1.add(button1);
        pane.add(row1);

        // Δεύτερη γραμμή
        row2.setLayout(flo);
        row2.add(button2);
        pane.add(row2);

        frame.setContentPane(pane); // Τοποθέτηση υποδοχέα στο πλαίσιο
        frame.pack(); //ορίζουμε το μέγεθος του παραθύρου όσο χρειάζεται για να χωρέσουν τα περιεχόμενα
    }

    //Έλεγχος για το πιο button έχει πατηθεί και η φόρτωση του αντίστοιχου παραθύρου (JFrame)
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == button1){    //e.getSource(), το όνομα του button που έχει πατηθεί να είναι το button1
            InsertMovieWindow b1 = new InsertMovieWindow(this);
        }
        else if(e.getSource() == button2){     //e.getSource(), το όνομα του button που έχει πατηθεί να είναι το button2
            SearchMovieWindow b2 = new SearchMovieWindow(this);
        }
    }
}