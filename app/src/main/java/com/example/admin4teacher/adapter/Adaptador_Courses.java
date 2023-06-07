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


import com.example.admin4teacher.Cursos;
import com.example.admin4teacher.Interfaces.AuxiliarClasses;
import com.example.admin4teacher.Interfaces.AuxiliarCourses;
import com.example.admin4teacher.R;
import com.example.admin4teacher.first_fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import persistencia.Classes;
import persistencia.Course;


public class Adaptador_Courses extends RecyclerView.Adapter<Adaptador_Courses.ViewHolder> {

    public List<Course> mdata;
    private final List<Course> Original_List;
    //se crearon 2 objetos del mimo tipo ya quelos necesitaremos a la hora de hacer un filtro
    private final LayoutInflater mInflater;
    private final Context context;

    public Adaptador_Courses(List<Course> itemList, Context context){
        this.mInflater = LayoutInflater.from(context);
        this.context= context;
        this.mdata = itemList;
        this.Original_List = new ArrayList<>();
        Original_List.addAll(itemList);
    }



    //En este metodo se infla ins vista la cual nos traera los atrbutos de la tarjeta
    @NonNull
    @Override
    public Adaptador_Courses.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.tarjeta_cursos,null);
        //retornaremos un Adaptador_Classes.ViewHolder el cual en esa linea llamara  al metodo ViewHolder
        //donde se asignaran los objetos por id a otros creados
        //mas abajo se entiende mejor
        return new Adaptador_Courses.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Course curso = mdata.get(position);
        holder.bindData(mdata.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,"has dado clic en:"+holder.id+", "+holder.nombre_curso.getText().toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });
        holder.boton.setOnClickListener(new Adaptador_Courses.eventoBorrar(curso,context));
    }

    //aca es el importante por asi decirlo
    //este obtiene la clase y la manda a un metodo llamado binData
    //en ese metodo cambiaremos los atributos de la tarjeta por los que tiene la clase
    //aca tambien editaremos el OnClickListener del botoneliminar para que llame a una clase que nos ayudara a
    //poder eliminar una tarjet<<mas abajo se entiende mejor>>
    //Tambien editaremos el onclic listener TODA la tarjerta para que cuando demos click an esta
    //realice la accion que querramos


    //estemetodo solo sierve para saber el tamaño de la items en el adaptador
    //la verdad no se me ocurre pera que usarlo pero alli esta
    @Override
    public int getItemCount() {return mdata.size();}

    //Este metodo sirve para cambier la lista de datos
    //La uso cuando hay cambios desde el fragment
    //pero en el delete no lo puedo usar
    public void setItems(List<Course>items){mdata = items;}


    //metodo pa filtrar
    public void filtro(final String strSearch) {
        if (strSearch.length() == 0) {
            mdata.clear();
            mdata.addAll(Original_List);
        }
        else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                mdata.clear();
                List<Course> collect = Original_List.stream()
                        .filter(i -> i.getCourseName().toLowerCase().contains(strSearch))
                        .collect(Collectors.toList());

                mdata.addAll(collect);
            }
            else {
                mdata.clear();
                for (Course i : Original_List) {
                    if (i.getCourseName().toLowerCase().contains(strSearch)) {
                        mdata.add(i);
                    }
                }
            }
        }
        notifyDataSetChanged();
    }
    //metodo para eliminar nunca lo use no me funciono
    //se queda pa el recuerdo
    //metodos que me mantienen humilde
    public void eliminar(Classes clase){
        mdata.remove(clase);
        notifyDataSetChanged();

    }

    //el View Holder que mecionaba antes
    //aca unicamente es asignar datos de las variables dependiendo de la vista y de la clase
    //nad del otro mundo
    public class ViewHolder extends RecyclerView.ViewHolder{
        //aca unicamente se crean las variables que tenga la tarjeta
        //y las que necesiten dependiendo de lo que quieran hacer
        //RelativeLayout Imagen;
        TextView nombre_curso;
        ImageButton boton;
        String id;
        Adaptador_Courses Adapter = new Adaptador_Courses(mdata,context);

        ViewHolder(View itemview){
            super(itemview);
            //aca inicializamos las variables pi ID se puede hacer atravez del view
            //que se envio mas arribita
            //nada del oto mundo
            //Imagen = itemview.findViewById(R.id.background);
            nombre_curso = itemview.findViewById(R.id.nombre_curso);
            boton= itemview.findViewById(R.id.bttn_eliminarCurso);
        }
        void bindData(final Course item){
            //aca se cambian los textos o imagenes o color
            //dependiendo de lo que tenga la clase y la tarjeta
            //y ya creo
            //Imagen.setBackgroundColor(Color.parseColor(item.getColor()));
            nombre_curso.setText(item.getCourseName());
            id= item.getIdCourse();
        }
    }

    //este es el metodo mas importante!!!!!!!
    //nos sirve para eliminar una tarjeta en la base de datos y en el recicler
    //implementamos el ONlickListener para que se comporte como uno
    //y sobreescribimos el Onclic listener
    public class eventoBorrar  implements View.OnClickListener{
        //Intanciamos un objet de tipoclase
        //y el contexto que a este punto ya paso por toda la app
        private Course curso;
        Context ctx;
        //creamos el contructor el cual pide una clase y un context
        //la clase la usaremos para poder saber cual clase eliminar
        //y el contexto...
        ///te lo comes sin pretexto :)


        //generamos el contructor donde se inicializaran las variables antes creadas

        public eventoBorrar(Course curso,Context contexto){
            this.curso = curso;
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
            AuxiliarCourses instancia = new Cursos(context);

            //se crea el ALERTDIALOG
            AlertDialog.Builder alerta = new AlertDialog.Builder(context);
            alerta.setTitle("Confirmacion");
            alerta.setMessage("Esta seguro que desea Eliminar: " + curso.getCourseName());
            alerta.setCancelable(false);
            Context finalContext = context;
            alerta.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //Se llama a los metodos antes explicados
                    instancia.opcionEliminar(curso,context);
                    mdata.remove(curso);
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
