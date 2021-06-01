package com.example.descubremadrid;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.opengl.GLES30;
import android.os.Build;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mapbox.geojson.Point;


import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.mapbox.mapboxsdk.Mapbox.getApplicationContext;

public class adaptadorQV extends RecyclerView.Adapter<adaptadorQV.ViewHolder>
        implements View.OnClickListener{

    private View.OnClickListener listenerqv;

double total;
double tiempo;
double longitud,latitud;

    private List<listaQV> listqv;
    private LayoutInflater mInflaterqv;
    public Context contextqv;
    listaQV listaQV;
    public ArrayList<Point> stops = new ArrayList<>();
    public ArrayList<String> prueba=new ArrayList<>();
    SparseBooleanArray itemStateArray= new SparseBooleanArray();



    private List<listaQV> elementosOriginalesqv;

    public adaptadorQV(List<listaQV> itemList,Context context) {
        this.mInflaterqv = LayoutInflater.from(context);
        this.contextqv = context;
        this.listqv = itemList;
        this.elementosOriginalesqv = new ArrayList<>();
        elementosOriginalesqv.addAll(listqv);
    }

    @NonNull
    @Override
    public adaptadorQV.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = mInflaterqv.inflate(R.layout.activity_lista_q_v, null);
        view.setOnClickListener(this);
        return new adaptadorQV.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull adaptadorQV.ViewHolder holder, final int position) {

        listaQV = listqv.get(position);

        holder.codigoqv.setText(listaQV.getIdLugar());
        holder.nombreqv.setText(listaQV.getNombreLugar());
        holder.tipoqv.setText(listaQV.getTipoLugar());
        holder.precioAdultoqv.setText(String.valueOf(listaQV.getPrecioLugar()+"€"));
        holder.tiempoVisitaqv.setText(String.valueOf( String.format("%.2f", listaQV.getTiempoLugar()/60))+"h");
        holder.latitudqv.setText(String.valueOf(listaQV.getLatitud()));
        holder.longitudqv.setText(String.valueOf(listaQV.getLongitud()));

        holder.nombreqv.setOnCheckedChangeListener(null);
        holder.nombreqv.setChecked(listaQV.isSeleccionado());

        holder.nombreqv.setChecked(itemStateArray.get(position));
        holder.nombreqv.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                int i=0;
                double b,a;




                            if (isChecked == true ) {
                                if(prueba.size()>11) {

                                    Toast.makeText(contextqv.getApplicationContext(),"no se puede añadir mas lugares",Toast.LENGTH_LONG).show();


                                }else{
                                    if(prueba.size()==10){
                                        latitud=listqv.get(position).getLatitud();
                                        longitud=listqv.get(position).getLongitud();
                                        stops.add(Point.fromLngLat(latitud,longitud));
                                        Intent intent= new Intent(contextqv,navegacionOptimizada.class);
                                        intent.putExtra("puntos", stops);
                                        contextqv.startActivity(intent);
                                        //intent.putExtra("puntos",puntos);


                                    }else{

                                       latitud=listqv.get(position).getLatitud();
                                       longitud=listqv.get(position).getLongitud();
                                        stops.add(Point.fromLngLat(latitud,longitud));
                                        itemStateArray.put(position,true);

                                        prueba.add(listqv.get(position).getNombreLugar());
                                        total=total+listqv.get(position).getPrecioLugar();
                                        tiempo=tiempo+(listqv.get(position).getTiempoLugar()/60);

                                    }

                                }

                        } else {

                                latitud=listqv.get(position).getLatitud();
                                       longitud=listqv.get(position).getLongitud();
                            stops.remove(Point.fromLngLat(latitud,longitud));
                                prueba.remove(listqv.get(position).getNombreLugar());

                                total=total-listqv.get(position).getPrecioLugar();
                                tiempo=tiempo-(listqv.get(position).getTiempoLugar()/60);
                            }



            }
        });






    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listenerqv = listener;


    }


    @Override
    public int getItemCount() {
        return listqv.size();

    }





    public void setItems(List<listaQV> items){listqv = items;}

    @Override
    public void onClick(View v) {
        if(listenerqv!=null){
            listenerqv.onClick(v);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView codigoqv,  tipoqv, precioAdultoqv, tiempoVisitaqv, longitudqv, latitudqv;
        CheckBox nombreqv;
        Button button;


        ViewHolder(View itemView){
            super(itemView);


            codigoqv = itemView.findViewById(R.id.IdLugarCV);
            nombreqv = itemView.findViewById(R.id.checkBox);
            tipoqv = itemView.findViewById(R.id.TipoCV);
            precioAdultoqv = itemView.findViewById(R.id.precioVisita);
            tiempoVisitaqv = itemView.findViewById(R.id.tiempoVisita);
            longitudqv = itemView.findViewById(R.id.textView5);
            latitudqv=itemView.findViewById(R.id.textView6);





        }

        public void onViewRecycled(@NonNull ViewHolder holder) {
            if (nombreqv != null) {
                nombreqv.setOnClickListener(null);
            }

        }


        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            if (!itemStateArray.get(adapterPosition, false)) {
                nombreqv.setChecked(true);

                itemStateArray.put(adapterPosition, true);
            }
            else  {
                nombreqv.setChecked(false);

                itemStateArray.put(adapterPosition, false);
            }
        }

    }

}
