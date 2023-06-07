package com.example.admin4teacher.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.admin4teacher.Actividades;
import com.example.admin4teacher.ListaActividades;
import com.example.admin4teacher.R;

import java.util.ArrayList;
import java.util.List;

import persistencia.Activities;

public class AdaptadorActividadesbyCourse extends RecyclerView.Adapter<AdaptadorActividadesbyCourse.ViewHolder>{
    private List<Activities> mdata;
    private final List<Activities> Original_List;
    private final LayoutInflater mInflater;
    private final Context context;

    public AdaptadorActividadesbyCourse(List<Activities> itemList, Context context) {
        this.mInflater = LayoutInflater.from(context);
        this.context= context;
        this.mdata = itemList;
        this.Original_List = new ArrayList<>();
        Original_List.addAll(itemList);
    }

    @NonNull
    @Override
    public AdaptadorActividadesbyCourse.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.tarjeta_actividades_by_course,null);
        return new AdaptadorActividadesbyCourse.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorActividadesbyCourse.ViewHolder holder, int position) {
        Activities tarea = mdata.get(position);
        holder.bindData(mdata.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,"has dado en: "+tarea.getIdActivity()+", "+tarea.getTitle(),Toast.LENGTH_LONG).show();
                Intent i = new Intent(context, Actividades.class);
                i.putExtra("title", tarea.getTitle());
                i.putExtra("description", tarea.getDescription());
                i.putExtra("date", tarea.getFinishDate());
                context.startActivity(i);
            }
        });
        holder.boton.setOnClickListener(new AdaptadorActividadesbyCourse.eventodelete(tarea,context));
    }

    @Override
    public int getItemCount() {return mdata.size();}
    public void setItems(List<Activities>items){mdata = items;}


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView titulo,grado,fecha;
        ImageButton boton;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titulo = itemView.findViewById(R.id.titulo_actividad);
            grado = itemView.findViewById(R.id.nombre_grado_actividad);
            fecha = itemView.findViewById(R.id.txt_fecha_actividad);
            boton = itemView.findViewById(R.id.bttn_eliminar_actividades_2);
        }
        void bindData(final Activities item){
            titulo.setText(item.getTitle());
            grado.setText(item.getCourseName());
            fecha.setText(item.getFinishDate());
        }
    }
    public class eventodelete implements View.OnClickListener{
        Activities tarea;
        Context ctx;

        public eventodelete(Activities tarea, Context ctx) {
            this.tarea = tarea;
            this.ctx = ctx;
        }

        @Override
        public void onClick(View view) {

            ListaActividades instancias = new ListaActividades();
            AlertDialog.Builder alerta = new AlertDialog.Builder(context);
            alerta.setTitle("Confirmacion");
            alerta.setMessage("Esta seguro que desea Eliminar: " + tarea.getCourseName());
            alerta.setCancelable(false);
            alerta.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    //instancias.opcionEliminar(tarea,context);
                    RequestQueue rqq = Volley.newRequestQueue(context);
                    instancias.deleteActivity(tarea.getIdActivity(),tarea.getIdCourse(),rqq,context);
                    mdata.remove(tarea);
                    notifyDataSetChanged();
                }
            });
            alerta.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            //se muestra la alerta :)
            alerta.show();
        }

    }
}
