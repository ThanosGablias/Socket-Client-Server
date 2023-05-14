import java.io.*;
import java.util.ArrayList;
import java.io.ObjectInputStream;
import java.util.HashMap;

//Η κλάση MySystem είναι η κύρια κλάση που διαχειρίζεται όλες τις λειτουργίες και τις συσχετίσεις των αντικειμένων.
public class MySystem {
    private String systemName;
    private String moviesFile = "Movies.txt";
    HashMap<String,Movie> movies; // Δημιουργία HashMap που θα περιλαμβάνει String keys και Movie values

    //Constructor
    public MySystem(String systemName) {
        this.systemName = systemName;
        movies = getReader(moviesFile);
    }
    // αναζητά ταινία από τίτλο ή σκηνοθέτη και επιστρέφει λίστα ταινιών
    // (1 αν είναι τίτλος που τον βρήκε, πολλές αν είναι ταινίες από σκηνοθέτη και
    // άδεια λίστα αν βρήκε
    ArrayList<Movie> SearchMovie(String title, String director) {
        System.out.println("search:"+title);
        // Δημιουργούμε μία λίστα με τύπου Movie στοιχεία
        ArrayList<Movie>  moviesl = new ArrayList<Movie>();
        //Αν κάνουμε αναζήτηση με βάση τον τίτλο (πεδίο εισαγωγής τίτλου -> δεν είναι άδειο)
        if (!title.equals("")){
            //παίρνουμε τον τίτλο της ταινίας
            Movie m = movies.get(title);
            // Αν το βρήκε
            if (m!=null)
                moviesl.add(m); //τότε στο moviesl εγχωρούμε(add) την ταινία που μόλις βρήκαμε.
        }
        //Αν κάνουμε αναζήτηση με βάση τον σκηνοθέτη (πεδίο εισαγωγής σκηνοθέτη -> δεν είναι άδειο)
        else if (!director.equals("")){
            //(for loop) -> Για κάθε στοιχείο του HashMap -> ζεύγος(key, value)
            for (HashMap.Entry<String,Movie> entry : movies.entrySet()) {
                //εγχωρεί το value στο Movie
                Movie m = entry.getValue();
                //Ελέγχουμε αν η συγκεκριμένη ταινία(m) έχει τον συγκεκριμένο σκηνοθέτη που αναζητούμε
                if (m.getDirector().equals(director)) {
                    moviesl.add(m); //τότε στο moviesl εγχωρούμε(add) την ταινία που μόλις βρήκαμε
                }
            }
        }
        return moviesl; //επιστρέφει την λίστα αντικειμένων
    }

    //Μέθοδος η οποία δημιουργεί και εισάγει αντικείμενα ταινιών στο HashMap movies.
    public void addNewMovie(String title, String director, String genre, String length, String description){
        movies.put(title,new Movie(title, director, genre, length, description));
    }

    //Μέθοδος για την ανάγνωση των ταινιών από το αρχείο
    private HashMap<String,Movie> getReader(String fileName){
        ObjectInputStream instream = null;

        try
        {
            File file = new File(fileName);
            instream = new ObjectInputStream(
                    new FileInputStream(file) );
            return (HashMap<String,Movie>) instream.readObject();
        }

        catch (FileNotFoundException e)
        {
            System.out.println("The file doesn't exist.");
            return new HashMap<String,Movie>();

        } catch (IOException ex) {
            //Logger.getLogger(MySystem.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            //Logger.getLogger(MySystem.class.getName()).log(Level.SEVERE, null, ex);
        }

        return new HashMap<String,Movie>();
    }

    //Μέθοδος εγγραφής των ταινιών σε αρχείο
    void getWriter(String fileName){
        ObjectOutputStream outputStream = null;

        try
        {
            File file = new File(fileName);
            outputStream = new ObjectOutputStream(
                    new FileOutputStream(file) );
            outputStream.writeObject(movies);
        }

        catch (FileNotFoundException e)
        {
            //System.out.println("The file doesn't exist.");


        } catch (IOException ex) {
            //Logger.getLogger(MySystem.class.getName()).log(Level.SEVERE, null, ex);
        } //catch (ClassNotFoundException ex) {

    }
}

