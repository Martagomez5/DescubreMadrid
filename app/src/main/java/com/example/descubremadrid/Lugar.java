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
import android.widget.AdapterView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Lugar extends AppCompatActivity implements SearchView.OnQueryTextListener{
    private static String URL="https://descubremadrid.xyz/descubreMadrid/datoslugares.php";
    RecyclerView recyclerView;
    ArrayList<ListaElementos> elementos;


    String idPersona;

    ListaAdaptador listaAdaptador;

    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle_view_card_view);

        Intent intent = getIntent();
        idPersona = intent.getStringExtra("idPersona");



        iniciarVista();
        iniciar();
        iniciarBuscador();

        cargarLugares();




    }

    private void iniciarVista(){
        searchView = findViewById(R.id.svSearch);
    }

    private void iniciar() {

        elementos = new ArrayList<>();


        ListaAdaptador listAdapter= new ListaAdaptador(elementos,Lugar.this);
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
                        listaAdaptador = new ListaAdaptador(elementos,Lugar.this);
                        recyclerView = findViewById(R.id.listRecyclerView);
                        recyclerView.setAdapter(listaAdaptador);

                        listaAdaptador.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(getApplicationContext(),
                                        "Seleccionado: "+elementos.get(recyclerView.getChildAdapterPosition(v)).getNombre(),
                                        Toast.LENGTH_SHORT).show();

                                Intent intent= new Intent(getApplicationContext(),DetallesLugares.class);

                                intent.putExtra("ID", elementos.get(recyclerView.getChildAdapterPosition(v)).getIdLugar());
                                intent.putExtra("idPersona", idPersona.toString());

                                startActivity(intent);
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
                Toast.makeText(Lugar.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        Volley.newRequestQueue(this).add(stringRequest);
    }

    private void iniciarBuscador(){
        searchView.setOnQueryTextListener(this);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        listaAdaptador.filter(newText);

        return false;
    }



}