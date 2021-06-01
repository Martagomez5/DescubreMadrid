package com.example.descubremadrid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class MenuItinerarios extends AppCompatActivity {
      ImageButton boton1,boton2,boton3,boton4,boton5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_itinerarios);
        boton1=findViewById(R.id.imageButton);
        boton2=findViewById(R.id.imageButton2);
        boton3=findViewById(R.id.imageButton5);
        boton4=findViewById(R.id.imageButton4);
        boton5=findViewById(R.id.imageButton6);

        boton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent(getApplicationContext(),infoItinerario1.class);
                startActivity(intent1);
            }
        });

        boton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2=new Intent(getApplicationContext(),Menu2Dias.class);
                startActivity(intent2);
            }
        });

        boton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent3=new Intent(getApplicationContext(),Menu3Dias.class);
                startActivity(intent3);
            }
        });

        boton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent4=new Intent(getApplicationContext(),Menu5Dias.class);
                startActivity(intent4);
            }
        });

        boton5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent5=new Intent(getApplicationContext(),Menu7Dias.class);
                startActivity(intent5);
            }
        });

    }
}