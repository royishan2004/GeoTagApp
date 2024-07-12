package com.example.geotagapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;

public class HomePage extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        Button CaptureLink = findViewById(R.id.capturelink);
        CaptureLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startcapture();
            }
        });

        Button GalleryLink = findViewById(R.id.gallerylink);
        GalleryLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startgallery();
            }
        });
    }

    private void startcapture() {
        Intent intent = new Intent(this, capture.class);
        startActivity(intent);
    }

    private void startgallery() {
        Intent intent = new Intent(this, gallery.class);
        startActivity(intent);
    }
}
