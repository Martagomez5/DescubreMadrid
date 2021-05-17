package com.example.descubremadrid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RecycleViewCardView extends AppCompatActivity {

    private static String URL="https://descubremadrid.000webhostapp.com/descubreMadrid/datosLugares.php";

    List<ListaElementos> elementos;
    Spinner spinner;


    List<ListaElementos> elementosOriginales;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle_view_card_view);



        init();

    }

    private void init() {

        elementos = new ArrayList<>();

        ListaAdaptador listAdapter = new ListaAdaptador(elementos, this);
        RecyclerView recyclerView = findViewById(R.id.listRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(listAdapter);
        spinner=findViewById(R.id.spinner);
        ArrayAdapter adapter=ArrayAdapter.createFromResource(this,R.array.lenguajes, android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(adapterView.getItemAtPosition(i).equals("MUSEO")){
                    ArrayAdapter adapter1=ArrayAdapter.createFromResource(RecycleViewCardView.this,R.array.lenguajes, android.R.layout.simple_spinner_item);
                    Toast.makeText(getApplicationContext(),""+adapterView.getItemAtPosition(i),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



    }


}