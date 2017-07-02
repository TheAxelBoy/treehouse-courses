package com.teamtreehouse;

import com.teamtreehouse.model.Song;
import com.teamtreehouse.model.SongBook;
import com.teamtreehouse.model.SongRequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

// Importing interfaces and classes
// No wildcards used for better overview of used classes

public class KaraokeMachine {
    private SongBook mSongBook;
    private Queue<SongRequest> mSongQueue;
    private BufferedReader mReader;
    private Map<String, String> mMenu;

    public KaraokeMachine(SongBook songBook) {
        mSongBook = songBook;
        mSongQueue = new ArrayDeque<>();
        mReader = new BufferedReader(new InputStreamReader(System.in));
        mMenu = new LinkedHashMap<>();
        // Menu points
        mMenu.put("add", "Add a new song to the song book");
        mMenu.put("play", "Play next song in queue");
        mMenu.put("choose", "Choose a song to sing");
        mMenu.put("quit", "Give up. Exit the program");
    }

    // Clear the console screen using ANSI Escape Codes
    private static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    // Ask for action in menu (mMenu)
    private String promptAction() throws IOException {
        System.out.printf("There are %d songs available and " +
                        "%d in the queue. Your options are: %n",
                mSongBook.getSongCount(),
                mSongQueue.size());
        // Print out menu points using interclass Map.Entry
        for (Map.Entry<String, String> option : mMenu.entrySet()) {
            System.out.printf("%s - %s%n", option.getKey(), option.getValue());
        }
        // Ask for input
        System.out.print("What do you want to do: ");
        String choice = mReader.readLine();
        return choice.trim().toLowerCase();
    }

    // Run the application
    public void run() {
        clearScreen();
        String choice = "";
        // do-while until "quit" is chosen
        do {
            try {
                // Ask for action in menu
                choice = promptAction();
                clearScreen();
                // Do action
                switch (choice) {
                    // Add Song to mSongBook
                    case "add":
                        Song song = promptNewSong();
                        mSongBook.addSong(song);
                        clearScreen();
                        System.out.printf("Added %s!%n%n", song);
                        break;
                    // Choose a song and add to the mSongQueue
                    case "choose":
                        // Ask for the singer
                        clearScreen();
                        String singerName = promptForSingerName();
                        // Ask for artist
                        String artist = promptArtist();
                        // Ask for songs from a specific artist
                        Song artistSong = promptSongForArtist(artist);
                        SongRequest songRequest = new SongRequest(singerName, artistSong);
                        if (mSongQueue.contains(songRequest)) {
                            clearScreen();
                            System.out.printf("Whoops, %s already requested %s!%n%n",
                                    singerName,
                                    artistSong);
                            break;
                        }
                        mSongQueue.add(songRequest);
                        clearScreen();
                        System.out.printf("You chose: %s%n%n", artistSong);
                        break;
                    // "Play" the next song and remove it from mSongQueue
                    case "play":
                        playNext();
                        break;
                    // Quit the application
                    case "quit":
                        System.out.println("Thanks for playing!");
                        break;
                    default:
                        System.out.printf("Unknown choice: '%s'. Try again.%n%n",
                                choice);
                }
            } catch (IOException ioe) {
                System.out.println("Problem with input");
                ioe.printStackTrace();
            } catch (IndexOutOfBoundsException iobe) {
                clearScreen();
                System.out.printf("This menu point is not available.%n%n");
            } catch (NumberFormatException nfe) {
                clearScreen();
                System.out.printf("Please type in a number to choose a " +
                        "menu point.%n%n");
            }
        } while (!choice.equals("quit"));
    }

    private String promptForSingerName() throws IOException {
        System.out.print("Enter the singer name: ");
        return mReader.readLine();
    }

    // Ask for new Song
    private Song promptNewSong() throws IOException {
        System.out.print("Enter the artist's name: ");
        String artist = mReader.readLine();
        System.out.print("Enter the title: ");
        String title = mReader.readLine();
        System.out.print("Enter the video URL: ");
        String videoUrl = mReader.readLine();
        return new Song(artist, title, videoUrl);
    }

    // Ask for available artists in mSongBook
    private String promptArtist() throws IOException {
        System.out.printf("------------------%nAvailable artists:%n");
        List<String> artists = new ArrayList<>(mSongBook.getArtists());
        // Prompt for artists, returns index for artists list
        int index = promptForIndex(artists);
        return artists.get(index);
    }

    // Ask for song from a specific artist
    private Song promptSongForArtist(String artist) throws IOException {
        // Create list of songs for a specific artist
        List<Song> songs = mSongBook.getSongsForArtist(artist);
        // List of song titles
        List<String> songTitles = new ArrayList();
        // Go through every song and add the title to songTitles
        for (Song song : songs) {
            songTitles.add(song.getTitle());
        }
        System.out.printf("------------------%nAvailable songs for %s:%n",
                artist);
        // Prompt for song title, returns index for songTitles list
        int index = promptForIndex(songTitles);
        return songs.get(index);
    }

    // Ask for a index of a list
    private int promptForIndex(List<String> options) throws IOException {
        int counter = 1;
        // Go through all songs and print them out
        for (String option : options) {
            System.out.printf("%d.) %s%n", counter, option);
            counter++;
        }
        // Ask for input
        System.out.print("Your choice: ");
        String optionAsString = mReader.readLine();
        int choice = Integer.parseInt(optionAsString.trim());
        // index is 0-based, so -1 has to be used
        return choice - 1;
    }

    // "Play" next song and remove it from mSongQueue
    private void playNext() {
        SongRequest songRequest = mSongQueue.poll();
        // If there is no song, print a message
        if (songRequest == null) {
            System.out.printf("Sorry, there are no songs in the queue. " +
                    "Use \"choose\" from the menu to add some%n%n");
        } else {
            Song song = songRequest.getSong();
            System.out.printf("Ready %s? Open %s to hear %s by %s%n%n",
                    songRequest.getSingerName(),
                    song.getVideoUrl(),
                    song.getTitle(),
                    song.getArtist());
        }
    }
}
