package com.example.geotagapp;

import android.content.Intent;
import android.graphics.*;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.Button;
import android.widget.Toast;
import android.widget.EditText;

public class gallery extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    private ImageView selectedImageView;
    private Button ChangeImageButton;
    private Button ApplyOverlayButton;
    private Uri selectedImageUri;

    private EditText titleEditText, locationEditText, latitudeEditText, longitudeEditText, dateEditText, timeEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        selectedImageView = findViewById(R.id.selectedImageView);
        ChangeImageButton = findViewById(R.id.changeimagebutton);
        ApplyOverlayButton = findViewById(R.id.applyOverlayButton);

        titleEditText = findViewById(R.id.titleEditText);
        locationEditText = findViewById(R.id.locationEditText);
        latitudeEditText = findViewById(R.id.latitudeEditText);
        longitudeEditText = findViewById(R.id.longitudeEditText);
        dateEditText = findViewById(R.id.dateEditText);
        timeEditText = findViewById(R.id.timeEditText);

        ChangeImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeImage();
            }
        });

        ApplyOverlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                applyOverlay();
            }
        });

        openGallery();
    }

    private void changeImage() {
        openGallery();
    }

    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            selectedImageUri = data.getData();
            if (selectedImageUri != null) {
                // Set the selected image to ImageView
                selectedImageView.setImageURI(selectedImageUri);
            }
        }
    }

    public void applyOverlay() {
        // Reset ImageView to original image without overlay
        selectedImageView.setImageURI(selectedImageUri); // Replace with your default image resource if needed

        // Retrieve values from EditText fields
        String title = titleEditText.getText().toString();
        String location = locationEditText.getText().toString();
        String latitude = latitudeEditText.getText().toString();
        String longitude = longitudeEditText.getText().toString();
        String date = dateEditText.getText().toString();
        String time = timeEditText.getText().toString();

        // Validate if all fields are filled
        if (title.isEmpty() || location.isEmpty() || latitude.isEmpty() || longitude.isEmpty() || date.isEmpty() || time.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Convert EditText values to a single string for overlay
        String overlayText = "Title: " + title + "\nLocation: " + location + "\nLat: " + latitude + "\nLong: " + longitude + "\nDate: " + date + "\nTime: " + time;

        // Convert ImageView to Bitmap
        BitmapDrawable drawable = (BitmapDrawable) selectedImageView.getDrawable();
        Bitmap imageBitmap = drawable.getBitmap();

        // Create a mutable bitmap to draw onto
        Bitmap mutableBitmap = imageBitmap.copy(Bitmap.Config.ARGB_8888, true);

        // Create a canvas to draw on the bitmap
        Canvas canvas = new Canvas(mutableBitmap);

        // Paint for overlay background (translucent black)
        Paint overlayPaint = new Paint();
        overlayPaint.setColor(Color.BLACK);
        overlayPaint.setAlpha(178); // Adjust alpha for opacity (178 is approximately 70% opacity)

        // Paint for text
        Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(Color.WHITE);

        // Calculate text size based on image size
        float textSize = imageBitmap.getHeight() / 30f; // Example: 1/30th of the image height
        textPaint.setTextSize(textSize);

        // Padding values
        float padding = 20; // Adjust as needed

        // Calculate the height of the overlay background based on the number of lines and text size
        String[] lines = overlayText.split("\n");
        float lineHeight = textPaint.descent() - textPaint.ascent();
        float overlayHeight = lineHeight * lines.length + padding * 2; // Add padding

        // Draw translucent black background at the bottom
        RectF backgroundRect = new RectF(0, mutableBitmap.getHeight() - overlayHeight, mutableBitmap.getWidth(), mutableBitmap.getHeight());
        canvas.drawRect(backgroundRect, overlayPaint);

        // Calculate text position and draw each line of text
        float x = padding; // X-coordinate for text
        float y = mutableBitmap.getHeight() - overlayHeight + padding + textSize; // Y-coordinate for text, starting from bottom

        // Draw each line of text on the canvas
        for (String line : lines) {
            canvas.drawText(line, x, y, textPaint);
            y += lineHeight; // Move to the next line
        }

        // Display the mutable bitmap with overlay in ImageView
        selectedImageView.setImageBitmap(mutableBitmap);

        // Example: Display a toast message for demonstration
        Toast.makeText(this, "Geotag overlay applied successfully", Toast.LENGTH_SHORT).show();
    }
}
