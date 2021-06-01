package com.example.descubremadrid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Oficinas extends AppCompatActivity {

    Button btnTelefono;

    String telefono = "915787810";

    private static final int REQUEST_PERMISSION_CALL = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oficinas);

        btnTelefono=findViewById(R.id.buttonTelefono);





        btnTelefono.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Build.VERSION.SDK_INT<Build.VERSION_CODES.M){
                    call(telefono);
                }else{
                    Log.i("TAG", "API >=23");
                    if (ContextCompat.checkSelfPermission(Oficinas.this, Manifest.permission.CALL_PHONE)==PackageManager.PERMISSION_GRANTED){
                        Log.i("TAG", "Permiso concedido");
                        call(telefono);
                    }else{
                        if (ActivityCompat.shouldShowRequestPermissionRationale(Oficinas.this, Manifest.permission.CALL_PHONE)){
                            btnTelefono.setEnabled(false);
                            Log.i("TAG", "Usuario ha rechazado el permiso");

                        }else {
                            Log.i("TAG", "Permiso concedido");
                        }
                        ActivityCompat.requestPermissions(Oficinas.this, new String[]{Manifest.permission.CALL_PHONE},REQUEST_PERMISSION_CALL);
                    }
                }


              /*  Intent intent2 = new Intent(Intent.ACTION_CALL, Uri.parse("tel: 915787810"));
                if (ActivityCompat.checkSelfPermission(Oficinas.this, Manifest.permission.CALL_PHONE)!=
                        PackageManager.PERMISSION_GRANTED)return;
                startActivity(intent2);*/
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == REQUEST_PERMISSION_CALL){
            if(permissions.length >0 && grantResults[0] == PackageManager.PERMISSION_DENIED){
                btnTelefono.setEnabled(false);
            }
            if (permissions.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Log.i("TAG", "Permiso permitido");
                btnTelefono.setEnabled(true);
                call(telefono);
            }else {
                Log.i("TAG", "Permiso denegado" );
                if (ActivityCompat.shouldShowRequestPermissionRationale(Oficinas.this, Manifest.permission.CALL_PHONE)){
                    new AlertDialog.Builder(this).setMessage("Necesitas aceptar el permiso para poder llamar")
                            .setPositiveButton("Intentalo de Nuevo", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    ActivityCompat.requestPermissions(Oficinas.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_PERMISSION_CALL);
                                }
                            })
                            .setNegativeButton("No Gracias", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    btnTelefono.setEnabled(false);
                                    Log.i("TAG", "Dejado" );
                                }
                            }).show();
                }
            }

        }


        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void call(String telefono) {
         Intent intent2 = new Intent(Intent.ACTION_CALL, Uri.parse("tel: 915787810"));
                if (ActivityCompat.checkSelfPermission(Oficinas.this, Manifest.permission.CALL_PHONE)!=
                        PackageManager.PERMISSION_GRANTED)return;
                startActivity(intent2);
    }
}