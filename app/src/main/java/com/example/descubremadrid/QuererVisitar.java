package com.example.descubremadrid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public class QuererVisitar extends AppCompatActivity implements Response.ErrorListener,Response.Listener<JSONObject> {

    TextView tvNombre, tvId;
    JsonObjectRequest jsonObjectRequest;
    RequestQueue request;
    String idPersona;
    ListView listView;
    ArrayList<String> clinicas;

    ArrayAdapter<String> adapter;
    String URL2="https://descubremadrid.xyz/descubreMadrid/obtenerNL.php?id=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_querer_visitar);

        tvId=findViewById(R.id.textViewid);
        tvNombre = findViewById(R.id.NombreQV);
        listView=findViewById(R.id.listView);
        Intent intent = getIntent();

        idPersona=intent.getStringExtra("idPersona");

        Toast.makeText(getApplicationContext(),idPersona,Toast.LENGTH_SHORT).show();

        request = Volley.newRequestQueue(getApplicationContext());
        cargarWeb();

    }
    private void cargarWeb() {

        String URL="https://descubremadrid.xyz/descubreMadrid/listaQuererVisitar.php?idPersona="+idPersona.toString();
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,URL, null,  this, this);
        request.add(jsonObjectRequest);

    }


    @Override
    public void onErrorResponse(VolleyError error) {


    }

    @Override
    public void onResponse(JSONObject response) {
String e= response.toString();
 clinicas=new ArrayList<>();
        try {
            JSONObject jsonObject= new JSONObject(e);
            JSONArray arr = jsonObject.getJSONArray("sitio");
            JSONObject jo;
            for (int i = 0; i < arr.length(); i++) {
                jo = arr.getJSONObject(i);
                Iterator<String> keys = jo.keys();

                while(keys.hasNext())
                {
                    String keyName = keys.next();
                    clinicas.add(jo.getString(keyName));
                }

            }
            } catch (JSONException jsonException) {
            jsonException.printStackTrace();
        }
        Toast.makeText(getApplicationContext(),clinicas.toString(),Toast.LENGTH_LONG).show();
       ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                clinicas );

        listView.setAdapter(arrayAdapter);

    }

}