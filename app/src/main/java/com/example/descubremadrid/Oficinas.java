package com.example.descubremadrid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Oficinas extends AppCompatActivity {

    Button btnTelefono;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oficinas);

        btnTelefono=findViewById(R.id.buttonTelefono);


        btnTelefono.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(Intent.ACTION_CALL, Uri.parse("tel: 915787810"));
                if (ActivityCompat.checkSelfPermission(Oficinas.this, Manifest.permission.CALL_PHONE)!=
                        PackageManager.PERMISSION_GRANTED)return;
                startActivity(intent2);
            }
        });

    }
}