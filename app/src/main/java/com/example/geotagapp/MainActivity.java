package com.example.geotagapp;

import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.os.Handler;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView logo = findViewById(R.id.logo);
        Animation logofadein = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade);
        logo.startAnimation(logofadein);

        // Add 3 second pause here
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                starthomepage();
            }
        }, 3000); // 3000 milliseconds = 3 seconds
    }

    private void starthomepage() {
        Intent intent = new Intent(this, HomePage.class);
        startActivity(intent);
    }
}
