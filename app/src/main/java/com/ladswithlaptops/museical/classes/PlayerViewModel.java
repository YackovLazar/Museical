package com.ladswithlaptops.museical.classes;

import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class PlayerViewModel extends ViewModel
{
    private Mapper music = new Mapper();

    public Mapper getMusic()
    {
        return music;
    }

    public void setMusic(Mapper music)
    {
        this.music = music;
    }

    public ArrayList<Song> getSongs()
    {
        return new ArrayList<>(Mapper.mSongs);
    }
}
