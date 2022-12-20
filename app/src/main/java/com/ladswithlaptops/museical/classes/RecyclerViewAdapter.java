package com.ladswithlaptops.museical.classes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ladswithlaptops.museical.R;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {
    private List<Song> mSongs;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView mAlbumArtImageView;
        public TextView mSongNameTextView;
        public TextView mArtistTextView;
        public TextView mAlbumTextView;

        public MyViewHolder(View view) {
            super(view);
            mAlbumArtImageView = view.findViewById(R.id.image_view_album_art);
            mSongNameTextView = view.findViewById(R.id.text_view_song_name);
            mArtistTextView = view.findViewById(R.id.text_view_artist);
            mAlbumTextView = view.findViewById(R.id.text_view_album);
        }
    }

    public RecyclerViewAdapter(List<Song> songs) {
        mSongs = songs;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.song_view, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Song song = mSongs.get(position);
        holder.mSongNameTextView.setText(song.getTitle());
        holder.mArtistTextView.setText(song.getArtists().toString());
        holder.mAlbumTextView.setText(song.getAlbum().getTitle());
        // Load the album art into the ImageView using a library such as Glide
        Glide.with(holder.mAlbumArtImageView.getContext())
                .load(song.getAlbum().getAlbumArt())
                .into(holder.mAlbumArtImageView);
    }

    @Override
    public int getItemCount() {
        return mSongs.size();
    }
}
