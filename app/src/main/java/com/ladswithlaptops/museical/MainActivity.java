package com.ladswithlaptops.museical;

import android.Manifest;
import android.app.ActivityOptions;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.provider.Settings;
import android.widget.Toast;

import com.ladswithlaptops.museical.classes.Importer;
import com.ladswithlaptops.museical.databinding.ActivityMainBinding;
import com.ladswithlaptops.museical.ui.main.SectionsPagerAdapter;

public class MainActivity extends AppCompatActivity
{

    private ActivityMainBinding binding;
    private static final int REQUEST_CODE_STORAGE_PERMISSIONS = 1;
    private static final String[] STORAGE_PERMISSIONS = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
    };

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
       });


        // Check if the necessary permissions have been granted
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            // Request the permissions if they have not been granted
            ActivityCompat.requestPermissions(this, STORAGE_PERMISSIONS, REQUEST_CODE_STORAGE_PERMISSIONS);
        } else {
            // Proceed with the import if the permissions have already been granted
            int filesCopied = Importer.moveFiles(this, "music_folder", "res/raw");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CODE_STORAGE_PERMISSIONS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Proceed with the import if the read storage permission has been granted
                int filesCopied = Importer.moveFiles(this, "music_folder", "");
            } else {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    // Show a message to the user explaining why the read storage permission is needed
                    Toast.makeText(this, "Read storage permission is required to import music.", Toast.LENGTH_LONG).show();
                    // Re-request the read storage permission
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_STORAGE_PERMISSIONS);
                } else {
                    // Show a message to the user explaining how to manually grant the read storage permission
                    Toast.makeText(this, "Please grant read storage permission in the app settings.", Toast.LENGTH_LONG).show();
                    // Create an intent to open the app settings
                    Intent intent = new Intent();
                    intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package", getPackageName(), null);
                    intent.setData(uri);
                    startActivity(intent);
                }
            }
        }
    }


}