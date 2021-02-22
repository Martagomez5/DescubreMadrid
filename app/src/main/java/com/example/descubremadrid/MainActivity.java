 package com.example.descubremadrid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

 public class MainActivity extends AppCompatActivity {


     int count;

    final private int REQUEST_CODE_ASK_PERMISSION=111;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        solicitarPermisos();

    }

    private void solicitarPermisos(){

        //COMPRUEBA SI TIENE EL PERMISO

        int permissionLocalizacion = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION );
        int permissionInternet = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.INTERNET );

        if(permissionLocalizacion!=PackageManager.PERMISSION_GRANTED ||
                permissionInternet!=PackageManager.PERMISSION_GRANTED){

            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.INTERNET}, REQUEST_CODE_ASK_PERMISSION);

            }

            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);


        }
        else{

            finishActivity(0);
        }

    }

}