package com.ladswithlaptops.museical.classes;

import java.util.LinkedList;

public class Album {

    //Data Fields:

    private String name, albumArt;
    private LinkedList<Artist> artists = new LinkedList<>();
    private LinkedList<Song> songs = new LinkedList<>();
    private short year;

    //Constructors:

    public Album(String name, String albumArt, LinkedList<Artist> artists,
                 LinkedList<Song> songs, short year) {
        this.name = name;
        this.albumArt = albumArt;
        this.artists = artists;
        this.songs = songs;
        this.year = year;
    }

   public Album(String name) {
       this.name = name;
       this.albumArt = null;
       this.year = -1;
   }

   //Getters:

    public String getName() {
        return name;
    }

    public String getAlbumArt() {
        return albumArt;
    }

    public LinkedList<Artist> getArtists() {
        return artists;
    }

    public LinkedList<Song> getSongs() {
        return songs;
    }

    public short getYear() {
        return year;
    }

    //Setters:

    public void setName(String name) {
        this.name = name;
    }

    public void setYear(short year) {
        this.year = year;
    }

    //Add/Remove:

    public boolean addArtist(Artist artist) {
        if (! artists.contains(artist)) {
            artists.add(artist);
            return true;
        }
        return false;
    }

    public boolean addSong(Song song) {
        if (! songs.contains(song)) {
            songs.add(song);
            return true;
        }
        return false;
    }

    public boolean removeArtist(Artist artist) {
        if (artists.contains(artist)) {
            artists.remove(artist);
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
