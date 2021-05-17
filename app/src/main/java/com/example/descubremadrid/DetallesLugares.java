package com.example.descubremadrid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.fonts.Font;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
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
    TextView tvNombre, tvtipo, tvhorario, tvweb, tvprecio, tvdescuento, tvreserva, tvtelefono, tvubicacion, tvtransporte, tvaccesibilidad, tvtiempoVisita;
    ImageView tvdireccion;
    Button btnTelefono, btnWeb, buttonQV;
    CheckBox cb;
    int tiempoEnHoras;


    public static String respuesta;

    Context context;
    public static String id2;
    public static String idPersona;
    JsonObjectRequest jsonObjectRequest;
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest2;
    RequestQueue request2;




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
        tvtiempoVisita = findViewById(R.id.textViewTiempoVisita);
        cb=findViewById(R.id.checkBoxQuererVer);
        buttonQV=findViewById(R.id.buttonGuardar);



        Intent intent = getIntent();


        id2 = intent.getStringExtra("ID");
        idPersona = intent.getStringExtra("idPersona");



        tvNombre.setText(id2.toString());

        request = Volley.newRequestQueue(getApplicationContext());
        request2 = Volley.newRequestQueue(getApplicationContext());

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

        buttonQV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String URL="https://descubremadrid.xyz/descubreMadrid/quererVerGuardar.php";
                if(cb.isChecked()==true){
                    respuesta="1";
                }else{
                    respuesta="0";
                }
                guardar(URL);

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
            dLugares.setTiempoVisita(jsonObject.optString("tiempoVisita"));


        } catch (JSONException e) {
            e.printStackTrace();
        }

        tvNombre.setText(dLugares.getNombre());
        tvtipo.setText(Html.fromHtml("<FONT COLOR='black'><b>Tipo:  </b></FONT>" + dLugares.getTipo()));
        Picasso.get().load(dLugares.getDireccion()).into(tvdireccion);
        tvhorario.setText(Html.fromHtml("<FONT COLOR='black'><b>Horario:  </b></FONT>" + dLugares.getHorario()));
        tvweb.setText(dLugares.getWeb());
        tvprecio.setText(Html.fromHtml("<FONT COLOR='black'><b>Precio:  </b></FONT>"+ dLugares.getPrecio()));
        tvdescuento.setText(Html.fromHtml("<FONT COLOR='black'><b>Descuento:  </b></FONT>"+ dLugares.getDescuento()));
        tvreserva.setText(Html.fromHtml("<FONT COLOR='black'><b>Reserva:  </b></FONT>"+ dLugares.getReserva()));
        tvtelefono.setText(dLugares.getTelefono());
        tvubicacion.setText(Html.fromHtml("<FONT COLOR='black'><b>Ubicación:  </b></FONT>"+ dLugares.getUbicacion()));
        tvtransporte.setText(Html.fromHtml("<FONT COLOR='black'><b>Transporte:  </b></FONT>"+ dLugares.getTransporte()));
        tvaccesibilidad.setText(Html.fromHtml("<FONT COLOR='black'><b>Accesibilidad:  </b></FONT>"+ dLugares.getAccesibilidad()));
        tvtiempoVisita.setText(Html.fromHtml("<FONT COLOR='black'><b>Tiempo duración visita:  </b></FONT>"+ dLugares.getTiempoVisita()));

         if(dLugares.getDescuento().equals("null")){
             tvdescuento.setHeight(0);
         }
        if(dLugares.getTelefono().equals("null")){

            Toast.makeText(getApplicationContext(),"No dispone de telefono de contacto",Toast.LENGTH_SHORT).show();
            tvtelefono.setHeight(0);
            btnTelefono.setEnabled(false);
        }

        if(dLugares.getReserva().equals("null")){
            tvreserva.setHeight(0);
        }

        if(dLugares.getWeb().equals("null")){
            tvweb.setHeight(0);
        }
        if(dLugares.getAccesibilidad().equals("null")){
            tvaccesibilidad.setHeight(0);
        }

        if(dLugares.getTransporte().equals("null")){
            tvtransporte.setHeight(0);
        }


        querer();




    }

    public void querer(){

        String URL="https://descubremadrid.xyz/descubreMadrid/pp.php?idLugar="+id2.toString() + "&idPersona="+idPersona.toString();

        StringRequest stringRequest= new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                 respuesta=response.trim();
                if(respuesta.equals("0")){
                    respuesta="0";
                    cb.setChecked(false);

                }else if(respuesta.equals("1")){
                    respuesta="1";
                    cb.setChecked(true);

                }else{
                    respuesta="0";
                    cb.setChecked(false);

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });


                RequestQueue requestQueue = Volley.newRequestQueue(this);
                requestQueue.add(stringRequest);
    }

    private void guardar(String URL) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {



                        Toast.makeText(getApplicationContext(), "GUARDADO "+respuesta, Toast.LENGTH_SHORT).show();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String t = "GUARDADO INCORRECTO";
                Toast.makeText(getApplicationContext(), t, Toast.LENGTH_SHORT).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                if(respuesta.equals("0")){

                    respuesta="0";
                }else {
                    respuesta="1";
                }
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("quererVisitar", respuesta);
                parametros.put("idPersona", idPersona);
                parametros.put("idLugar", id2);
                return parametros;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

public void visibilidad(){
    if(tvdescuento.getText().equals("null")){
        tvdescuento.setVisibility(View.INVISIBLE);
    }
}

}