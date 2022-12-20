package com.ladswithlaptops.museical.classes;

import com.rits.cloning.Cloner;

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
        Cloner cloner = new Cloner();
        return cloner.deepClone(name);
    }

    public LinkedList<Song> getSongs() {
        Cloner cloner = new Cloner();
        return cloner.deepClone(songs);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Genre)) return false;
        Genre genre = (Genre) o;
        return name.equals(genre.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
