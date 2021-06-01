 package com.example.descubremadrid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
     public void barra(){

        final Timer t = new Timer();
         TimerTask tt = new TimerTask() {
             @Override
             public void run() {
                 count++;
                 pb.setProgress(count);
                 if(count==100){

                     SharedPreferences prefs =
                             getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);

                     int validacion = prefs.getInt("Estado",0);

                     if(validacion == 0) {
                         Intent intent = new Intent(MainActivity.this, Privacidad.class);
                         startActivity(intent);
                         t.cancel();
                         finish();
                     }
                     else{
                         Intent intent = new Intent(MainActivity.this, Login.class);
                         startActivity(intent);
                         t.cancel();
                         finish();
                     }
                 }

             }

         };

         t.schedule(tt,0,30);

     }

}