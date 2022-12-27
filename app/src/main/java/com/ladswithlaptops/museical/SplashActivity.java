package com.ladswithlaptops.museical;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ImageView imageView = findViewById(R.id.splash_icon); // Get a reference to the image view

        ScaleAnimation scaleAnimation = new ScaleAnimation(
                0.2f, 1.00f, // Start and end scales for the x axis
                0.2f, 1.00f, // Start and end scales for the y axis
                Animation.RELATIVE_TO_SELF, 0.5f, // Pivot point of x axis is at the center of the view
                Animation.RELATIVE_TO_SELF, 0.5f); // Pivot point of y axis is at the center of the view
        scaleAnimation.setDuration(5000); // Set the duration to 2 seconds

        // Set an AnimationListener to listen for the end of the animation
        scaleAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationEnd(Animation animation) {
                // Start the new activity when the animation is finished
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}

            @Override
            public void onAnimationStart(Animation animation) {}
        });

        imageView.startAnimation(scaleAnimation); // Start the animation
    }
}