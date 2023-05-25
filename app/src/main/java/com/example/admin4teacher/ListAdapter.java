package com.example.admin4teacher;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
    private List<ListElement> mdata;
    private List<ListElement> Original_List;
    private LayoutInflater mInflater;
    private Context context;

    public ListAdapter(List<ListElement> itemList, Context context){
        this.mInflater = LayoutInflater.from(context);
        this.context= context;
        this.mdata = itemList;
        this.Original_List = new ArrayList<>();
        Original_List.addAll(itemList);
    }

    @NonNull
    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.tarjeta,null);
        return new ListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final  ListAdapter.ViewHolder holder, int position) {
        holder.bindData(mdata.get(position));
    }

    @Override
    public int getItemCount() {return mdata.size();}

    public void setItems(List<ListElement>items){mdata = items;}

    public void filtro(final String strSearch) {
        if (strSearch.length() == 0) {
            mdata.clear();
            mdata.addAll(Original_List);
        }
        else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                mdata.clear();
                List<ListElement> collect = Original_List.stream()
                        .filter(i -> i.getMatertia().toLowerCase().contains(strSearch))
                        .collect(Collectors.toList());

                mdata.addAll(collect);
            }
            else {
                mdata.clear();
                for (ListElement i : Original_List) {
                    if (i.getMatertia().toLowerCase().contains(strSearch)) {
                        mdata.add(i);
                    }
                }
            }
        }
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView Imagen;
        TextView Curso,Grado,Estudiantes;

        ViewHolder(View itemview){
            super(itemview);
            Imagen = itemview.findViewById(R.id.imageView);
            Curso = itemview.findViewById(R.id.nombre_curso);
            Grado = itemview.findViewById(R.id.nombre_grado);
            Estudiantes = itemview.findViewById(R.id.cant_estudiantes);
        }
        void bindData(final ListElement item){
            Imagen.setColorFilter(Color.parseColor(item.getColor()), PorterDuff.Mode.SRC_IN);
            Curso.setText(item.getMatertia());
            Grado.setText(item.getGrado());
            Estudiantes.setText(item.getEstudiantes());
        }
    }
}