package com.ladswithlaptops.museical;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.shape.CornerFamily;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.gson.Gson;
import com.ladswithlaptops.museical.classes.PlayerViewModel;

import org.jetbrains.annotations.NotNull;

public class Player extends AppCompatActivity {
    
    PlayerViewModel playerViewModel = MainActivity.playerViewModel;
    FloatingActionButton playPauseButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        playerViewModel.restoreState(this);
        
        ImageView albumArt = (ImageView) findViewById(R.id.albumArt);
        playPauseButton = (FloatingActionButton) findViewById(R.id.playPause);
        albumArt.setImageBitmap(playerViewModel.getCurrentIcon());


        // makes the toolbar rounded
        roundToolbar();


        playPauseButton.setOnClickListener(view ->
        {
            if (playerViewModel.getCurrentPlayer().isPlaying()) {
                playerViewModel.pause();
                playPauseButton.setImageResource(R.drawable.ic_baseline_play_arrow_24);

            } else if (playerViewModel.getCurrentSong() != null){
                playerViewModel.play();
                playPauseButton.setImageResource(R.drawable.ic_baseline_pause_24);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("MP_STATE", "isPlaying: " + String.valueOf(playerViewModel.getCurrentPlayer().isPlaying()));
        playerViewModel.play();
        Log.d("MP_STATE", "isPlaying: " + String.valueOf(playerViewModel.getCurrentPlayer().isPlaying()));
    }

    private void roundToolbar() {
        float radius = 50;
        MaterialToolbar toolbar = (MaterialToolbar) findViewById(R.id.materialToolbar);

        MaterialShapeDrawable materialShapeDrawable = (MaterialShapeDrawable) toolbar.getBackground();
        materialShapeDrawable.setShapeAppearanceModel(materialShapeDrawable.getShapeAppearanceModel().toBuilder().setAllCorners(CornerFamily.ROUNDED, radius).build());
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
        outState.putString("PVM", new Gson().toJson(playerViewModel));
    }

    @Override
    public void onBackPressed()
    {
        playerViewModel.saveState();
        playerViewModel.getCurrentPlayer().pause();
        super.onBackPressed();
    }

    @Override
    public void finish()
    {
        Intent intent = new Intent();
        intent.putExtra("state", new Gson().toJson(playerViewModel.getState()));
        setResult(Activity.RESULT_OK, intent);
        super.finish();
    }

}