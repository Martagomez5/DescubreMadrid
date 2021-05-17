package com.example.descubremadrid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QV extends AppCompatActivity {

    RecyclerView recyclerViewqv;
    ArrayList<listaQV> elementosqv;
    adaptadorQV adaptadorQV;

    String idPersona;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_q_v);

        Intent intent = getIntent();
        idPersona = intent.getStringExtra("idPersona");

        init();
        initListener();

        cargarLugares();

    }



    private void init() {

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
                                lugares.getString("precioAdulto"),
                                lugares.getString("tiempoVisita"),
                                lugares.getDouble("latitud"),
                                lugares.getDouble("longitud")

                        ));
                        adaptadorQV = new adaptadorQV(elementosqv,QV.this);
                        recyclerViewqv = findViewById(R.id.listRecyclerViewQV);
                        recyclerViewqv.setAdapter(adaptadorQV);



                        adaptadorQV.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {



                            }
                        });



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

    private void initListener(){

    }


}