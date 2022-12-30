package com.ladswithlaptops.museical;

import android.graphics.Bitmap;

public class SongView {
    private final Bitmap albumArt;
    private final String songName;
    private final String artist;
    private final String album;

    public SongView(Bitmap albumArt, String songName, String artist, String album) {
        this.albumArt = albumArt;
        this.songName = songName;
        this.artist = artist;
        this.album = album;
    }

    public Bitmap getAlbumArt() {
        return albumArt;
    }

    public String getSongName() {
        return songName;
    }

    public String getArtist() {
        return artist;
    }

    public String getAlbum() {
        return album;
    }
}
