package com.example.descubremadrid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.descubremadrid.Adapter.Lugares;
import com.example.descubremadrid.Adapter.LugaresAdapter;

import java.util.ArrayList;
import java.util.List;

public class Lugar extends AppCompatActivity {

    RecyclerView recyclerLugares;
    LugaresAdapter lugaresAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lugar);
        
        inicializarElementos();
    }

    private void inicializarElementos() {
        recyclerLugares=findViewById(R.id.idRecycler);
        recyclerLugares.setLayoutManager(new LinearLayoutManager(this));

        List<Lugares> lugarList = new ArrayList<>();


        for (int i = 0;i<20;i++){

            lugarList.add(new Lugares(i,"Bernabeu","Estadio","Estadio Real Madrid","foto"+i));
        }

        lugaresAdapter = new LugaresAdapter(lugarList,this);

    }
}