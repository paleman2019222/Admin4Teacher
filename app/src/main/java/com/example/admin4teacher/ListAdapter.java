package com.example.admin4teacher;

import android.content.Context;
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

import persistencia.Students;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
    private List<Students> mdata;
    private List<Students> Original_List;
    private LayoutInflater mInflater;
    private Context context;

    public ListAdapter(List<Students> itemList, Context context){
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

    public void setItems(List<Students>items){mdata = items;}

    public void filtro(final String strSearch) {
        if (strSearch.length() == 0) {
            mdata.clear();
            mdata.addAll(Original_List);
        }
        else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                mdata.clear();
                List<Students> collect = Original_List.stream()
                        .filter(i -> i.getName().toLowerCase().contains(strSearch))
                        .collect(Collectors.toList());

                mdata.addAll(collect);
            }
            else {
                mdata.clear();
                for (Students i : Original_List) {
                    if (i.getName().toLowerCase().contains(strSearch)) {
                        mdata.add(i);
                    }
                }
            }
        }
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        //ImageView Imagen;
        TextView name, lastname, carnet;
        ImageView imagen;
        ViewHolder(View itemview){
            super(itemview);

            name = (TextView) itemview.findViewById(R.id.nombre_estudiante);
            lastname = (TextView) itemview.findViewById(R.id.apellido_estudiante);
            carnet = (TextView) itemview.findViewById(R.id.carnet_estudiante);
            imagen = (ImageView)  itemview.findViewById(R.id.img_user);
        }
        void bindData(final Students item){

            //Curso.setText(item.getMatertia());

            name.setText(item.getName());
            lastname.setText(item.getLastname());
        }
    }
}
