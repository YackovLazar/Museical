package com.ladswithlaptops.museical.classes;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;

import android.util.Log;

import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ladswithlaptops.museical.MainActivity;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PlayerViewModel extends ViewModel
{
    private ArrayList<Song> music = new ArrayList<>();

    private final SavedStateHandle state;
    protected MediaPlayer currentPlayer = new MediaPlayer();
    protected Song currentSong;
    protected Bitmap currentIcon;

    public static boolean added = false;

    public PlayerViewModel(SavedStateHandle savedStateHandle)
    {
        state = savedStateHandle;
    }

    public ArrayList<Song> getMusic()
    {
        return music;
    }

    public void addSong(Context context, int songId) {
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        // set the data source to the resource ID of the MP3 file
        mmr.setDataSource(context, Uri.parse("android.resource://" + context.getPackageName() + "/" + songId));
        // get the artist, album, and title
        String artist = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);
        String album = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM);
        String title = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
        Bitmap art = null;
        if (!(mmr.getEmbeddedPicture() == null))
        {
            art = BitmapFactory.decodeByteArray(mmr.getEmbeddedPicture(), 0, mmr.getEmbeddedPicture().length);
        }
        Song song = new Song.Builder(songId).album(new Album.Builder().title(album).albumArt(art).build()).title(title).build();
        song.addArtist(new Artist(artist));
        Log.d("MUSIC", "Artist: " + artist + "; Album: " + album + "; Title: " + title);
        music.add(song);
    }

    public ArrayList<Song> getSongs()
    {
        return music;
    }

    public void setCurrent(Context context, int position)
    {
        currentPlayer.release();
        currentPlayer = MediaPlayer.create(context, Uri.parse("android.resource://" + context.getPackageName() + "/" + music.get(position).getResId()));
        currentPlayer.setLooping(true);
        currentPlayer.start();
        currentSong = music.get(position);
        currentIcon = currentSong.getAlbum().getAlbumArt();
    }

    public MediaPlayer getCurrentPlayer()
    {
        return this.currentPlayer;
    }

    public void play()
    {
        currentPlayer.start();
        Log.d("PLAYER", "PLAY");
    }

    public void pause()
    {
        currentPlayer.pause();
        Log.d("PLAYER", "PAUSED");
    }

    public Song getCurrentSong()
    {
        return currentSong;
    }

    public void saveState()
    {
        state.set("music", music);
        state.set("location", currentPlayer.getCurrentPosition());
        state.set("currentSong", new Gson().toJson(currentSong));
        state.set("added", added);
        state.set("wasPlaying", currentPlayer.isPlaying());
        state.set("icon", this.currentIcon);
    }

    public void restoreState(Context context)
    {
        this.music = state.get("music");
        this.currentSong = new Gson().fromJson((String) state.get("currentSong"), Song.class);
        if (currentSong != null)
        {
            this.currentPlayer = MediaPlayer.create(context, currentSong.getResId());
            currentPlayer.seekTo(state.get("location"));
        }
        added = Boolean.TRUE.equals(state.get("added"));
        this.currentIcon = state.get("icon");
    }

    public boolean wasPlaying()
    {
        return Boolean.TRUE.equals(state.get("wasPlaying"));
    }

    public SavedStateHandle getState()
    {
        return state;
    }

    public static String getGSONStringFromList (List<Song> list)
    {
        Gson gson = new Gson ();
        return gson.toJson (list);
    }

    public static ArrayList<Song> getListFromGSONString (String strList)
    {
        Gson gson = new Gson ();
        Type gematriaRVItemType = new TypeToken<ArrayList<Song>>()
        {
        }.getType ();
        return gson.fromJson (strList, gematriaRVItemType);
    }

    public Bitmap getCurrentIcon()
    {
        return currentIcon;
    }
}
