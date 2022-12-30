package com.ladswithlaptops.museical;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.ladswithlaptops.museical.classes.PlayerViewModel;
import com.ladswithlaptops.museical.classes.RecyclerViewAdapter;
import com.ladswithlaptops.museical.classes.Song;
import com.ladswithlaptops.museical.databinding.ActivityMainBinding;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;


public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    public static PlayerViewModel playerViewModel;
    private RecyclerViewAdapter.OnItemClickListener listener;

    ActivityResultLauncher<Intent> playerActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        playerViewModel = new PlayerViewModel(new Gson().fromJson(data.getStringExtra("state"), SavedStateHandle.class));
                        // Check for changes and update UI
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        ExtendedFloatingActionButton songTitle = binding.songTitle;
        FloatingActionButton playPauseButton = (FloatingActionButton) findViewById(R.id.playPause);

        playerViewModel = new ViewModelProvider(this).get(PlayerViewModel.class);

        if (savedInstanceState == null) {

            for (Field songId : R.raw.class.getFields()) {
                songId.setAccessible(true);
                try {
                    playerViewModel.addSong(this, songId.getInt(null));
                } catch (IllegalAccessException e) {
                    Log.d("SONG", songId.getName());
                }
            }

        } else {


            playerViewModel.restoreState(this);
            if (playerViewModel.wasPlaying()) {
                playerViewModel.getCurrentPlayer().start();
                playPauseButton.setImageResource(R.drawable.ic_baseline_pause_24);
            }
            if (playerViewModel.getCurrentSong() != null) {
                songTitle.setText(playerViewModel.getCurrentSong().getTitle());
                songTitle.setVisibility(View.VISIBLE);
                ImageView albumArt = (ImageView) findViewById(R.id.albumArt);
                albumArt.setImageBitmap(playerViewModel.getCurrentSong().getAlbum().getAlbumArt());
            }
        }


        RecyclerView recyclerView = findViewById(R.id.songsView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(playerViewModel.getSongs());
        recyclerView.setAdapter(recyclerViewAdapter);

        listener = position -> {
            Song selected = playerViewModel.getSongs().get(position);
            playerViewModel.setCurrent(this, position);
            songTitle.setText(selected.getTitle());
            playPauseButton.setImageResource(R.drawable.ic_baseline_pause_24);
            songTitle.setVisibility(View.VISIBLE);
            ImageView albumArt = (ImageView) findViewById(R.id.albumArt);
            albumArt.setImageBitmap(playerViewModel.getCurrentSong().getAlbum().getAlbumArt());
        };

        recyclerViewAdapter.setOnItemClickListener(listener);

        songTitle.setOnClickListener(view -> {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this, songTitle, "transition_fab");
            playerViewModel.getCurrentPlayer().pause();

            Intent intent = new Intent(MainActivity.this, Player.class);
            playerActivityResultLauncher.launch(intent);
        });

        findViewById(R.id.playPause).setOnClickListener(view ->
        {
            if (playerViewModel.getCurrentPlayer().isPlaying()) {
                playerViewModel.pause();
                playPauseButton.setImageResource(R.drawable.ic_baseline_play_arrow_24);

            } else if (playerViewModel.getCurrentSong() != null) {
                playerViewModel.play();
                playPauseButton.setImageResource(R.drawable.ic_baseline_pause_24);
            }
        });

        findViewById(R.id.previous).setOnClickListener(view ->
        {
            if (playerViewModel.getCurrentSong() != null) {
                playerViewModel.getCurrentPlayer().seekTo(0, MediaPlayer.SEEK_PREVIOUS_SYNC);
            }
        });

        findViewById(R.id.next).setOnClickListener(view ->
        {
            if (playerViewModel.getCurrentSong() != null) {
                playerViewModel.getCurrentPlayer().seekTo(playerViewModel.getCurrentPlayer().getDuration(), MediaPlayer.SEEK_NEXT_SYNC);
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        playerViewModel.saveState();
        playerViewModel.getCurrentPlayer().pause();
    }

    @Override
    protected void onSaveInstanceState(@NotNull Bundle outState) {
        super.onSaveInstanceState(outState);
        //outState.putString("PVM", new Gson().toJson(playerViewModel));
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Handle Item Selection
        switch (item.getItemId()) {
            case R.id.about:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("This is a music app by Yackov Lazar, Yoel Druxman, and Chanoach Winograd").setTitle("About")
                        .setCancelable(true).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
        }
        return super.onOptionsItemSelected(item);
    }
}