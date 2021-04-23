package com.example.descubremadrid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class menuprincipal extends AppCompatActivity {

    ImageButton btnLugares,btnPlanos,btnItinarios, btnOficinaTurismo, btnMadridCard, btnBusTuristico, btnLugaresVistos, btnLugaresQuererVer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manuprincipal);

        btnLugares=findViewById(R.id.btnLugares);
        btnPlanos=findViewById(R.id.btnPlanos);
        btnItinarios=findViewById(R.id.btnItinerario);
        btnOficinaTurismo=findViewById(R.id.btnOficinasTurismo);
        btnMadridCard=findViewById(R.id.btnMadridCard);
        btnBusTuristico=findViewById(R.id.btnBusTuristico);
        btnLugaresVistos=findViewById(R.id.btnVistos);
        btnLugaresQuererVer=findViewById(R.id.btnQuererVer);


        btnLugares.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), Lugar.class);
                startActivity(intent);

            }
        });

        btnPlanos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), MenuMapas.class);
                startActivity(intent);
            }
        });

        btnItinarios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnOficinaTurismo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), Oficinas.class);
                startActivity(intent);

            }
        });

        btnMadridCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TarjetaTransporte.class);
                startActivity(intent);

            }
        });

        btnBusTuristico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), BusTuristico.class);
                startActivity(intent);

            }
        });

        btnLugaresVistos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnLugaresQuererVer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }


    public void onBackPressed() {
        moveTaskToBack(true);
    }



}