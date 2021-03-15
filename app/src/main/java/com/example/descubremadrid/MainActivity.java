 package com.example.descubremadrid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.animation.DecelerateInterpolator;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

 public class MainActivity extends AppCompatActivity {


     int count;
     ProgressBar pb;




     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         pb = (ProgressBar)findViewById(R.id.progressBar);

         pb.setScaleY(6f);

        barra();



    }

    //No se utiliza, permisos gps
    /*private void solicitarPermisos(){

        //COMPRUEBA SI TIENE EL PERMISO

        int permissionLocalizacion = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION );
        int permissionInternet = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.INTERNET );

        if(permissionLocalizacion!=PackageManager.PERMISSION_GRANTED ||
                permissionInternet!=PackageManager.PERMISSION_GRANTED){

            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.INTERNET}, REQUEST_CODE_ASK_PERMISSION);

            }

            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);


        }
        else{

            finishActivity(0);
        }

    }*/

     public void barra(){



        final Timer t = new Timer();
         TimerTask tt = new TimerTask() {
             @Override
             public void run() {
                 count++;
                 pb.setProgress(count);
                 if(count==100){

                     Intent intent = new Intent(getApplicationContext(), Login.class);
                     startActivity(intent);
                     t.cancel();
                 }

             }

         };

         t.schedule(tt,0,30);


     }



}