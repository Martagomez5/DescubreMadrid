package com.example.descubremadrid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class Registro extends AppCompatActivity {

    EditText editTextNombre, editTextCorreo, editTextApellidos, editTextContraseña, editTextComprobarContraseña;
    RadioGroup radioGroupGenero;
    RadioButton radioButtonHombre, radioButtonMujer, radioButtonOtro;
    Button buttonRegistrarse;
    String gene = "V";
    String nombre;
    String apellidos;
    String contra = "Si";
    TextView iniciarSesion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);


        editTextNombre = (EditText) findViewById(R.id.editTextNombre);
        editTextCorreo = (EditText) findViewById(R.id.editTextCorreo);
        editTextApellidos = (EditText) findViewById(R.id.editTextApellidos);
        editTextContraseña = (EditText) findViewById(R.id.editTextContraseña);
        editTextComprobarContraseña = (EditText) findViewById(R.id.editTextComprobarContraseña);
        buttonRegistrarse = (Button) findViewById(R.id.buttonRegistrarse);
        radioGroupGenero = (RadioGroup) findViewById(R.id.radioGroupGenero);
        radioButtonHombre = (RadioButton) findViewById(R.id.radioButtonHombre);
        radioButtonMujer = (RadioButton) findViewById(R.id.radioButtonMujer);
        radioButtonOtro = (RadioButton) findViewById(R.id.radioButtonOtro);
        iniciarSesion = (TextView)findViewById(R.id.iniciarSesion);


        radioGroupGenero.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                genero();
            }
        });

        iniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
            }
        });


        buttonRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //contraseña();
                comprobarVacios();


            /*else{
                    ejecutarServicio("https://descubremadrid.000webhostapp.com/descubreMadrid/registro.php");

                }*/


            }
        });
    }

    private void ejecutarServicio(String URL) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.equals("Ya existe este correo")){
                            Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getApplicationContext(), "Registro Correcto", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), Login.class);
                            startActivity(intent);
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String t = "REGISTRO INCORRECTO";
                Toast.makeText(getApplicationContext(), t, Toast.LENGTH_SHORT).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("nombre", editTextNombre.getText().toString());
                parametros.put("apellidos", editTextApellidos.getText().toString());
                parametros.put("correo", editTextCorreo.getText().toString());
                parametros.put("contrasena", editTextContraseña.getText().toString());
                parametros.put("genero", gene.toString());

                return parametros;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }




    private void contraseña() {
        if (!validarEmail(editTextCorreo.getText().toString())){
            editTextCorreo.setError("Email no válido");
        }
    }


        private void genero () {

            if (radioButtonHombre.isChecked()) {
                gene = "H";
                Toast.makeText(getApplicationContext(), gene, Toast.LENGTH_SHORT).show();
            } else if (radioButtonMujer.isChecked()) {
                gene = "M";
                Toast.makeText(getApplicationContext(), gene, Toast.LENGTH_SHORT).show();
            } else if (radioButtonOtro.isChecked()) {
                gene = "O";
                Toast.makeText(getApplicationContext(), gene, Toast.LENGTH_SHORT).show();
            }
        }


        private void comprobarVacios () {

        String contra = editTextComprobarContraseña.getText().toString();


            if ((editTextNombre.getText().toString().isEmpty()) && (editTextApellidos.getText().toString().isEmpty()) && (editTextCorreo.getText().toString().isEmpty()) && ((gene == "V") && (editTextContraseña.getText().toString().isEmpty()) && (editTextComprobarContraseña.getText().toString().isEmpty()))) {
                editTextNombre.setError("El nombre no puede estar vacio");
                editTextApellidos.setError("Los apellidos no pueden estar vacios");
                editTextCorreo.setError("El correo no puede estar vacio");
                editTextContraseña.setError("La contraseña no puede estar vacia");
                editTextComprobarContraseña.setError("La comprobacion de contraseña no puede estar vacia");
                Toast.makeText(getApplicationContext(), "No se permite el genero vacio", Toast.LENGTH_SHORT).show();
            } else if ((editTextApellidos.getText().toString().isEmpty()) && (editTextCorreo.getText().toString().isEmpty()) && (gene == "V") && (editTextContraseña.getText().toString().isEmpty()) && (editTextComprobarContraseña.getText().toString().isEmpty())) {
                editTextApellidos.setError("Los apellidos no pueden estar vacios");
                editTextCorreo.setError("El correo no puede estar vacio");
                Toast.makeText(getApplicationContext(), "No se permite el genero vacio", Toast.LENGTH_SHORT).show();
                editTextContraseña.setError("La contraseña no puede estar vacia");
                editTextComprobarContraseña.setError("La comprobacion de contraseña no puede estar vacia");

            } else if ((editTextNombre.getText().toString().isEmpty()) && (editTextCorreo.getText().toString().isEmpty()) && (gene == "V") && (editTextContraseña.getText().toString().isEmpty()) && (editTextComprobarContraseña.getText().toString().isEmpty())) {
                editTextNombre.setError("El nombre no puede estar vacio");
                editTextCorreo.setError("El correo no puede estar vacio");
                Toast.makeText(getApplicationContext(), "No se permite el genero vacio", Toast.LENGTH_SHORT).show();
                editTextContraseña.setError("La contraseña no puede estar vacia");
                editTextComprobarContraseña.setError("La comprobacion de contraseña no puede estar vacia");
            } else if ((editTextNombre.getText().toString().isEmpty()) && (editTextApellidos.getText().toString().isEmpty()) && (gene == "V") && (editTextContraseña.getText().toString().isEmpty()) && (editTextComprobarContraseña.getText().toString().isEmpty())) {
                editTextNombre.setError("El nombre no puede estar vacio");
                editTextApellidos.setError("Los apellidos no pueden estar vacios");
                Toast.makeText(getApplicationContext(), "No se permite el genero vacio", Toast.LENGTH_SHORT).show();
                editTextContraseña.setError("La contraseña no puede estar vacia");
                editTextComprobarContraseña.setError("La comprobacion de contraseña no puede estar vacia");

            } else if ((editTextNombre.getText().toString().isEmpty()) && (editTextApellidos.getText().toString().isEmpty()) && (editTextCorreo.getText().toString().isEmpty()) && (editTextContraseña.getText().toString().isEmpty()) && (editTextComprobarContraseña.getText().toString().isEmpty())) {
                editTextNombre.setError("El nombre no puede estar vacio");
                editTextApellidos.setError("Los apellidos no pueden estar vacios");
                editTextCorreo.setError("El correo no puede estar vacio");
                editTextContraseña.setError("La contraseña no puede estar vacia");
                editTextComprobarContraseña.setError("La comprobacion de contraseña no puede estar vacia");

            } else if ((editTextNombre.getText().toString().isEmpty()) && (editTextApellidos.getText().toString().isEmpty()) && (editTextCorreo.getText().toString().isEmpty()) && (gene == "V") && (editTextComprobarContraseña.getText().toString().isEmpty())) {
                editTextNombre.setError("El nombre no puede estar vacio");
                editTextApellidos.setError("Los apellidos no pueden estar vacios");
                editTextCorreo.setError("El correo no puede estar vacio");
                Toast.makeText(getApplicationContext(), "No se permite el genero vacio", Toast.LENGTH_SHORT).show();
                editTextComprobarContraseña.setError("La comprobacion de contraseña no puede estar vacia");

            } else if ((editTextNombre.getText().toString().isEmpty()) && (editTextApellidos.getText().toString().isEmpty()) && (editTextCorreo.getText().toString().isEmpty()) && (gene == "V") && (editTextContraseña.getText().toString().isEmpty())) {
                editTextNombre.setError("El nombre no puede estar vacio");
                editTextApellidos.setError("Los apellidos no pueden estar vacios");
                editTextCorreo.setError("El correo no puede estar vacio");
                Toast.makeText(getApplicationContext(), "No se permite el genero vacio", Toast.LENGTH_SHORT).show();
                editTextContraseña.setError("La contraseña no puede estar vacia");

            } else if ((editTextCorreo.getText().toString().isEmpty()) && (gene == "V") && (editTextContraseña.getText().toString().isEmpty()) && (editTextComprobarContraseña.getText().toString().isEmpty())) {
                editTextCorreo.setError("El correo no puede estar vacio");
                Toast.makeText(getApplicationContext(), "No se permite el genero vacio", Toast.LENGTH_SHORT).show();
                editTextContraseña.setError("La contraseña no puede estar vacia");
                editTextComprobarContraseña.setError("La comprobacion de contraseña no puede estar vacia");
            } else if ((editTextNombre.getText().toString().isEmpty()) && (editTextApellidos.getText().toString().isEmpty()) && (editTextCorreo.getText().toString().isEmpty()) && (gene == "V")) {
                editTextNombre.setError("El nombre no puede estar vacio");
                editTextApellidos.setError("Los apellidos no pueden estar vacios");
                editTextCorreo.setError("El correo no puede estar vacio");
                Toast.makeText(getApplicationContext(), "No se permite el genero vacio", Toast.LENGTH_SHORT).show();

            } else if ((editTextNombre.getText().toString().isEmpty()) && (editTextApellidos.getText().toString().isEmpty()) && (editTextCorreo.getText().toString().isEmpty()) && (editTextContraseña.getText().toString().isEmpty())) {
                editTextNombre.setError("El nombre no puede estar vacio");
                editTextApellidos.setError("Los apellidos no pueden estar vacios");
                editTextCorreo.setError("El correo no puede estar vacio");
                editTextContraseña.setError("La contraseña no puede estar vacia");

            } else if ((editTextNombre.getText().toString().isEmpty()) && (editTextApellidos.getText().toString().isEmpty()) && (editTextCorreo.getText().toString().isEmpty()) && (editTextComprobarContraseña.getText().toString().isEmpty())) {
                editTextNombre.setError("El nombre no puede estar vacio");
                editTextApellidos.setError("Los apellidos no pueden estar vacios");
                editTextCorreo.setError("El correo no puede estar vacio");
                editTextComprobarContraseña.setError("La comprobacion de contraseña no puede estar vacia");

            } else if ((editTextNombre.getText().toString().isEmpty()) && (editTextApellidos.getText().toString().isEmpty()) && (gene == "V") && (editTextContraseña.getText().toString().isEmpty())) {
                editTextNombre.setError("El nombre no puede estar vacio");
                editTextApellidos.setError("Los apellidos no pueden estar vacios");
                Toast.makeText(getApplicationContext(), "No se permite el genero vacio", Toast.LENGTH_SHORT).show();
                editTextContraseña.setError("La contraseña no puede estar vacia");

            } else if ((editTextNombre.getText().toString().isEmpty()) && (editTextApellidos.getText().toString().isEmpty()) && (gene == "V") && (editTextComprobarContraseña.getText().toString().isEmpty())) {
                editTextNombre.setError("El nombre no puede estar vacio");
                editTextApellidos.setError("Los apellidos no pueden estar vacios");
                Toast.makeText(getApplicationContext(), "No se permite el genero vacio", Toast.LENGTH_SHORT).show();
                editTextComprobarContraseña.setError("La comprobacion de contraseña no puede estar vacia");

            } else if ((editTextNombre.getText().toString().isEmpty()) && (editTextApellidos.getText().toString().isEmpty()) && (editTextContraseña.getText().toString().isEmpty()) && (editTextComprobarContraseña.getText().toString().isEmpty())) {
                editTextNombre.setError("El nombre no puede estar vacio");
                editTextApellidos.setError("Los apellidos no pueden estar vacios");
                editTextContraseña.setError("La contraseña no puede estar vacia");
                editTextComprobarContraseña.setError("La comprobacion de contraseña no puede estar vacia");

            } else if ((editTextNombre.getText().toString().isEmpty()) && (editTextCorreo.getText().toString().isEmpty()) && (gene == "V") && (editTextContraseña.getText().toString().isEmpty())) {
                editTextNombre.setError("El nombre no puede estar vacio");
                editTextCorreo.setError("El correo no puede estar vacio");
                Toast.makeText(getApplicationContext(), "No se permite el genero vacio", Toast.LENGTH_SHORT).show();
                editTextContraseña.setError("La contraseña no puede estar vacia");
            } else if ((editTextNombre.getText().toString().isEmpty()) && (editTextCorreo.getText().toString().isEmpty()) && (gene == "V") && (editTextComprobarContraseña.getText().toString().isEmpty())) {
                editTextNombre.setError("El nombre no puede estar vacio");
                editTextCorreo.setError("El correo no puede estar vacio");
                Toast.makeText(getApplicationContext(), "No se permite el genero vacio", Toast.LENGTH_SHORT).show();
                editTextComprobarContraseña.setError("La comprobacion de contraseña no puede estar vacia");
            } else if ((editTextNombre.getText().toString().isEmpty()) && (editTextCorreo.getText().toString().isEmpty()) && (editTextContraseña.getText().toString().isEmpty()) && (editTextComprobarContraseña.getText().toString().isEmpty())) {
                editTextNombre.setError("El nombre no puede estar vacio");
                editTextCorreo.setError("El correo no puede estar vacio");
                editTextContraseña.setError("La contraseña no puede estar vacia");
                editTextComprobarContraseña.setError("La comprobacion de contraseña no puede estar vacia");
            } else if ((editTextNombre.getText().toString().isEmpty()) && (gene == "V") && (editTextContraseña.getText().toString().isEmpty()) && (editTextComprobarContraseña.getText().toString().isEmpty())) {
                editTextNombre.setError("El nombre no puede estar vacio");
                editTextContraseña.setError("La contraseña no puede estar vacia");
                editTextComprobarContraseña.setError("La comprobacion de contraseña no puede estar vacia");
                Toast.makeText(getApplicationContext(), "No se permite el genero vacio", Toast.LENGTH_SHORT).show();
            } else if ((editTextApellidos.getText().toString().isEmpty()) && (editTextCorreo.getText().toString().isEmpty()) && (gene == "V") && (editTextContraseña.getText().toString().isEmpty())) {
                editTextApellidos.setError("Los apellidos no pueden estar vacios");
                editTextCorreo.setError("El correo no puede estar vacio");
                editTextContraseña.setError("La contraseña no puede estar vacia");
                Toast.makeText(getApplicationContext(), "No se permite el genero vacio", Toast.LENGTH_SHORT).show();

            } else if ((editTextApellidos.getText().toString().isEmpty()) && (editTextCorreo.getText().toString().isEmpty()) && (gene == "V") && (editTextComprobarContraseña.getText().toString().isEmpty())) {
                editTextApellidos.setError("Los apellidos no pueden estar vacios");
                editTextCorreo.setError("El correo no puede estar vacio");
                editTextComprobarContraseña.setError("La comprobacion de contraseña no puede estar vacia");
                Toast.makeText(getApplicationContext(), "No se permite el genero vacio", Toast.LENGTH_SHORT).show();

            } else if ((editTextApellidos.getText().toString().isEmpty()) && (editTextCorreo.getText().toString().isEmpty()) && (editTextContraseña.getText().toString().isEmpty()) && (editTextComprobarContraseña.getText().toString().isEmpty())) {
                editTextApellidos.setError("Los apellidos no pueden estar vacios");
                editTextCorreo.setError("El correo no puede estar vacio");
                editTextContraseña.setError("La contraseña no puede estar vacia");
                editTextComprobarContraseña.setError("La comprobacion de contraseña no puede estar vacia");

            } else if ((editTextApellidos.getText().toString().isEmpty()) && (gene == "V") && (editTextContraseña.getText().toString().isEmpty()) && (editTextComprobarContraseña.getText().toString().isEmpty())) {
                editTextApellidos.setError("Los apellidos no pueden estar vacios");
                Toast.makeText(getApplicationContext(), "No se permite el genero vacio", Toast.LENGTH_SHORT).show();
                editTextContraseña.setError("La contraseña no puede estar vacia");
                editTextComprobarContraseña.setError("La comprobacion de contraseña no puede estar vacia");

            } else if ((editTextNombre.getText().toString().isEmpty()) && (editTextApellidos.getText().toString().isEmpty()) && (editTextCorreo.getText().toString().isEmpty())) {
                editTextNombre.setError("El nombre no puede estar vacio");
                editTextApellidos.setError("Los apellidos no pueden estar vacios");
                editTextCorreo.setError("El correo no puede estar vacio");

            } else if ((editTextNombre.getText().toString().isEmpty()) && (editTextApellidos.getText().toString().isEmpty()) && (gene == "V")) {
                editTextNombre.setError("El nombre no puede estar vacio");
                editTextApellidos.setError("Los apellidos no pueden estar vacios");
                Toast.makeText(getApplicationContext(), "No se permite el genero vacio", Toast.LENGTH_SHORT).show();

            } else if ((editTextNombre.getText().toString().isEmpty()) && (editTextApellidos.getText().toString().isEmpty()) && (editTextContraseña.getText().toString().isEmpty())) {
                editTextNombre.setError("El nombre no puede estar vacio");
                editTextApellidos.setError("Los apellidos no pueden estar vacios");
                editTextContraseña.setError("La contraseña no puede estar vacia");

            } else if ((editTextNombre.getText().toString().isEmpty()) && (editTextApellidos.getText().toString().isEmpty()) && (editTextComprobarContraseña.getText().toString().isEmpty())) {
                editTextNombre.setError("El nombre no puede estar vacio");
                editTextApellidos.setError("Los apellidos no pueden estar vacios");
                editTextComprobarContraseña.setError("La comprobacion de contraseña no puede estar vacia");

            } else if ((editTextNombre.getText().toString().isEmpty()) && (editTextCorreo.getText().toString().isEmpty()) && (gene == "V")) {
                editTextNombre.setError("El nombre no puede estar vacio");
                editTextCorreo.setError("El correo no puede estar vacio");
                Toast.makeText(getApplicationContext(), "No se permite el genero vacio", Toast.LENGTH_SHORT).show();
            } else if ((editTextNombre.getText().toString().isEmpty()) && (editTextCorreo.getText().toString().isEmpty()) && (editTextContraseña.getText().toString().isEmpty())) {
                editTextNombre.setError("El nombre no puede estar vacio");
                editTextCorreo.setError("El correo no puede estar vacio");
                editTextContraseña.setError("La contraseña no puede estar vacia");
            } else if ((editTextNombre.getText().toString().isEmpty()) && (editTextCorreo.getText().toString().isEmpty()) && (editTextComprobarContraseña.getText().toString().isEmpty())) {
                editTextNombre.setError("El nombre no puede estar vacio");
                editTextCorreo.setError("El correo no puede estar vacio");
                editTextComprobarContraseña.setError("La comprobacion de contraseña no puede estar vacia");
            } else if ((editTextNombre.getText().toString().isEmpty()) && (!(radioButtonHombre.isChecked()) || !(radioButtonMujer.isChecked()) || !(radioButtonOtro.isChecked())) && (editTextContraseña.getText().toString().isEmpty())) {
                editTextNombre.setError("El nombre no puede estar vacio");
                Toast.makeText(getApplicationContext(), "No se permite el genero vacio", Toast.LENGTH_SHORT).show();
                editTextContraseña.setError("La contraseña no puede estar vacia");
            } else if ((editTextNombre.getText().toString().isEmpty()) && (gene == "V") && (editTextComprobarContraseña.getText().toString().isEmpty())) {
                editTextNombre.setError("El nombre no puede estar vacio");
                Toast.makeText(getApplicationContext(), "No se permite el genero vacio", Toast.LENGTH_SHORT).show();
                editTextComprobarContraseña.setError("La comprobacion de contraseña no puede estar vacia");
            } else if ((editTextNombre.getText().toString().isEmpty()) && (editTextContraseña.getText().toString().isEmpty()) && (editTextComprobarContraseña.getText().toString().isEmpty())) {
                editTextNombre.setError("El nombre no puede estar vacio");
                editTextContraseña.setError("La contraseña no puede estar vacia");
                editTextComprobarContraseña.setError("La comprobacion de contraseña no puede estar vacia");
            } else if ((editTextApellidos.getText().toString().isEmpty()) && (editTextCorreo.getText().toString().isEmpty()) && (gene == "V")) {
                editTextApellidos.setError("Los apellidos no pueden estar vacios");
                editTextCorreo.setError("El correo no puede estar vacio");
                Toast.makeText(getApplicationContext(), "No se permite el genero vacio", Toast.LENGTH_SHORT).show();

            } else if ((editTextApellidos.getText().toString().isEmpty()) && (editTextCorreo.getText().toString().isEmpty()) && (editTextContraseña.getText().toString().isEmpty())) {
                editTextApellidos.setError("Los apellidos no pueden estar vacios");
                editTextCorreo.setError("El correo no puede estar vacio");
                editTextContraseña.setError("La contraseña no puede estar vacia");

            } else if ((editTextApellidos.getText().toString().isEmpty()) && (editTextCorreo.getText().toString().isEmpty()) && (editTextComprobarContraseña.getText().toString().isEmpty())) {
                editTextApellidos.setError("Los apellidos no pueden estar vacios");
                editTextCorreo.setError("El correo no puede estar vacio");
                editTextComprobarContraseña.setError("La comprobacion de contraseña no puede estar vacia");

            } else if ((editTextApellidos.getText().toString().isEmpty()) && (gene == "V") && (editTextContraseña.getText().toString().isEmpty())) {
                editTextApellidos.setError("Los apellidos no pueden estar vacios");
                Toast.makeText(getApplicationContext(), "No se permite el genero vacio", Toast.LENGTH_SHORT).show();
                editTextContraseña.setError("La contraseña no puede estar vacia");

            } else if ((editTextApellidos.getText().toString().isEmpty()) && (gene == "V") && (editTextComprobarContraseña.getText().toString().isEmpty())) {
                editTextApellidos.setError("Los apellidos no pueden estar vacios");
                Toast.makeText(getApplicationContext(), "No se permite el genero vacio", Toast.LENGTH_SHORT).show();
                editTextComprobarContraseña.setError("La comprobacion de contraseña no puede estar vacia");

            } else if ((editTextApellidos.getText().toString().isEmpty()) && (editTextContraseña.getText().toString().isEmpty()) && (editTextComprobarContraseña.getText().toString().isEmpty())) {
                editTextApellidos.setError("Los apellidos no pueden estar vacios");
                editTextContraseña.setError("La contraseña no puede estar vacia");
                editTextComprobarContraseña.setError("La comprobacion de contraseña no puede estar vacia");

            } else if ((editTextCorreo.getText().toString().isEmpty()) && (gene == "V") && (editTextContraseña.getText().toString().isEmpty())) {
                editTextCorreo.setError("El correo no puede estar vacio");
                Toast.makeText(getApplicationContext(), "No se permite el genero vacio", Toast.LENGTH_SHORT).show();
                editTextContraseña.setError("La contraseña no puede estar vacia");
            } else if ((editTextCorreo.getText().toString().isEmpty()) && (gene == "V") && (editTextComprobarContraseña.getText().toString().isEmpty())) {
                editTextCorreo.setError("El correo no puede estar vacio");
                Toast.makeText(getApplicationContext(), "No se permite el genero vacio", Toast.LENGTH_SHORT).show();
                editTextComprobarContraseña.setError("La comprobacion de contraseña no puede estar vacia");
            } else if ((editTextCorreo.getText().toString().isEmpty()) && (editTextContraseña.getText().toString().isEmpty()) && (editTextComprobarContraseña.getText().toString().isEmpty())) {
                editTextCorreo.setError("El correo no puede estar vacio");
                editTextContraseña.setError("La contraseña no puede estar vacia");
                editTextComprobarContraseña.setError("La comprobacion de contraseña no puede estar vacia");
            } else if ((gene == "V") && (editTextContraseña.getText().toString().isEmpty()) && (editTextComprobarContraseña.getText().toString().isEmpty())) {
                Toast.makeText(getApplicationContext(), "No se permite el genero vacio", Toast.LENGTH_SHORT).show();
                editTextContraseña.setError("La contraseña no puede estar vacia");
                editTextComprobarContraseña.setError("La comprobacion de contraseña no puede estar vacia");
            } else if ((editTextNombre.getText().toString().isEmpty()) && (editTextApellidos.getText().toString().isEmpty())) {
                editTextNombre.setError("El nombre no puede estar vacio");
                editTextApellidos.setError("Los apellidos no pueden estar vacios");

            } else if (editTextNombre.getText().toString().isEmpty() && (editTextCorreo.getText().toString().isEmpty())) {
                editTextNombre.setError("El nombre no puede estar vacio");
                editTextCorreo.setError("El correo no puede estar vacio");
            } else if ((editTextNombre.getText().toString().isEmpty()) && (gene == "V")) {
                Toast.makeText(getApplicationContext(), "No se permite el genero vacio", Toast.LENGTH_SHORT).show();
                editTextNombre.setError("El nombre no puede estar vacio");
            } else if ((editTextNombre.getText().toString().isEmpty()) && (editTextContraseña.getText().toString().isEmpty())) {
                editTextNombre.setError("El nombre no puede estar vacio");
                editTextContraseña.setError("La contraseña no puede estar vacia");
            } else if (editTextNombre.getText().toString().isEmpty() && (editTextComprobarContraseña.getText().toString().isEmpty())) {
                editTextNombre.setError("El nombre no puede estar vacio");
                editTextComprobarContraseña.setError("La comprobacion de contraseña no puede estar vacia");
            } else if (editTextApellidos.getText().toString().isEmpty() && (editTextCorreo.getText().toString().isEmpty())) {
                editTextApellidos.setError("Los apellidos no pueden estar vacios");
                editTextCorreo.setError("El correo no puede estar vacio");

            } else if ((editTextApellidos.getText().toString().isEmpty()) && (gene == "V")) {
                Toast.makeText(getApplicationContext(), "No se permite el genero vacio", Toast.LENGTH_SHORT).show();
                editTextApellidos.setError("Los apellidos no pueden estar vacios");

            } else if ((editTextApellidos.getText().toString().isEmpty()) && (editTextContraseña.getText().toString().isEmpty())) {
                editTextApellidos.setError("Los apellidos no pueden estar vacios");
                editTextContraseña.setError("La contraseña no puede estar vacia");

            } else if ((editTextApellidos.getText().toString().isEmpty()) && (editTextComprobarContraseña.getText().toString().isEmpty())) {
                editTextApellidos.setError("Los apellidos no pueden estar vacios");
                editTextComprobarContraseña.setError("La comprobacion de contraseña no puede estar vacia");
            } else if ((editTextCorreo.getText().toString().isEmpty()) && (gene == "V")) {
                Toast.makeText(getApplicationContext(), "No se permite el genero vacio", Toast.LENGTH_SHORT).show();
                editTextCorreo.setError("El correo no puede estar vacio");
            } else if ((editTextCorreo.getText().toString().isEmpty()) && (editTextContraseña.getText().toString().isEmpty())) {
                editTextCorreo.setError("El correo no puede estar vacio");
                editTextContraseña.setError("La contraseña no puede estar vacia");
            } else if ((editTextCorreo.getText().toString().isEmpty()) && (editTextComprobarContraseña.getText().toString().isEmpty())) {
                editTextCorreo.setError("El correo no puede estar vacio");
                editTextComprobarContraseña.setError("La comprobacion de contraseña no puede estar vacia");
            } else if ((gene == "V") && (editTextContraseña.getText().toString().isEmpty())) {
                Toast.makeText(getApplicationContext(), "No se permite el genero vacio", Toast.LENGTH_SHORT).show();
                editTextContraseña.setError("La contraseña no puede estar vacia");
            } else if ((gene == "V") && (editTextComprobarContraseña.getText().toString().isEmpty())) {
                Toast.makeText(getApplicationContext(), "No se permite el genero vacio", Toast.LENGTH_SHORT).show();
                editTextComprobarContraseña.setError("La comprobacion de contraseña no puede estar vacia");
            } else if ((editTextContraseña.getText().toString().isEmpty()) && (editTextComprobarContraseña.getText().toString().isEmpty())) {
                editTextContraseña.setError("La contraseña no puede estar vacia");
                editTextComprobarContraseña.setError("La comprobacion de contraseña no puede estar vacia");
            } else if (editTextNombre.getText().toString().isEmpty()) {
                editTextNombre.setError("El nombre no puede estar vacio");
            } else if (editTextApellidos.getText().toString().isEmpty()) {
                editTextApellidos.setError("Los apellidos no pueden estar vacios");
            } else if (editTextCorreo.getText().toString().isEmpty()) {
                editTextCorreo.setError("El correo no puede estar vacio");
            } else if (gene == "V") {

                Toast.makeText(getApplicationContext(), "No se permite el genero vacio", Toast.LENGTH_SHORT).show();
            } else if (editTextContraseña.getText().toString().isEmpty()) {
                editTextContraseña.setError("La contraseña no puede estar vacia");
            } else if (editTextComprobarContraseña.getText().toString().isEmpty()) {
                editTextComprobarContraseña.setError("La comprobacion de contraseña no puede estar vacia");

            } else if (!(editTextContraseña.getText().toString().equals(editTextComprobarContraseña.getText().toString()))) {
                Toast.makeText(this,"contraseña no  coinciden",Toast.LENGTH_SHORT).show();
            }
            else if (!validarEmail(editTextCorreo.getText().toString())){
                editTextCorreo.setError("Formato Email no válido");
            }
            /*else if( (editTextCorreo.getText().toString().contains(" "))||(editTextContraseña.getText().toString().contains(" "))||(editTextComprobarContraseña.getText().toString().contains(" "))){
                Toast.makeText(this,"No puede contener espacios ",Toast.LENGTH_SHORT).show();

            }*/

            else {
                ejecutarServicio("https://descubremadrid.xyz/descubreMadrid/registro.php");

            }

        }

    public void onBackPressed() {
        moveTaskToBack(true);
    }

    private boolean validarEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;

        return pattern.matcher(email).matches();
    }

}
