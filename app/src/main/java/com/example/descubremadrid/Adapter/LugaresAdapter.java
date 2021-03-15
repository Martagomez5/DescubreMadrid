package com.example.descubremadrid.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.descubremadrid.R;

import java.util.List;

public class LugaresAdapter extends RecyclerView.Adapter<LugaresAdapter.ViewHolder>{

    private List<Lugares> lugaresList;
    private Context context;

    public LugaresAdapter(List<Lugares> lugaresList, Context context) {
        this.lugaresList = lugaresList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tarjeta_lugares,parent);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.txtNombre.setText(lugaresList.get(position).getNombre());
        holder.txtTipo.setText(lugaresList.get(position).getTipo());
        holder.txtDescripcion.setText(lugaresList.get(position).getDescripcion());

       Glide.with(context)
               .load(lugaresList.get(position).getFoto())
               .centerCrop()
               .into(holder.imgFoto);

    }

    @Override
    public int getItemCount() {
        return lugaresList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{


        private ImageView imgFoto;
        private TextView txtNombre;
        private TextView txtTipo;
        private TextView txtDescripcion;


        public ViewHolder(@NonNull View itemView) {

            super(itemView);

            imgFoto = itemView.findViewById(R.id.img_foto);
            txtNombre = itemView.findViewById(R.id.txt_nombre);
            txtTipo = itemView.findViewById(R.id.txt_Tipo);
            txtDescripcion = itemView.findViewById(R.id.txt_descripcion);

        }
    }
}
