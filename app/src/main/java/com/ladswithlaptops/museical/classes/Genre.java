package com.ladswithlaptops.museical.classes;

import java.util.LinkedList;

public class Genre {

    //Data Fields:

    private String name;
    private LinkedList<Song> songs = new LinkedList<>();

    //Constructors:

    public Genre(String name, LinkedList<Song> songs) {
        this.name = name;
        this.songs = songs;
    }

    public Genre(String name) {
        this.name = name;
    }

    //Getters:

    public String getName() {
        return name;
    }

    public LinkedList<Song> getSongs() {
        return songs;
    }

    //Setters:

    public void setName(String name) {
        this.name = name;
    }

    //Add/Remove:

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
