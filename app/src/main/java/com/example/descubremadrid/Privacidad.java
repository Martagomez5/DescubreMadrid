package com.example.descubremadrid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Privacidad extends AppCompatActivity {
    Button aceptar, cancelar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacidad);
        aceptar = findViewById(R.id.btnAceptarP);
        cancelar = findViewById(R.id.btnCancelar);

        aceptar.setOnClickListener(this::onClick);
        cancelar.setOnClickListener(this::onClick);
    }

    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.btnAceptarP:
                startActivity(new Intent(Privacidad.this, Login.class));
                //Empieza a cargar la proxima interfaz
                SharedPreferences prefs =
                        getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putInt("Estado", 1);
                //Crea un archivo al que solo tendra acceso la aplicacion
                // y donde guardara el cambio de estado

                editor.commit();
                break;

            case R.id.btnCancelar:
                finish();
                break;

        }


    }
}