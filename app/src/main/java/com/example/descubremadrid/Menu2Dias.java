package com.example.descubremadrid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class Menu2Dias extends AppCompatActivity {

    ImageButton dia1, dia2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu2_dias);

        dia1 = findViewById(R.id.imageButtondia1menu2);
        dia2 = findViewById(R.id.imageButtondia2menu2);


        dia1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),infoItinerario1.class);
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

    }
}