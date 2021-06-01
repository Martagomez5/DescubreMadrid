package com.example.descubremadrid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class Menu3Dias extends AppCompatActivity {

    ImageButton dia1, dia2, dia3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu3_dias);

        dia1 = findViewById(R.id.imageButtondia1menu3);
        dia2 = findViewById(R.id.imageButtondia2menu3);
        dia3 = findViewById(R.id.imageButtondia3menu3);


        dia1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),infoItinerario1a.class);
                startActivity(intent);
            }
        });

        dia2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2=new Intent(getApplicationContext(),infoItinerario2.class);
                startActivity(intent2);
            }
        });

        dia3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent3=new Intent(getApplicationContext(),infoItinerario3.class);
                startActivity(intent3);
            }
        });


    }
}