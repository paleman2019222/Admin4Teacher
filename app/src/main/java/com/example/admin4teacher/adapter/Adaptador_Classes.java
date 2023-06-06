package com.example.admin4teacher.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;


import com.example.admin4teacher.Interfaces.AuxiliarClasses;
import com.example.admin4teacher.R;
import com.example.admin4teacher.first_fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import persistencia.Classes;

public class Adaptador_Classes extends RecyclerView.Adapter<Adaptador_Classes.ViewHolder>{
    public List<Classes> mdata;
    private final List<Classes> Original_List;
    private final LayoutInflater mInflater;
    private final Context context;

    public Adaptador_Classes(List<Classes> itemList, Context context){
        this.mInflater = LayoutInflater.from(context);
        this.context= context;
        this.mdata = itemList;
        this.Original_List = new ArrayList<>();
        Original_List.addAll(itemList);
    }



    @NonNull
    @Override
    public Adaptador_Classes.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.tarjeta_classes,null);

        return new Adaptador_Classes.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final  Adaptador_Classes.ViewHolder holder, int position) {
        Classes clase = mdata.get(position);
        holder.bindData(mdata.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,"has dado clic en:"+holder.id+", "+holder.nombre_clase.getText().toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });
        holder.boton.setOnClickListener(new eventoBorrar(clase,context));
    }

    @Override
    public int getItemCount() {return mdata.size();}

    public void setItems(List<Classes>items){mdata = items;}

    public void filtro(final String strSearch) {
        if (strSearch.length() == 0) {
            mdata.clear();
            mdata.addAll(Original_List);
        }
        else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                mdata.clear();
                List<Classes> collect = Original_List.stream()
                        .filter(i -> i.getClass_name().toLowerCase().contains(strSearch))
                        .collect(Collectors.toList());

                mdata.addAll(collect);
            }
            else {
                mdata.clear();
                for (Classes i : Original_List) {
                    if (i.getClass_name().toLowerCase().contains(strSearch)) {
                        mdata.add(i);
                    }
                }
            }
        }
        notifyDataSetChanged();
    }
    public void eliminar(Classes clase){
        mdata.remove(clase);
        notifyDataSetChanged();

    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        //RelativeLayout Imagen;
        TextView nombre_clase;
        ImageButton boton;
        String id;
        Adaptador_Classes Adapter = new Adaptador_Classes(mdata,context);

        ViewHolder(View itemview){
            super(itemview);
            //Imagen = itemview.findViewById(R.id.background);
            nombre_clase = itemview.findViewById(R.id.nombre_grado);
            boton= itemview.findViewById(R.id.bttn_eliminar);
        }
        void bindData(final Classes item){
            //Imagen.setBackgroundColor(Color.parseColor(item.getColor()));
            nombre_clase.setText(item.getClass_name());
            id= item.getId_class();
        }
    }
    public class eventoBorrar  implements View.OnClickListener{
        private Classes clase;
        Context ctx;

        public eventoBorrar(Classes clase,Context contexto){
            this.clase = clase;
            this.ctx=contexto;

        }


        @Override
        public void onClick(View view) {
            AuxiliarClasses instancia = new first_fragment(context);

            AlertDialog.Builder alerta = new AlertDialog.Builder(context);
            alerta.setTitle("Confirmacion");
            alerta.setMessage("Esta seguro que desea Eliminar: " + clase.getClass_name());
            alerta.setCancelable(false);
            Context finalContext = context;
            alerta.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    instancia.opcionEliminar(clase,context);
                    mdata.remove(clase);
                    notifyDataSetChanged();
                }
            });
            alerta.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            alerta.show();


        }
    }
}
