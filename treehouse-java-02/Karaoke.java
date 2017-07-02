import com.teamtreehouse.KaraokeMachine;
import com.teamtreehouse.model.SongBook;

public class Karaoke {

    public static void main(String[] args) {
        SongBook songBook = new SongBook();
        // Import songs from a txt-File
        songBook.importFrom("songs.txt");
        // Create new KaraokeMachine and run it
        KaraokeMachine machine = new KaraokeMachine(songBook);
        machine.run();
        // Export songs to a txt-File
        System.out.println("Saving book...");
        songBook.exportTo("songs.txt");
    }
}
