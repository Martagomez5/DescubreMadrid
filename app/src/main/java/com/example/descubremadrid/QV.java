package com.example.descubremadrid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.mapboxsdk.location.LocationComponent;
import com.mapbox.mapboxsdk.location.LocationComponentActivationOptions;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.plugins.locationlayer.modes.CameraMode;
import com.mapbox.mapboxsdk.plugins.locationlayer.modes.RenderMode;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.sql.BatchUpdateException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QV extends AppCompatActivity {

    RecyclerView recyclerViewqv;
    ArrayList<listaQV> elementosqv;
    adaptadorQV adaptadorQV;
    PermissionsManager permissionsManager;

    String idPersona;
    Button button,botonInfo;
    ArrayList<String> prueba2;
    LocationComponent locationComponent;
    private static final int REQUEST_PERMISSION_LOCA = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_q_v);

        Intent intent = getIntent();
        idPersona = intent.getStringExtra("idPersona");
        button=findViewById(R.id.button11);
        botonInfo=findViewById(R.id.button12);

        //localizacion();
        if (Build.VERSION.SDK_INT<Build.VERSION_CODES.M){
            button.setEnabled(true);
        }else{
            Log.i("TAG", "API >=23");
            if (ContextCompat.checkSelfPermission(QV.this, Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED){
                Log.i("TAG", "Permiso concedido");
                if(isGPSProvider(getApplicationContext())==true){
                    button.setEnabled(true);
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
                                        button.setEnabled(false);
                                    }
                                    button.setEnabled(true);

                                }
                            })
                            .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, @SuppressWarnings("unused") final int i) {
                                    button.setEnabled(false);
                                    dialogInterface.cancel();

                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();



                }

            }else{
                if (ActivityCompat.shouldShowRequestPermissionRationale(QV.this, Manifest.permission.ACCESS_FINE_LOCATION)){
                    Log.i("TAG", "Usuario ha rechazado el permiso");

                }else {
                    Log.i("TAG", "Permiso concedido");
                }
                ActivityCompat.requestPermissions(QV.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_PERMISSION_LOCA);
            }
        }

        iniciar();

        cargarLugares();




        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Toast.makeText(getApplicationContext(),"eoooooooooooooooooo"+adaptadorQV.stops,Toast.LENGTH_LONG).show();
                if(adaptadorQV.stops.size()==0 || adaptadorQV.stops.isEmpty()){
                    Toast.makeText(getApplicationContext(),"No se puede inicar la ruta no ha indicado ningun lugar",Toast.LENGTH_LONG).show();
                }else{
                 Intent intent= new Intent(getApplicationContext(),navegacionOptimizada.class);
                intent.putExtra("puntos",adaptadorQV.stops);
                startActivity(intent);
                }

            }

        });



        botonInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mensaje="Lugares: "+adaptadorQV.prueba.toString().replace("[", "").replace("]", "") +"\n"+"PRECIO TOTAL : "+adaptadorQV.total+"€"+"\n"+"TIEMPO TOTAL DE VISITA: "+String.valueOf( String.format("%.2f", adaptadorQV.tiempo))+"h";
                if(adaptadorQV.stops.size()==0 || adaptadorQV.stops.isEmpty()){
                    Toast.makeText(getApplicationContext(),"DEBE SELECCIONAR AL MENOS UN LUGAR",Toast.LENGTH_LONG).show();
                }else{
                    AlertDialog.Builder alertDialog= new AlertDialog.Builder(QV.this);
                    alertDialog.setMessage(mensaje)
                            .setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                }
                            });
                    AlertDialog titulo=alertDialog.create();
                    titulo.setTitle("INFORMACION DE VIAJE");
                    titulo.show();

                }
            }
        });

    }
    @SuppressLint("WrongConstant")
    @SuppressWarnings({"MissingPermission"})
    private void localizacion(){



        if(isGPSProvider(getApplicationContext())==true){
            button.setEnabled(true);
            //Toast.makeText(getApplicationContext(),"activado",Toast.LENGTH_LONG).show();
        }else{


                final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("El gps esta desctivado.¿Desea activarlo?")
                        .setCancelable(false)
                        .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(@SuppressWarnings("unused") final DialogInterface dialogInterface, @SuppressWarnings("unused") final int i) {
                                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                                if(isGPSProvider(getApplicationContext())==false){
                                    button.setEnabled(false);
                                }
                                    button.setEnabled(true);

                            }
                        })
                        .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, @SuppressWarnings("unused") final int i) {
                                button.setEnabled(false);
                                dialogInterface.cancel();

                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();



        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_PERMISSION_LOCA){
            if (permissions.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Log.i("TAG", "Permiso permitido");

                //button.setEnabled(true);
                if(isGPSProvider(getApplicationContext())==true){
                    button.setEnabled(true);
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
                                        button.setEnabled(false);
                                    }
                                    button.setEnabled(true);

                                }
                            })
                            .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, @SuppressWarnings("unused") final int i) {
                                    button.setEnabled(false);
                                    dialogInterface.cancel();

                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();



                }

            }else {
                Log.i("TAG", "Permiso denegado" );
                if (ActivityCompat.shouldShowRequestPermissionRationale(QV.this, Manifest.permission.ACCESS_FINE_LOCATION)){
                    new androidx.appcompat.app.AlertDialog.Builder(this).setMessage("Necesitas aceptar el permiso para poder iniciar ruta")
                            .setPositiveButton("Intentalo de Nuevo", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    ActivityCompat.requestPermissions(QV.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_PERMISSION_LOCA);
                                    //localizacion();
                                    //button.setEnabled(true);

                                }
                            })
                            .setNegativeButton("No Gracias", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    button.setEnabled(false);
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


    private void iniciar() {

        elementosqv = new ArrayList<>();
        //elementos.add(new ListElement("Bernabeu", "Estadio"));
        // elements.add(new ListElement("#607d8b","Wanda", "Estadio", "Estadio Atletico del Madrid"));

        adaptadorQV listaAdaptador =new adaptadorQV(elementosqv,QV.this);

        //ListaAdaptador listaAdaptador= new ListaAdaptador(elementos,this);
        recyclerViewqv = findViewById(R.id.listRecyclerViewQV);
        recyclerViewqv.setHasFixedSize(true);
        recyclerViewqv.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewqv.setAdapter(listaAdaptador);






    }

    private void cargarLugares(){

        String URL="https://descubremadrid.xyz/descubreMadrid/coordenadasLugar.php?idPersona="+ idPersona;

        elementosqv = new ArrayList<>();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray array = new JSONArray(response);

                    for (int i = 0; i < array.length(); i++) {
                        JSONObject lugares = array.getJSONObject(i);

                        elementosqv.add(new listaQV(
                                lugares.getString("id"),
                                lugares.getString("nombre"),
                                lugares.getString("tipo"),
                                lugares.getDouble("precioAdulto"),
                                lugares.getDouble("tiempoVisita"),
                                lugares.getDouble("latitud"),
                                lugares.getDouble("longitud"),
                                lugares.getBoolean("seleccionado")

                        ));
                        adaptadorQV = new adaptadorQV(elementosqv,QV.this);
                        recyclerViewqv = findViewById(R.id.listRecyclerViewQV);
                        recyclerViewqv.setAdapter(adaptadorQV);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(QV.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        Volley.newRequestQueue(this).add(stringRequest);
    }





}