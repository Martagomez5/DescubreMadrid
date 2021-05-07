package com.example.descubremadrid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RecycleViewCardView extends AppCompatActivity {

    private static String URL="https://descubremadrid.000webhostapp.com/descubreMadrid/datosLugares.php";

    List<ListaElementos> elementos;


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

    }


}