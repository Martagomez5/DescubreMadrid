package com.example.descubremadrid;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.opengl.GLES30;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;


import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class adaptadorQV extends RecyclerView.Adapter<adaptadorQV.ViewHolder>
        implements View.OnClickListener{

    private View.OnClickListener listenerqv;


    private List<listaQV> listqv;
    private LayoutInflater mInflaterqv;
    private Context contextqv;
    listaQV listaQV;

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
        holder.precioAdultoqv.setText(listaQV.getPrecioLugar());
        holder.tiempoVisitaqv.setText(listaQV.getTiempoLugar());
        holder.latitudqv.setText(String.valueOf(listaQV.getLatitud()));
        holder.longitudqv.setText(String.valueOf(listaQV.getLongitud()));


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

        TextView codigoqv, nombreqv, tipoqv, precioAdultoqv, tiempoVisitaqv, longitudqv, latitudqv;

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

    }

}
