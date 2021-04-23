package com.example.descubremadrid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class DetallesLugares extends AppCompatActivity implements  Response.Listener<JSONObject>, Response.ErrorListener {
    TextView tvNombre, tvtipo, tvhorario, tvweb, tvprecio, tvdescuento, tvreserva, tvtelefono, tvubicacion, tvtransporte, tvaccesibilidad;
    ImageView tvdireccion;
    Button btnTelefono, btnWeb;

    Context context;
    public static String id2;
    JsonObjectRequest jsonObjectRequest;
    RequestQueue request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_lugares);


        btnTelefono=findViewById(R.id.buttonTelefono);
        btnWeb=findViewById(R.id.buttonWeb);

        tvNombre = findViewById(R.id.textViewNombre);
        tvdireccion = (ImageView)findViewById(R.id.imagenLugares);
        tvtipo = findViewById(R.id.textViewTipo);
        tvhorario = findViewById(R.id.textViewHorario);
        tvweb = findViewById(R.id.textViewWeb);
        tvprecio = findViewById(R.id.textViewPrecio);
        tvdescuento = findViewById(R.id.textViewDescuento);
        tvreserva = findViewById(R.id.textViewReserva);
        tvtelefono = findViewById(R.id.textViewTelefono);
        tvubicacion = findViewById(R.id.textViewUbicacion);
        tvtransporte = findViewById(R.id.textViewTransporte);
        tvaccesibilidad = findViewById(R.id.textViewAccesibilidad);



        Intent intent = getIntent();


        id2 = intent.getStringExtra("ID");
        //Toast.makeText(getApplicationContext(), id2.toString(), Toast.LENGTH_SHORT).show();
        tvNombre.setText(id2.toString());

        request = Volley.newRequestQueue(getApplicationContext());


        cargarWeb();

        btnWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(tvweb.getText().toString());
                Intent intent1 = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent1);
            }
        });

        btnTelefono.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+tvtelefono.getText().toString()));
                if (ActivityCompat.checkSelfPermission(DetallesLugares.this, Manifest.permission.CALL_PHONE)!=
                        PackageManager.PERMISSION_GRANTED)return;
                    startActivity(intent2);


            }
        });

    }

    private void cargarWeb() {

        String URL="https://descubremadrid.xyz/descubreMadrid/detallesLugares.php?id="+id2.toString();
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,URL, null,this, this);
        request.add(jsonObjectRequest);

    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getApplicationContext(), "No se pudo conectar"+error.toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponse(JSONObject response) {
      //  Toast.makeText(getApplicationContext(), "Mensaje:"+response.toString(), Toast.LENGTH_SHORT).show();

        dLugares dLugares = new dLugares();
        JSONArray jsonArray = response.optJSONArray("lugar");
        JSONObject jsonObject=null;

        try {
            jsonObject=jsonArray.getJSONObject(0);
            dLugares.setNombre(jsonObject.optString("nombre"));
            dLugares.setDireccion(jsonObject.optString("direccion"));
            dLugares.setTipo(jsonObject.optString("tipo"));
            dLugares.setHorario(jsonObject.optString("horario"));
            dLugares.setWeb(jsonObject.optString("web"));
            dLugares.setPrecio(jsonObject.optString("precio"));
            dLugares.setDescuento(jsonObject.optString("descuento"));
            dLugares.setReserva(jsonObject.optString("reserva"));
            dLugares.setTelefono(jsonObject.optString("telefono"));
            dLugares.setUbicacion(jsonObject.optString("ubicacion"));
            dLugares.setTransporte(jsonObject.optString("transporte"));
            dLugares.setAccesibilidad(jsonObject.optString("accesibilidad"));


        } catch (JSONException e) {
            e.printStackTrace();
        }
        tvNombre.setText(dLugares.getNombre());
        tvtipo.setText("Tipo:  " + dLugares.getTipo());
        Picasso.get().load(dLugares.getDireccion()).into(tvdireccion);
        tvhorario.setText("Horario:  " + dLugares.getHorario());
        tvweb.setText(dLugares.getWeb());
        tvprecio.setText("Precio:  "+ dLugares.getPrecio());
        tvdescuento.setText("Descuentos:  "+ dLugares.getDescuento());
        tvreserva.setText("Reserva:  "+ dLugares.getReserva());
        tvtelefono.setText(dLugares.getTelefono());
        tvubicacion.setText("Ubicación:  "+ dLugares.getUbicacion());
        tvtransporte.setText("Transporte:  "+ dLugares.getTransporte());
        tvaccesibilidad.setText("Accesibilidad:  "+ dLugares.getAccesibilidad());





    }

   /*
    private void cargarWeb(String URL, String id){
        jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, URL, null,this ,this);

        requestQueue.add(jsonObjectRequest);
    }*/



   /* private void validarId(String URL){
        StringRequest stringRequest= new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
               
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DetallesLugares.this, error.toString(),Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parametros=new HashMap<String, String>();
                parametros.put("id",id2.toString());

                return parametros;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }*/


}