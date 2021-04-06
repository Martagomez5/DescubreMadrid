package com.example.descubremadrid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder>{

    private List<Lugares> mData;
    private Context context;
    private LayoutInflater mInflater;

    public ListAdapter(List<Lugares> itemList, Context context) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.mData = itemList;
    }

    @NonNull
    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = mInflater.inflate(R.layout.tarjeta_lugares,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        holder.bindData(mData.get(position));

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setItems(List<Lugares> items){
        mData=items;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{


        private ImageView imgFoto;
        private TextView txtNombre;
        private TextView txtTipo;
        private TextView txtDescripcion;


         ViewHolder(@NonNull View itemView) {

            super(itemView);

            imgFoto = (ImageView) itemView.findViewById(R.id.iconImageView);
            txtNombre = itemView.findViewById(R.id.nameTextView);
            txtTipo = itemView.findViewById(R.id.descripcionTextView);
            txtDescripcion = itemView.findViewById(R.id.tipoTextView);

        }

         void bindData(final Lugares item){

           // imgFoto.setImageResource(Lugares);
            txtNombre.setText(item.getNombre());
            txtDescripcion.setText(item.getDescripcion());
            txtTipo.setText(item.getTipo());
        }
    }
}
