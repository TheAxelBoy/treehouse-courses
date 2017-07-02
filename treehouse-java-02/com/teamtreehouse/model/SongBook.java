package com.teamtreehouse.model;

import java.io.*;
import java.util.*;

// No wildcards used for better overview of used classes

public class SongBook {
    private List<Song> mSongs;

    public SongBook() {
        mSongs = new ArrayList<Song>();
    }

    // Custom Serialization: Export songs to a file
    public void exportTo(String fileName) {
        try (
                // Output stream for file
                FileOutputStream fos = new FileOutputStream(fileName);
                // Writer for output stream
                PrintWriter writer = new PrintWriter(fos);
        ) {
            // Go through every song and write in file
            for (Song song : mSongs) {
                // Use pipes for seperation
                writer.printf("%s|%s|%s%n",
                        song.getArtist(),
                        song.getTitle(),
                        song.getVideoUrl());
            }
        } catch (IOException ioe) {
            System.out.printf("Problem saving %s%n", fileName);
            ioe.printStackTrace();
        }
    }

    // Custom Serialization: Import songs from a file
    public void importFrom(String fileName) {
        try (
                // FileInputStream: Obtains input bytes from a file
                FileInputStream fis = new FileInputStream(fileName);
                // BufferedReader: Reads characters from a character-input stream
                // InputStreamReader: Read bytes and decode them into characters
                BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
        ) {
            String line;
            // readLine() returns null when end of file is reached
            while ((line = reader.readLine()) != null) {
                // Split data using regular expressions
                String[] args = line.split("\\|");
                // add song to SongBook
                addSong(new Song(args[0], args[1], args[2]));
            }
        } catch (IOException ioe) {
            System.out.printf("Problems loading %s%n", fileName);
            ioe.printStackTrace();
        }
    }

    // Add a song to mSongs
    public void addSong(Song song) {
        mSongs.add(song);
    }

    // Get number of songs
    public int getSongCount() {
        return mSongs.size();
    }

    // This should be cached
    // Returns a map of songs with <artist, songs>
    private Map<String, List<Song>> byArtist() {
        Map<String, List<Song>> byArtist = new TreeMap<>();
        // Go through every song
        for (Song song : mSongs) {
            // If the map contains the artist, this will return the list with songs
            // else "null" will be returned
            List<Song> artistSongs = byArtist.get(song.getArtist());
            // If the artist does not exist in the map, create a new List
            // and add artist and list to the map
            if (artistSongs == null) {
                artistSongs = new ArrayList<>();
                byArtist.put(song.getArtist(), artistSongs);
            }
            // Add a song to the list
            artistSongs.add(song);
        }
        return byArtist;
    }

    // get a set of all artists
    public Set<String> getArtists() {
        return byArtist().keySet();
    }

    // Get a all songs from a specific artist
    public List<Song> getSongsForArtist(String artistName) {
        // Create a list of songs from a specific artist
        List<Song> songs = byArtist().get(artistName);
        // Sort the songs alphabetically, using a new Comparator
        songs.sort(new Comparator<Song>() {
            // anonymous class
            @Override
            public int compare(Song song1, Song song2) {
                if (song1.equals(song2)) {
                    return 0;
                }
                // Compare songs by title
                return song1.mTitle.compareTo(song2.mTitle);
            }
        });
        return songs;
    }
}
