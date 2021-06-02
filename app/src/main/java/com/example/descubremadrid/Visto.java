package com.example.descubremadrid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class Visto extends AppCompatActivity implements Response.ErrorListener, Response.Listener<JSONObject> {
    String idPersona;

    ListView listView;
    ArrayList<String> lugares;
    JsonObjectRequest jsonObjectRequest;
    RequestQueue request;

    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visto);


        Intent intent = getIntent();
        idPersona = intent.getStringExtra("idPersona");

        listView = findViewById(R.id.listViewVisto);

        request = Volley.newRequestQueue(getApplicationContext());
        cargarWeb();

    }

    public void cargarWeb(){

        String URL = "https://descubremadrid.xyz/descubreMadrid/listavisto.php?idPersona="+ idPersona;
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URL, null, this, this);
        request.add(jsonObjectRequest);


    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onResponse(JSONObject response) {
        String e= response.toString();
        lugares=new ArrayList<>();
        try {
            JSONObject jsonObject= new JSONObject(e);
            JSONArray arr = jsonObject.getJSONArray("sitio");
            JSONObject obejeto;
            for (int i = 0; i < arr.length(); i++) {
                obejeto = arr.getJSONObject(i);
                Iterator<String> clave = obejeto.keys();

                while(clave.hasNext())
                {
                    String nombreLugar = clave.next();
                    lugares.add(obejeto.getString(nombreLugar));
                }

            }
        } catch (JSONException jsonException) {
            jsonException.printStackTrace();
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                lugares );

        listView.setAdapter(arrayAdapter);
    }
}