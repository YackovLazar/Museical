package com.ladswithlaptops.museical.classes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ladswithlaptops.museical.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> implements Serializable
{

    private List<Song> mSongs;
    private OnItemClickListener listener;

    public interface OnItemClickListener
    {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener)
    {
        this.listener = listener;
    }

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

    public RecyclerViewAdapter(ArrayList<Song> songs)
    {
        mSongs = songs;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.song_view, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position)
    {
        Song current = mSongs.get(holder.getAdapterPosition());
        holder.mSongNameTextView.setText(current.getTitle());
        holder.mArtistTextView.setText(current.getArtists().getFirst().getName());
        holder.mAlbumTextView.setText(current.getAlbum().getTitle());
        // Load the album art into the ImageView using a library such as Glide
        Glide.with(holder.mAlbumArtImageView.getContext())
                .load(current.getAlbum().getAlbumArt())
                .into(holder.mAlbumArtImageView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(holder.getAdapterPosition());

            }
        });
    }

    @Override
    public int getItemCount() {
        return mSongs.size();
    }
}
