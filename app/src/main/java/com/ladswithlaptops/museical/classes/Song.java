package com.ladswithlaptops.museical.classes;

import java.util.LinkedList;

public class Song {

    //Data Fields:

    private String filename, title;
    private LinkedList<Artist> artists = new LinkedList<>();
    private Album album;
    private LinkedList<Genre> genres = new LinkedList<>();
    private short year;

    //Constructors:

    public Song(String filename, String title, LinkedList<Artist> artists,
                Album album, LinkedList<Genre> genres, short year) {
        this.filename = filename;
        this.title = title;
        this.artists = artists;
        this.album = album;
        this.genres = genres;
        this.year = year;
    }

    public Song(String filename, String title) {
        this.filename = filename;
        this.title = title;
        this.album = new Album("Unknown");
        this.year = -1;
    }

    //Getters:

    public String getFilename() {
        return filename;
    }

    public String getTitle() {
        return title;
    }

    public LinkedList<Artist> getArtists() {
        return artists;
    }

    public Album getAlbum() {
        return album;
    }

    public LinkedList<Genre> getGenres() {
        return genres;
    }

    public short getYear() {
        return year;
    }

    //Setters:

    public void rename(String filename, String title) {
        this.filename = filename;
        this.title = title;
    }

    public void setAlbum(Album album) {
        this.album = album;
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
