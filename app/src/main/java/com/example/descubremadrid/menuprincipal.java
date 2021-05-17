package com.example.descubremadrid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
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

public class menuprincipal extends AppCompatActivity implements Response.ErrorListener, Response.Listener<JSONObject> {

    String correoRecibido;
    ImageButton btnLugares,btnPlanos,btnItinarios, btnOficinaTurismo, btnMadridCard, btnBusTuristico, btnLugaresVistos, btnLugaresQuererVer;
    TextView id;

    JsonObjectRequest jsonObjectRequest;
    RequestQueue request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manuprincipal);

        btnLugares=findViewById(R.id.btnLugares);
        btnPlanos=findViewById(R.id.btnPlanos);
        btnItinarios=findViewById(R.id.btnItinerario);
        btnOficinaTurismo=findViewById(R.id.btnOficinasTurismo);
        btnMadridCard=findViewById(R.id.btnMadridCard);
        btnBusTuristico=findViewById(R.id.btnBusTuristico);
        btnLugaresVistos=findViewById(R.id.btnVistos);
        btnLugaresQuererVer=findViewById(R.id.btnQuererVer);
        id=findViewById(R.id.textViewid);

        Intent intent = getIntent();

        correoRecibido = intent.getStringExtra("Correo");

        request = Volley.newRequestQueue(getApplicationContext());

        cargarCorreo();




        btnLugares.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), Lugar.class);
                intent.putExtra("idPersona", id.getText().toString());
                startActivity(intent);

            }
        });

        btnPlanos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), MenuMapas.class);
                startActivity(intent);
            }
        });

        btnItinarios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), MenuItinerarios.class);
                startActivity(intent);

            }
        });

        btnOficinaTurismo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), Oficinas.class);
                startActivity(intent);

            }
        });

        btnMadridCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TarjetaTransporte.class);
                startActivity(intent);

            }
        });

        btnBusTuristico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), BusTuristico.class);
                startActivity(intent);

            }
        });

        btnLugaresVistos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnLugaresQuererVer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), QV.class);

                intent.putExtra("idPersona", id.getText().toString());
                startActivity(intent);

            }
        });

    }

    private void cargarCorreo() {
        String URL="https://descubremadrid.xyz/descubreMadrid/persona.php?correo="+correoRecibido;
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,URL, null,this, this);
        request.add(jsonObjectRequest);
    }


    public void onBackPressed() {
        moveTaskToBack(true);
    }


    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getApplicationContext(), "No se pudo conectar"+error.toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponse(JSONObject response) {
        dPersona dPersona = new dPersona();
        JSONArray jsonArray = response.optJSONArray("persona");
        JSONObject jsonObject=null;
        String a;

        try {
            jsonObject=jsonArray.getJSONObject(0);
            dPersona.setId(jsonObject.optString("id"));


        } catch (JSONException e) {
            e.printStackTrace();
        }

        id.setText(dPersona.getId());

    }


}