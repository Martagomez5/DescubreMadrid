package com.example.descubremadrid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class MenuMapas extends AppCompatActivity {

    ImageButton btnMetro,btnCercanias,btnTurismo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_mapas);

        btnMetro=findViewById(R.id.btnMetro);
        btnCercanias=findViewById(R.id.btnCercanias);
        btnTurismo=findViewById(R.id.btnTurismo);

        btnMetro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), MapaMetro.class);
                startActivity(intent);

            }
        });

        btnCercanias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), MapaCercanias.class);
                startActivity(intent);

            }
        });

        btnTurismo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), MapaTurismo.class);
                startActivity(intent);

            }
        });
    }
}