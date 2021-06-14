package com.binduhait.instagram;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class PhotoViewer extends AppCompatActivity {

    private String incoming;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_viewer);

        ImageView snappyImageViewer = findViewById(R.id.photo_view);
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.ic_profile);
        requestOptions.fitCenter();

        initWidgets();

        Glide.with(getApplicationContext()).applyDefaultRequestOptions(requestOptions).
                load(incoming).into(snappyImageViewer);
    }

    private void initWidgets() {
        incoming = getIntent().getStringExtra("image");
        if (incoming == null) {
            finish();
        }
    }
}