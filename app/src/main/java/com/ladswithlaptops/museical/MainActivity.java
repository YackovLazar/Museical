package com.ladswithlaptops.museical;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.ladswithlaptops.museical.databinding.ActivityMainBinding;
import com.ladswithlaptops.museical.ui.main.SectionsPagerAdapter;

public class MainActivity extends AppCompatActivity
{

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = binding.viewPager;
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = binding.tabs;
        tabs.setupWithViewPager(viewPager);
        ExtendedFloatingActionButton songTitle = binding.songTitle;

        songTitle.setOnClickListener(view -> {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this, songTitle, "transition_fab");
            Intent intent = new Intent(MainActivity.this, Player.class);
            startActivity(intent, options.toBundle());
            Log.d("FAB", "onClick: CLICKED");
       });
    }
}