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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.descubremadrid.ListaElementos;


import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.mapbox.mapboxsdk.Mapbox.getApplicationContext;

public class ListaAdaptador extends RecyclerView.Adapter<ListaAdaptador.ViewHolder>
        implements View.OnClickListener{


    private View.OnClickListener listener;


    private List<ListaElementos> mData;

    private LayoutInflater mInflater;
    private Context context;


    ListaElementos listElement;




    private List<ListaElementos> elementosOriginales;

    public ListaAdaptador(List<ListaElementos> itemList,Context context) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.mData = itemList;
        this.elementosOriginales = new ArrayList<>();
        elementosOriginales.addAll(mData);





    }

    @NonNull
    @Override
    public ListaAdaptador.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = mInflater.inflate(R.layout.activity_lista_elementos, null);
        view.setOnClickListener(this);


        return new ListaAdaptador.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListaAdaptador.ViewHolder holder, final int position) {

        listElement = mData.get(position);

        holder.codigo.setText(listElement.getIdLugar());
        holder.nombre.setText(listElement.getNombre());
        holder.tipo.setText(listElement.getTipo());
        Glide.with(context).load(listElement.getDireccion()).into(holder.imageView);

    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void filter(final String strSearch){

        if (strSearch.length() ==0){
            mData.clear();
            mData.addAll(elementosOriginales);
        }
        else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

                List<ListaElementos> collect = mData.stream()
                        .filter(i -> i.getNombre().toUpperCase().contains(strSearch))
                        .collect(Collectors.toList());

                mData.clear();
                mData.addAll(collect);

            }

            else {
                mData.clear();
                for(ListaElementos i : elementosOriginales){
                    if (i.getNombre().toUpperCase().contains(strSearch)){
                        mData.add(i);
                    }
                }
            }

        }

        notifyDataSetChanged();

    }

    public void setItems(List<ListaElementos> items){mData = items;}

    @Override
    public void onClick(View v) {
        if(listener!=null){
            listener.onClick(v);
        }
    }





    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView codigo, nombre, tipo;

        ViewHolder(View itemView){
            super(itemView);

            codigo = itemView.findViewById(R.id.IdLugarCV);
            nombre = itemView.findViewById(R.id.NombreCV);
            tipo = itemView.findViewById(R.id.TipoCV);
            imageView = itemView.findViewById(R.id.imagenCV);

        }


    }

}
