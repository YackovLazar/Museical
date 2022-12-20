package com.ladswithlaptops.museical.classes;

import com.rits.cloning.Cloner;
import java.util.LinkedList;

public class Song {
    private String path, title;
    private LinkedList<Artist> artists = new LinkedList<>();
    private Album album;
    private LinkedList<Genre> genres = new LinkedList<>();
    private short year;

    private Song(Builder builder) {
        this.path = builder.path;
        this.title = builder.title;
        this.artists = builder.artists;
        this.album = builder.album;
        this.genres = builder.genres;
        this.year = builder.year;
    }

    public static class Builder {
        private String path = "Unknown", title = "Unknown";
        private LinkedList<Artist> artists = new LinkedList<>();
        private Album album = new Album.Builder().build();
        private LinkedList<Genre> genres = new LinkedList<>();
        private short year = 0;

        public Builder path(String filename) {
            this.path = filename;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder artists(LinkedList<Artist> artists) {
            this.artists = artists;
            return this;
        }

        public Builder album(Album album) {
            this.album = album;
            return this;
        }

        public Builder genres(LinkedList<Genre> genres) {
            this.genres = genres;
            return this;
        }

        public Builder year(short year) {
            this.year = year;
            return this;
        }

        public Song build() {
            return new Song(this);
        }
    }

    // Getters:

    public String getPath() {
        Cloner cloner = new Cloner();
        return cloner.deepClone(path);
    }

    public String getTitle() {
        Cloner cloner = new Cloner();
        return cloner.deepClone(title);
    }

    public LinkedList<Artist> getArtists() {
        Cloner cloner = new Cloner();
        return cloner.deepClone(artists);
    }

    public Album getAlbum() {
        Cloner cloner = new Cloner();
        return cloner.deepClone(album);
    }

    public LinkedList<Genre> getGenres() {
        Cloner cloner = new Cloner();
        return cloner.deepClone(genres);
    }

    public short getYear() {
        return year;
    }

    // Setters:

    public void rename(String filename, String title) {
        this.path = filename;
        this.title = title;
    }

    public void setAlbum(Album album) {
        this.album = album;
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

    public boolean addGenre(Genre genre) {
        if (! genres.contains(genre)) {
            genres.add(genre);
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

    public boolean removeGenre(Genre genre) {
        if (genres.contains(genre)) {
            genres.remove(genre);
            return true;
        }
        return false;
    }
}

