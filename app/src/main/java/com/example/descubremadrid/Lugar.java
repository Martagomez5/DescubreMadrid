package com.example.descubremadrid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class Lugar extends AppCompatActivity {

    List<Lugar> elements;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lugar);
        
        init();
    }

    private void init() {

        elements = new ArrayList<>();
      //  elements.add(new Lugar("#775447","Bernabeu", "Estadio", "Estadio Real Madrid"));
        //elements.add(new Lugar("#607d8b","Wanda", "Estadio", "Estadio Atletico del Madrid"));

      //  ListAdapter listAdapter= new ListAdapter(elements,this);
       // RecyclerView recyclerView = findViewById(R.id.listRecyclerView);
      //  recyclerView.setHasFixedSize(true);
        //recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //recyclerView.setAdapter(listAdapter);

    }


}