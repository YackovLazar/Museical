package com.ladswithlaptops.museical.classes;

import com.rits.cloning.Cloner;

import java.util.LinkedList;

public class Artist {

    //Data Fields:

    private String name;
    private LinkedList<Song> songs = new LinkedList<>();

    //Constructor:

    public Artist(String name, LinkedList<Song> songs) {
        this.name = name;
        this.songs = songs;
    }

    public Artist(String name) {
        this.name = name;
    }

    // Getters:

    public String getName() {
        Cloner cloner = new Cloner();
        return cloner.deepClone(name);

    }

    public LinkedList<Song> getSongs() {
        Cloner cloner = new Cloner();
        return cloner.deepClone(songs);
    }

    // Setters:

    public void setName(String name) {
        this.name = name;
    }

    // Add/Remove:

    public boolean addSong(Song song) {
        if (! songs.contains(song)) {
            songs.add(song);
            return true;
        }
        return false;
    }

    public boolean removeSong(Song song) {
        if (songs.contains(song)) {
            songs.remove(song);
            return true;
        }
        return false;
    }
}
