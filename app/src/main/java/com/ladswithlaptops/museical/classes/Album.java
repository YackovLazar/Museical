package com.ladswithlaptops.museical.classes;

import android.graphics.Bitmap;

import com.rits.cloning.Cloner;

import java.util.LinkedList;

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
            if (!(title == null || title.equals("")))
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
        Cloner cloner = new Cloner();
        return cloner.deepClone(title);        
    }

    public Bitmap getAlbumArt() {
        Cloner cloner = new Cloner();
        return cloner.deepClone(albumArt);
    }

    public LinkedList<Artist> getArtists() {
        Cloner cloner = new Cloner();
        return cloner.deepClone(artists);

    }

    public LinkedList<Song> getSongs() {
        Cloner cloner = new Cloner();
        return cloner.deepClone(songs);
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

