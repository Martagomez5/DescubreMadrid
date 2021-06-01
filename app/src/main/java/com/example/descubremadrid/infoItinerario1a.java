package com.example.descubremadrid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class infoItinerario1a extends AppCompatActivity {

        Button btnRuta;
    private static final int REQUEST_PERMISSION_LOCA = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_itinerario1a);

        btnRuta=findViewById(R.id.buttonRuta1adia);
        if (Build.VERSION.SDK_INT<Build.VERSION_CODES.M){
            btnRuta.setEnabled(true);
        }else{
            Log.i("TAG", "API >=23");
            if (ContextCompat.checkSelfPermission(infoItinerario1a.this, Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){
                Log.i("TAG", "Permiso concedido");
                if(isGPSProvider(getApplicationContext())==true){
                    btnRuta.setEnabled(true);
                    //Toast.makeText(getApplicationContext(),"activado",Toast.LENGTH_LONG).show();
                }else{


                    final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setMessage("¿El gps esta desactivado lo desea activar?,Necesita activar el GPS para iniciar la ruta")
                            .setCancelable(false)
                            .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(@SuppressWarnings("unused") final DialogInterface dialogInterface, @SuppressWarnings("unused") final int i) {
                                    startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                                    if(isGPSProvider(getApplicationContext())==false){
                                        btnRuta.setEnabled(false);
                                    }
                                    btnRuta.setEnabled(true);

                                }
                            })
                            .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, @SuppressWarnings("unused") final int i) {
                                    btnRuta.setEnabled(false);
                                    dialogInterface.cancel();

                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();



                }

            }else{
                if (ActivityCompat.shouldShowRequestPermissionRationale(infoItinerario1a.this, Manifest.permission.ACCESS_FINE_LOCATION)){
                    Log.i("TAG", "Usuario ha rechazado el permiso");

                }else {
                    Log.i("TAG", "Permiso concedido");
                }
                ActivityCompat.requestPermissions(infoItinerario1a.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_PERMISSION_LOCA);
            }
        }

        btnRuta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),Ruta1a.class);
                startActivity(intent);
            }
        });



    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_PERMISSION_LOCA){
            if (permissions.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Log.i("TAG", "Permiso permitido");

                //button.setEnabled(true);
                if(isGPSProvider(getApplicationContext())==true){
                    btnRuta.setEnabled(true);
                    //Toast.makeText(getApplicationContext(),"activado",Toast.LENGTH_LONG).show();
                }else{


                    final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setMessage("¿El gps esta desactivado lo desea activar?,Necesita activar el GPS para iniciar la ruta")
                            .setCancelable(false)
                            .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(@SuppressWarnings("unused") final DialogInterface dialogInterface, @SuppressWarnings("unused") final int i) {
                                    startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                                    if(isGPSProvider(getApplicationContext())==false){
                                        btnRuta.setEnabled(false);
                                    }
                                    btnRuta.setEnabled(true);

                                }
                            })
                            .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, @SuppressWarnings("unused") final int i) {
                                    btnRuta.setEnabled(false);
                                    dialogInterface.cancel();

                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();



                }

            }else {
                Log.i("TAG", "Permiso denegado" );
                if (ActivityCompat.shouldShowRequestPermissionRationale(infoItinerario1a.this, Manifest.permission.ACCESS_FINE_LOCATION)){
                    new androidx.appcompat.app.AlertDialog.Builder(this).setMessage("Necesitas aceptar el permiso para poder iniciar ruta")
                            .setPositiveButton("Intentalo de Nuevo", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    ActivityCompat.requestPermissions(infoItinerario1a.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_PERMISSION_LOCA);
                                    //localizacion();
                                    //button.setEnabled(true);

                                }
                            })
                            .setNegativeButton("No Gracias", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    btnRuta.setEnabled(false);
                                    Log.i("TAG", "Dejado" );
                                }
                            }).show();
                }
            }

        }


        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
    public static boolean isGPSProvider(Context context) {
        LocationManager lm = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
        return lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }


}