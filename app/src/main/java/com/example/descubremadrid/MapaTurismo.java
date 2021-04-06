package com.example.descubremadrid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import uk.co.senab.photoview.PhotoViewAttacher;

public class MapaTurismo extends AppCompatActivity {

    ImageView imageView;
    PhotoViewAttacher photoViewAttacher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa_turismo);


        imageView = (ImageView) findViewById(R.id.imageViewTurismo);

        photoViewAttacher = new PhotoViewAttacher(imageView);
    }
}