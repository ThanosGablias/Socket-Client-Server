//κλάση Movie για την δημιουργία ταινιών
public class Movie {
    // Δήλωση μεταβλητών
    private String title;        // Τίτλος
    private String director;     // Σκηνοθέτης
    private String genre;        // Είδος
    private String length;       // Διάρκεια
    private String description;  // Περιγραφή για την υπόθεση της ταινίας

    //Constructor
    public Movie(String title, String director, String genre, String length, String description){
        this.title = title;
        this.director = director;
        this.genre = genre;
        this.length = length;
        this.description = description;
    }

    //Μέθοδος 'getTitle()' η οποία επιστρέφει την μεταβλητή title
    public String getTitle(){
        return title;
    }

    //Μέθοδος 'getDirector()' η οποία επιστρέφει την μεταβλητή director
    public String getDirector(){
        return director;
    }

    //Μέθοδος 'getGenre()' η οποία επιστρέφει την μεταβλητή genre
    public String getGenre(){
        return genre;
    }

    //Μέθοδος 'getLength()' η οποία επιστρέφει την μεταβλητή length
    public String getLength(){
        return length;
    }

    //Μέθοδος 'getDescription()' η οποία επιστρέφει την μεταβλητή description
    public String getDescription(){
        return description;
    }

    //Μέθοδος η οποία επιστρέφει όλα τα στοιχεία μίας ταινίας
    public String MoviesToString(){
        return getTitle() + ":" + getDirector() + ":" + getGenre() + ":" + getLength() + ":" + getDescription();
    }
}
