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


//Para poder comportarse como un adaptador se extiende del RecyclerView.Adapter
//Solo que se edito lo qe esta dentro del menoy y mayor que para poder crear una clase de tipo Viewholder
//El cual nos ayudara a ser ni que chingados pero asi estaba en el video
//es algo inecesario pero en ese momento no lo sabia y asi se quedo y se chingan
public class Adaptador_Classes extends RecyclerView.Adapter<Adaptador_Classes.ViewHolder>{
    //ESte es el importante mi hijo preferido XD
    public List<Classes> mdata;
    private final List<Classes> Original_List;
    //se crearon 2 objetos del mimo tipo ya quelos necesitaremos a la hora de hacer un filtro
    private final LayoutInflater mInflater;
    private final Context context;

    public Adaptador_Classes(List<Classes> itemList, Context context){
        this.mInflater = LayoutInflater.from(context);
        this.context= context;
        this.mdata = itemList;
        this.Original_List = new ArrayList<>();
        Original_List.addAll(itemList);
    }



    //En este metodo se infla ins vista la cual nos traera los atrbutos de la tarjeta
    @NonNull
    @Override
    public Adaptador_Classes.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.tarjeta_classes,null);
        //retornaremos un Adaptador_Classes.ViewHolder el cual en esa linea llamara  al metodo ViewHolder
        //donde se asignaran los objetos por id a otros creados
        //mas abajo se entiende mejor
        return new Adaptador_Classes.ViewHolder(view);
    }

    //aca es el importante por asi decirlo
    //este obtiene la clase y la manda a un metodo llamado binData
    //en ese metodo cambiaremos los atributos de la tarjeta por los que tiene la clase
    //aca tambien editaremos el OnClickListener del botoneliminar para que llame a una clase que nos ayudara a
    //poder eliminar una tarjet<<mas abajo se entiende mejor>>
    //Tambien editaremos el onclic listener TODA la tarjerta para que cuando demos click an esta
    //realice la accion que querramos
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

    //estemetodo solo sierve para saber el tamaño de la items en el adaptador
    //la verdad no se me ocurre pera que usarlo pero alli esta
    @Override
    public int getItemCount() {return mdata.size();}

    //Este metodo sirve para cambier la lista de datos
    //La uso cuando hay cambios desde el fragment
    //pero en el delete no lo puedo usar
    public void setItems(List<Classes>items){mdata = items;}


    //metodo pa filtrar
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
        TextView nombre_clase;
        ImageButton boton;
        String id;
        Adaptador_Classes Adapter = new Adaptador_Classes(mdata,context);

        ViewHolder(View itemview){
            super(itemview);
            //aca inicializamos las variables pi ID se puede hacer atravez del view
            //que se envio mas arribita
            //nada del oto mundo
            //Imagen = itemview.findViewById(R.id.background);
            nombre_clase = itemview.findViewById(R.id.nombre_grado);
            boton= itemview.findViewById(R.id.bttn_eliminar);
        }
        void bindData(final Classes item){
            //aca se cambian los textos o imagenes o color
            //dependiendo de lo que tenga la clase y la tarjeta
            //y ya creo
            //Imagen.setBackgroundColor(Color.parseColor(item.getColor()));
            nombre_clase.setText(item.getClass_name());
            id= item.getId_class();
        }
    }

    //este es el metodo mas importante!!!!!!!
    //nos sirve para eliminar una tarjeta en la base de datos y en el recicler
    //implementamos el ONlickListener para que se comporte como uno
    //y sobreescribimos el Onclic listener
    public class eventoBorrar  implements View.OnClickListener{
        //Intanciamos un objet de tipoclase
        //y el contexto que a este punto ya paso por toda la app
        private Classes clase;
        Context ctx;
        //creamos el contructor el cual pide una clase y un context
        //la clase la usaremos para poder saber cual clase eliminar
        //y el contexto...
        ///te lo comes sin pretexto :)


        //generamos el contructor donde se inicializaran las variables antes creadas

        public eventoBorrar(Classes clase,Context contexto){
            this.clase = clase;
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
            AuxiliarClasses instancia = new first_fragment(context);

            //se crea el ALERTDIALOG
            AlertDialog.Builder alerta = new AlertDialog.Builder(context);
            alerta.setTitle("Confirmacion");
            alerta.setMessage("Esta seguro que desea Eliminar: " + clase.getClass_name());
            alerta.setCancelable(false);
            Context finalContext = context;
            alerta.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //Se llama a los metodos antes explicados
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
            //se muestra la alerta :)
            alerta.show();


        }
    }
}
