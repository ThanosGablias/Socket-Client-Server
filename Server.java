/*
3212019029, Γκαμπλιάς Αθανάσιος
*/

import java.net.*;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

//Κλάση που αντιπροσωπεύει το μοντέλο εξυπηρετητή
public class Server {
    public static void main(String[] args) {
        MySystem ms = new MySystem("Movies+");
        try {
            // Δημιουργία αντικειμένου ServerSocket
            ServerSocket server = new ServerSocket(5555);

            while (true) {
                System.out.println("Waiting for client");
                // Αναμονή για σύνδεση από τον πελάτη
                Socket sock = server.accept();

                System.out.println("Accepting Connection...");
                System.out.println("Local Address :" + sock.getInetAddress() + " Port :" + server.getLocalPort());

                // Δημιουργία Streams για διάβασμα και αποστολή μηνυμάτων κειμένου
                ObjectOutputStream outstream = new ObjectOutputStream(sock.getOutputStream());
                ObjectInputStream instream = new ObjectInputStream(sock.getInputStream());

                // Ανάγνωση μηνύματος από τον πελάτη
                String strin = (String) instream.readObject();
                System.out.println("The client says: " + strin);

                // Αν το μήνυμα είναι "BEGIN"
                if (strin.equals("BEGIN")) {
                    // Αποστολή μηνύματος "LISTENING"
                    String strout = "LISTENING";
                    //Στέλνουμε στον client το strout -> "LISTENING"
                    outstream.writeObject(strout);
                    outstream.flush();
                    //The server says: LISTENING
                    System.out.println("The server says: " + strout);

                    // Ανάγνωση μηνύματος από τον πελάτη
                    strin = (String) instream.readObject();
                    System.out.println("The client says: " + strin);

                    //Το μήνυμα μπορεί να είναι είτε "RQ_INSERT" για εισαγωγή ταινίας
                    if (strin.equals("RQ_INSERT")) {
                        //Ανάγνωση μηνύνατος που προέρχεται από τον client και εγχώρηση της τιμής (μήνυμα) στην μεταβλητή title
                        String title =  (String) instream.readObject();
                        System.out.println("The client says: " + title);

                        //Ανάγνωση μηνύνατος που προέρχεται από τον client και εγχώρηση της τιμής (μήνυμα) στην μεταβλητή director
                        String director = (String) instream.readObject();
                        System.out.println("The client says: " + director);

                        //Ανάγνωση μηνύνατος που προέρχεται από τον client και εγχώρηση της τιμής (μήνυμα) στην μεταβλητή genre
                        String genre = (String) instream.readObject();
                        System.out.println("The client says: " + genre);

                        //Ανάγνωση μηνύνατος που προέρχεται από τον client και εγχώρηση της τιμής (μήνυμα) στην μεταβλητή length
                        String length = (String) instream.readObject();
                        System.out.println("The client says: " + length);

                        //Ανάγνωση μηνύνατος που προέρχεται από τον client και εγχώρηση της τιμής (μήνυμα) στην μεταβλητή description
                        String description = (String) instream.readObject();
                        System.out.println("The client says: " + description);

                        //Εισάγει την ταινία που πληκτρολόγησε ο χρήστης
                        ms.addNewMovie(title, director, genre, length, description);

                        //Προσθήκη 6 ταινιών -> Τα έχω βάλει για testing όταν τρέχω το αρχείο
                        /*ms.addNewMovie("Black Crab","Adam Berg","Action","154","In a post-apocalyptic world, six soldiers on a covert mission must transport a mysterious package across a frozen archipelago.");
                        ms.addNewMovie("Extraction","Sam Hargrave","Action","156","Tyler Rake, a fearless black market mercenary, embarks on the most deadly extraction of his career when he's enlisted to rescue the kidnapped son of an imprisoned international crime lord.");
                        ms.addNewMovie("The Hunt","Adam Berg","Action","90","Twelve strangers wake up in a clearing. They don't know where they are, or how they got there. They don't know they've been chosen - for a very specific purpose");
                        ms.addNewMovie("Against the ice","Peter Flinth","Adventure","102","In 1909, two explorers fight to survive after they're left behind while on a Denmark expedition in ice-covered Greenland.");
                        ms.addNewMovie("Spider-Man: Homecoming","Adam Berg","Action","133","Peter Parker returns to routine life as a high schooler until the Vulture's arrival gives him the chance to prove himself as a web-slinging superhero.");
                        ms.addNewMovie("The Shawshank Redemption","Peter Flinth","Dramas","157","Convicted of murdering his wife and her lover, a quiet banker tries to survive prison by clinging to hope — and befriending a fellow lifer named Red.");
                        */
                        // εγγραφή σε αρχείο
                        ms.getWriter("Movies.txt");

                        //Στέλνουμε στον client το strout -> "OK"
                        outstream.writeObject("OK");
                        outstream.flush();

                        // είτε "RQ_SEARCH" για αναζήτηση ταινίας
                    } else if (strin.equals("RQ_SEARCH")) {
                        // Ανάγνωση μηνύματος από τον πελάτη
                        strin = (String) instream.readObject();
                        System.out.println("The client says: " + strin);
                        ArrayList<Movie> moviesl = ms.SearchMovie(strin,"");

                        if (moviesl.size()==0){
                            strout = "NORECORD";
                            //Στέλνουμε στον client το strout -> "NORECORD"
                            outstream.writeObject(strout);
                            outstream.flush();
                        }
                        else{
                            //είτε στην περίπτωση που δεν έχουμε καμία εγχώρηση δεδομένων " "
                            strout = "";
                            for(Movie m:moviesl){
                                strout+=m.MoviesToString()+"\n";
                            }
                            outstream.writeObject(strout);
                            outstream.flush();
                        }
                    }
                    // είτε "RQD_SEARCH" για αναζήτηση σκηνοθέτη
                    else if (strin.equals("RQD_SEARCH")) {
                        // Ανάγνωση μηνύματος από τον πελάτη
                        strin = (String) instream.readObject();
                        System.out.println("The client says: " + strin);
                        ArrayList<Movie> moviesl = ms.SearchMovie("",strin);
                        //Αν η λίστα moviesl είναι άδεια
                        //Αν η λίστα είναι άδεια
                        if (moviesl.size()==0){
                            //τότε ουσιαστικά εγχωρεί στο strout το μήνυμα "NORECORD"
                            strout = "NORECORD";
                            outstream.writeObject(strout);
                            outstream.flush();
                        }
                        //είτε στην περίπτωση που δεν έχουμε καμία εγχώρηση δεδομένων " "
                        else{
                            strout = "";
                            for(Movie m:moviesl){
                                strout+=m.MoviesToString()+"\n";
                            }
                            outstream.writeObject(strout);
                            outstream.flush();
                        }
                    }
                    // Κλείσιμο ροών και socket
                    instream.close();
                    outstream.close();
                    sock.close();
                    System.out.println("Connection Closing...");
                }
            }
        } catch (Exception ex) {
            System.out.println("Error during I/O");
            ex.getMessage();
            ex.printStackTrace();
        }
    }
}


