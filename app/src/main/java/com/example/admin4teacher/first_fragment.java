package com.example.admin4teacher;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.admin4teacher.Interfaces.AuxiliarClasses;
import com.example.admin4teacher.adapter.Adaptador_Classes;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

import persistencia.Classes;
import persistencia.class_consulta;


public class first_fragment extends Fragment implements
        class_consulta.QueryResultListener,
        class_consulta.DeleteResultListener,
        AuxiliarClasses,
        class_consulta.InsertResultListener{
    // estos 2 no sirven pero ya venian asi que los deje
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    // Importante decir es que en muchos metodos pido el contexto
    //mas en la linea de eventos para elimiar
    //esto debido a que se creo otra instancia desde una clase donde no se creo la vista
    //por lo que no se puede acceder a todos los objetos inicializado en el metodo OnCreate View
    //por eso mando el contexto para que se pueda hacer toas las acciones que se van necesitando
    List<Classes> elements;
    Adaptador_Classes Adapter;
    RecyclerView recyclerView;

    RequestQueue rq;

    AppCompatActivity activity ;
    FloatingActionButton add_class;
    Context ctx;
    View root;
    String idUser;


    //debido a que hay problemas con el contexto cree otro constructor
    // el cual pide el contexto del activity asi me ahorro problemas
    public first_fragment(Context ctx, String id) {
        this.ctx = ctx;
        this.idUser = id;
    }
    public first_fragment(Context ctx) {this.ctx = ctx;}

    public first_fragment() {
        // Required empty public constructor
    }

    public static first_fragment newInstance(String param1, String param2) {
        first_fragment fragment = new first_fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        //Instancio estas variables para que se puedan utilizar independientemente de que si se crea la vista o no
        // desde cualquier otra instancia
        elements = new ArrayList<>();
        activity = (AppCompatActivity) getActivity();
        rq = Volley.newRequestQueue(ctx);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root =  inflater.inflate(R.layout.fragment_first_fragment, container, false);
        recyclerView = root.findViewById(R.id.layout_RV_classes);
        add_class = (FloatingActionButton)root.findViewById(R.id.idFabAgregarclase);

        //cambio las dimeciones del recicler y el manajer para que sea un gril de 2 columnas
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));


        //este boton corresponde al boton flotante agregar
        //aun no he agregado el cuadro de dialogo, pero funciona
        add_class.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = requireActivity().getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.dialog_add_class, null);

                TextInputLayout box = dialogView.findViewById(R.id.txtDialogClassName);
                EditText dialogEditTextName =  box.findViewById(R.id.editClaseName);



                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(requireContext());
                dialogBuilder.setTitle("Ingrese el nombre de la clase");
                dialogBuilder.setView(dialogView);
                dialogBuilder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(!dialogEditTextName.getText().toString().isEmpty()) {
                            String nombre = dialogEditTextName.getText().toString();
                            add_class(nombre, idUser);
                        }else{
                            Toast.makeText(getContext(), "debes agregar un nombre ala clase", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                dialogBuilder.setNegativeButton("Cancelar", null);

                // Muestra el cuadro de diálogo
                AlertDialog dialog = dialogBuilder.create();
                dialog.show();


            }});

        //init es el metodo que consulta la ta classes y debuelve todas las que esten alli
        init();
        return root;
    }


    //metodo que ocurre si la consulta sucedio con exito y
    // cambia el adaptador del recicler al nuevo con la lista de classes obtenida
    @Override
    public void onQuerySuccess(List<Classes> lista) {
        Adapter = new Adaptador_Classes(lista,activity);
        recyclerView.setAdapter(Adapter);
    }

    //metodo si hubo un error en la consulta
    @Override
    public void onQueryError(String errorMessage) {
        Toast.makeText(activity,errorMessage,Toast.LENGTH_LONG).show();
    }

    //metodo si se elimino correctamente la clase
    @Override
    public void onDeleteSucces(List<Classes>  lista, Context contexto) {
        Toast.makeText(contexto,"Se elimino",Toast.LENGTH_SHORT).show();
    }

    //metodo si se inserto correctamente la clase
    // funciona casi igual que la consulta pero este tien un Toast :)
    @Override
    public void onInsertSucces(List<Classes>  lista) {
        Adapter = new Adaptador_Classes(lista,activity);
        recyclerView.setAdapter(Adapter);
        Toast.makeText(activity,"se ha agregado la clase",Toast.LENGTH_LONG).show();

    }


    //crea un objeto de la classe consulta y cambie el queryResultListener para que pueda mostrar el toast
    //luego ya solo llama a un metodo mandandolo los parametros que necesita

    public void init(){
        class_consulta cls_cs = new class_consulta();
        cls_cs.setqueryResultListener(this);
        cls_cs.query_class(rq,activity);
    }
    //lo mimo para los otros metodos solo que esos edito diferentes listener para mostrar diferentes Toast

    public void add_class(String className, String idUser){
        class_consulta cls_cs = new class_consulta();
        cls_cs.setInsertResultListener(this);
        cls_cs.add_class(className,idUser,rq,activity);
    }

    public void delete_class(String id,Context context){
        RequestQueue rqq = Volley.newRequestQueue(context);
        class_consulta cls_cs = new class_consulta(context);
        cls_cs.setDeleteResultListener(this);
        cls_cs.delete_class(id,rqq,context);
    }


    //Metodo implementado de la Interfaz AuxiliarClasses el cual me permitira eliminar una clase
    @Override
    public void opcionEliminar(@NonNull final Classes clase, Context context) {delete_class(clase.getId_class(),context);}
    }
