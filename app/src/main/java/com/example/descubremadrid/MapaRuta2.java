package com.example.descubremadrid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import uk.co.senab.photoview.PhotoViewAttacher;

public class MapaRuta2 extends AppCompatActivity {

    ImageView imageView;
    PhotoViewAttacher photoViewAttacher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa_ruta2);

        imageView = (ImageView) findViewById(R.id.imageViewRuta2);
        photoViewAttacher = new PhotoViewAttacher(imageView);

    }
}