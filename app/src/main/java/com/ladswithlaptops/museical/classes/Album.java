package com.ladswithlaptops.museical.classes;

import android.graphics.Bitmap;

import java.util.LinkedList;

/*public class Album {

    //Data Fields:

    private String name;
    private Bitmap albumArt;
    private LinkedList<Artist> artists = new LinkedList<>();
    private LinkedList<Song> songs = new LinkedList<>();
    private short year;

    //Constructors:

    public Album(String name, Bitmap albumArt, LinkedList<Artist> artists,
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

    public Bitmap getAlbumArt() {
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
}*/

public class Album {
    private String title;
    private Bitmap albumArt;
    private LinkedList<Artist> artists = new LinkedList<>();
    private LinkedList<Song> songs = new LinkedList<>();
    private short year;

    private Album(Builder builder) {
        this.title = builder.title;
        this.albumArt = builder.albumArt;
        this.artists = builder.artists;
        this.songs = builder.songs;
        this.year = builder.year;
    }

    public static class Builder {
        private String title = "Unknown";
        private Bitmap albumArt = null;
        private LinkedList<Artist> artists = new LinkedList<>();
        private LinkedList<Song> songs = new LinkedList<>();
        private short year = 0;

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder albumArt(Bitmap albumArt) {
            this.albumArt = albumArt;
            return this;
        }

        public Builder artists(LinkedList<Artist> artists) {
            this.artists = artists;
            return this;
        }

        public Builder songs(LinkedList<Song> songs) {
            this.songs = songs;
            return this;
        }

        public Builder year(short year) {
            this.year = year;
            return this;
        }

        public Album build() {
            return new Album(this);
        }
    }

    // Getters:

    public String getTitle() {
        return title;
    }

    public Bitmap getAlbumArt() {
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

    public void setTitle(String title) {
        this.title = title;
    }

    public void setYear(short year) {
        this.year = year;
    }

    // Add/Remove:

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

