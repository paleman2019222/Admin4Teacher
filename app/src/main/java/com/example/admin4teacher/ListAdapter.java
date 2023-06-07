package com.example.admin4teacher;

import android.content.Context;
import android.content.DialogInterface;
import android.media.Image;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.admin4teacher.Interfaces.AuxiliarCourses;
import com.example.admin4teacher.Interfaces.AuxiliarStudents;
import com.example.admin4teacher.adapter.Adaptador_Courses;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import persistencia.Course;
import persistencia.Students;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
    private List<Students> mdata;
    private List<Students> Original_List;
    private LayoutInflater mInflater;
    private final Context context;


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
        Students student = mdata.get(position);
        holder.bindData(mdata.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Toast.makeText(context,"has dado clic en:"+holder.name.getText().toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });
        holder.eliminar.setOnClickListener(new ListAdapter.eventoBorrar(student,context));
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
        ImageButton eliminar;
        ViewHolder(View itemview){
            super(itemview);

            name = (TextView) itemview.findViewById(R.id.nombreStudent);
            lastname = (TextView) itemview.findViewById(R.id.apellidoStudent);
            eliminar = (ImageButton) itemview.findViewById(R.id.bttn_eliminarStudent);
        }
        void bindData(final Students item){

            //Curso.setText(item.getMatertia());
           // carnet.setText(item.getCarnet());
            name.setText(item.getName());
            lastname.setText(item.getLastname());

        }

        //este es el metodo mas importante!!!!!!!
        //nos sirve para eliminar una tarjeta en la base de datos y en el recicler
        //implementamos el ONlickListener para que se comporte como uno
        //y sobreescribimos el Onclic listener



    }
    public class eventoBorrar  implements View.OnClickListener{
        //Intanciamos un objet de tipoclase
        //y el contexto que a este punto ya paso por toda la app
        private Students student;
        Context ctx;
        //creamos el contructor el cual pide una clase y un context
        //la clase la usaremos para poder saber cual clase eliminar
        //y el contexto...
        ///te lo comes sin pretexto :)


        //generamos el contructor donde se inicializaran las variables antes creadas

        public eventoBorrar(Students student,Context contexto){
            this.student= student;
            this.ctx=contexto;

        }

        //sobre escribimos el metodo OncliListener
        //aca creamos un ALERTDIALOG donde preguntara si esta seguro de eliminar o no
        //xq sabemos que la gente es bien mula
        //si le da a que si se llama al metodo de la intefaz y aparte se elimina del recicler
        //La importancia de tener esta clase aca<< eventoBorrar>>
        // es xq no necesitamos instanciar para poder usar los metodos y variables de la clase Adaptador
        //por lo que tenemos acceso al mdata que sera importante para eliminar el item del recicler
        @Override
        public void onClick(View view) {
            //intanciamos un objeto de la interfaz la parate derecha del igual indica donde se usara
            //segun chat GPT duas pregunte alla yo no se xq
            AuxiliarStudents instancia = new Estudiantes(context);

            //se crea el ALERTDIALOG
            AlertDialog.Builder alerta = new AlertDialog.Builder(context);
            alerta.setTitle("Confirmacion");
            alerta.setMessage("Esta seguro que desea Eliminar: " + student.getName());
            alerta.setCancelable(false);
            Context finalContext = context;
            alerta.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //Se llama a los metodos antes explicados
                    instancia.opcionEliminar(student,context);
                    mdata.remove(student);
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
