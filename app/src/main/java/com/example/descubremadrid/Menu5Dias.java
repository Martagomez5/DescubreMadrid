package com.example.descubremadrid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class Menu5Dias extends AppCompatActivity {

    ImageButton dia1, dia2, dia3, dia4, dia5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu5_dias);

        dia1 = findViewById(R.id.imageButtondia1menu5);
        dia2 = findViewById(R.id.imageButtondia2menu5);
        dia3 = findViewById(R.id.imageButtondia3menu5);
        dia4 = findViewById(R.id.imageButtondia4menu5);
        dia5 = findViewById(R.id.imageButtondia5menu5);


        dia1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), infoItinerario1a.class);
                startActivity(intent);
            }
        });

        dia2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), infoItinerario2.class);
                startActivity(intent);
            }
        });

        dia3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), infoItinerario3.class);
                startActivity(intent);
            }
        });

        dia4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), infoItinerario4.class);
                startActivity(intent);
            }
        });

        dia5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), infoItinerario5.class);
                startActivity(intent);
            }
        });

    }
}