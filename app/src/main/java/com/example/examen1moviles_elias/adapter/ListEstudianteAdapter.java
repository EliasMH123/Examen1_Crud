package com.example.examen1moviles_elias.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.recyclerview.widget.RecyclerView;

import com.example.examen1moviles_elias.R;
import com.example.examen1moviles_elias.model.Estudiante;

import java.util.List;

public class ListEstudianteAdapter extends RecyclerView.Adapter<ListEstudianteAdapter.ViewHolder> {
    private List<Estudiante> data;
    private LayoutInflater inflater;
    private Context context;
    private OnCardListener mOnCardListener;

    public ListEstudianteAdapter(List<Estudiante> itemList, Context context, OnCardListener onCardListener){
        this.inflater = LayoutInflater.from(context);
        this.context = context;
        this.data = itemList;
        this.mOnCardListener = onCardListener;

    }
    @Override
    public int getItemCount(){ return data.size();}

    @Override
    public ListEstudianteAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_element_est, null);
        return new ListEstudianteAdapter.ViewHolder(view, mOnCardListener);
    }

    @Override
    public void onBindViewHolder(final ListEstudianteAdapter.ViewHolder holder, int position) {
        holder.bindData(data.get(position));
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView imageView;
        TextView nombres, codigo, dni, direccion, correo, carrera;
        OnCardListener onCardListener;
        ViewHolder(View itemView, OnCardListener onCardListener){
            super(itemView);
            imageView = itemView.findViewById(R.id.iconImageView1);
            nombres = itemView.findViewById(R.id.txt_nombres);
            codigo = itemView.findViewById(R.id.txt_codigo);
            correo = itemView.findViewById(R.id.txt_correo);
            dni = itemView.findViewById(R.id.txt_dni);
            direccion = itemView.findViewById(R.id.txt_direccion);
            carrera = itemView.findViewById(R.id.txt_carrera);
            this.onCardListener = onCardListener;
            itemView.setOnClickListener(this);
        }
        @Override public void onClick(View view ){ onCardListener.onCardClick(getAdapterPosition());}

        void bindData(final Estudiante item){
            nombres.setText(item.getNombre_apellido());
            codigo.setText(item.getCodigo());
            correo.setText(item.getCorreo());
            dni.setText(item.getDni());
            direccion.setText(item.getDireccion());
            carrera.setText(item.getCarrera_profes());
        }
    }
    public interface OnCardListener{
        void onCardClick(int position);
    }
}
