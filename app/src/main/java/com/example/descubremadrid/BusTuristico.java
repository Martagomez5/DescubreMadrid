package com.example.descubremadrid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class BusTuristico extends AppCompatActivity {

    Button btnRuta1, btnRuta2;
    TextView textViewLink;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_turistico);

        btnRuta1 = findViewById(R.id.buttonRuta1);
        btnRuta2 = findViewById(R.id.buttonRuta2);
        textViewLink = findViewById(R.id.textViewLugarWeb);

        url = "https://madrid.city-tour.com/es";

        btnRuta1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MapaRuta1.class);
                startActivity(intent);
            }
        });

        btnRuta2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(getApplicationContext(), MapaRuta2.class);
                startActivity(intent2);
            }
        });

        textViewLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);

            }
        });

    }
}