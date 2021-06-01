package com.example.descubremadrid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class CardQV extends AppCompatActivity {

    private static String URL="https://descubremadrid.xyz/descubreMadrid/coordenadasLugar.php?idPersona=14";

    List<listaQV> elementosqv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_q_v);

        iniciar();
    }

    private void iniciar() {

        elementosqv = new ArrayList<>();

        adaptadorQV adaptadorQV = new adaptadorQV(elementosqv, this);
        RecyclerView recyclerView = findViewById(R.id.listRecyclerViewQV);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adaptadorQV);

    }
}