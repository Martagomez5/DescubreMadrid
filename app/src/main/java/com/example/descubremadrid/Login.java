package com.example.descubremadrid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class Login extends AppCompatActivity{
  EditText textcorreo,textcontra;
  Button btnlog;
  String correo,pass;
  TextView registrarse;
  CheckBox mostrarcontraseña;

    public static String id2;
    JsonObjectRequest jsonObjectRequest;
    RequestQueue request;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        textcorreo=findViewById(R.id.email_iniciosesion);
        textcontra=findViewById(R.id.contraseña_iniciosesion);
        btnlog=findViewById(R.id.btn_iniciosesion);
        registrarse=findViewById(R.id.textViewRegistrate);
        mostrarcontraseña=findViewById(R.id.CheckBoxMostrarContraseña);



        mostrarContraseña();

        btnlog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              
               if (textcorreo.getText().toString().isEmpty() && textcontra.getText().toString().isEmpty()){
                    textcorreo.setError("El correo no puede estar vacio");
                    textcontra.setError("La contraseña no puede estar vacia");

               }else if(textcorreo.getText().toString().isEmpty() ) {

                    textcorreo.setError("El correo no puede estar vacio");

               }else if(textcontra.getText().toString().isEmpty()) {

                    textcontra.setError("La contraseña no puede estar vacia");

               }else{

                    validarUsuario("https://descubremadrid.xyz/descubreMadrid/validarusuario.php");

               }
            }
        });


        registrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Registro.class);
                startActivity(intent);
            }
        });

    }



    private void mostrarContraseña(){
        mostrarcontraseña.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                textcontra.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                } else {
                    textcontra.setInputType(129);
                }
            }
        });
    }

    private void validarUsuario(String URL){
        StringRequest stringRequest= new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(!response.isEmpty()){
                    Intent intent = new Intent(getApplicationContext(), menuprincipal.class);
                    intent.putExtra("Correo", textcorreo.getText().toString());
                    startActivity(intent);

                }else{
                    Toast.makeText(Login.this, "Correo o contraseña incorrecta", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Login.this, "ERROR",Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parametros=new HashMap<String, String>();
                parametros.put("correo",textcorreo.getText().toString());
                parametros.put("contrasena",textcontra.getText().toString());

                return parametros;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void onBackPressed() {
        moveTaskToBack(true);
    }

}