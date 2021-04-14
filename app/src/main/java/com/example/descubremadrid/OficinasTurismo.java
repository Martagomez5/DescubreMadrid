package com.example.descubremadrid;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class OficinasTurismo extends AppCompatActivity {

    ViewPager viewPager;
    TextView Oficinas, Mapa;
    PageViewAdapter pageViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oficinas_turismo);

        Oficinas = findViewById(R.id.oficinas);
        Mapa = findViewById(R.id.mapaOficinas);

        viewPager=findViewById(R.id.FragmentContainer);

        Oficinas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(0);
            }
        });

        Mapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(1);
            }
        });

        pageViewAdapter = new PageViewAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pageViewAdapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

               // onChangeTab(position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private void onChangeTab(int position) {

        if (position==0){
            Oficinas.setTextSize(25);
            Mapa.setTextSize(15);
        }
        if (position==1){
            Oficinas.setTextSize(15);
            Mapa.setTextSize(25);
        }

    }
}