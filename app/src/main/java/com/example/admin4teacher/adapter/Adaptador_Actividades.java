package com.example.admin4teacher.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.admin4teacher.Grado;
import com.example.admin4teacher.R;

import java.util.ArrayList;
import java.util.List;

import persistencia.Activities;
import persistencia.Classes;

public class Adaptador_Actividades extends RecyclerView.Adapter<Adaptador_Actividades.ViewHolder>{
    private List<Activities> mdata;
    private List<Activities> Original_List;
    private LayoutInflater mInflater;
    private Context context;

    public Adaptador_Actividades(List<Activities> itemList, Context context) {
        this.mInflater = LayoutInflater.from(context);
        this.context= context;
        this.mdata = itemList;
        this.Original_List = new ArrayList<>();
        Original_List.addAll(itemList);
    }

    @NonNull
    @Override
    public Adaptador_Actividades.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.tarjeta_actividad,null);
        return new Adaptador_Actividades.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adaptador_Actividades.ViewHolder holder, int position) {
        Activities tarea = mdata.get(position);
        holder.bindData(mdata.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent i = new Intent(context, Grado.class);
                //context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {return mdata.size();}
    public void setItems(List<Activities>items){mdata = items;}


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView titulo,grado,fecha;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titulo = itemView.findViewById(R.id.titulo_actividad);
            grado = itemView.findViewById(R.id.nombre_grado_actividad);
            fecha = itemView.findViewById(R.id.txt_fecha_actividad);
        }
        void bindData(final Activities item){
            titulo.setText(item.getTitle());
            grado.setText(item.getCourseName());
            fecha.setText(item.getFinishDate());
        }
    }
}
