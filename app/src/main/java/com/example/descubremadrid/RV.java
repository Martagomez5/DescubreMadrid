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
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.descubremadrid.ListaAdaptador;
import com.example.descubremadrid.ListaElementos;
import com.example.descubremadrid.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RV extends AppCompatActivity {
    private static String URL="https://descubremadrid.xyz/descubreMadrid/datoslugares.php";
    RecyclerView recyclerView;
    List<ListaElementos> elementos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle_view_card_view);
        init();

        cargarLugares();
    }

    private void init() {

        elementos = new ArrayList<>();
        //elementos.add(new ListElement("Bernabeu", "Estadio"));
        // elements.add(new ListElement("#607d8b","Wanda", "Estadio", "Estadio Atletico del Madrid"));

        ListaAdaptador listaAdaptador =new ListaAdaptador(elementos,RV.this);

        //ListaAdaptador listaAdaptador= new ListaAdaptador(elementos,this);
        recyclerView = findViewById(R.id.listRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(listaAdaptador);
    }

    private void cargarLugares(){

        elementos = new ArrayList<>();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray array = new JSONArray(response);

                    for (int i = 0; i < array.length(); i++) {
                        JSONObject lugares = array.getJSONObject(i);

                        elementos.add(new ListaElementos(
                                lugares.getString("id"),
                                lugares.getString("nombre"),
                                lugares.getString("tipo"),
                                lugares.getString("direccion")

                        ));
                        ListaAdaptador listAdapter = new ListaAdaptador(elementos,RV.this);
                        recyclerView = findViewById(R.id.listRecyclerView);
                        recyclerView.setAdapter(listAdapter);

                        listAdapter.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(getApplicationContext(),
                                        "Seleccion: "+elementos.get(recyclerView.getChildAdapterPosition(v)).getNombre(),
                                        Toast.LENGTH_SHORT).show();

                               // Intent intent= new Intent(getApplicationContext(),DatosLugares.class);

                              //  intent.putExtra("NOMBRE", elementos.get(recyclerView.getChildAdapterPosition(v)).getNombre());

                               // startActivity(intent);
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
                Toast.makeText(RV.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        Volley.newRequestQueue(this).add(stringRequest);
    }
}