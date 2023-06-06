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
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.admin4teacher.Interfaces.AuxiliarClasses;
import com.example.admin4teacher.adapter.Adaptador_Classes;
import com.getbase.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import persistencia.Classes;
import persistencia.class_consulta;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link first_fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class first_fragment extends Fragment implements
        class_consulta.QueryResultListener,
        class_consulta.DeleteResultListener,
        AuxiliarClasses,
        class_consulta.InsertResultListener{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    List<Classes> elements;
    Adaptador_Classes Adapter;
    RecyclerView recyclerView;

    RequestQueue rq;

    AppCompatActivity activity ;
    FloatingActionButton add_class;
    Context ctx;
    View root;
    RecyclerView RV;
    public first_fragment(Context ctx) {
        this.ctx = ctx;
    }

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root =  inflater.inflate(R.layout.fragment_first_fragment, container, false);
        recyclerView = root.findViewById(R.id.layout_RV_classes);
        add_class = (FloatingActionButton)root.findViewById(R.id.idFabAgregarclase);


        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));


        add_class.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add_class("4to Bach","1");
            }});

        init();
        return root;
    }


    @Override
    public void onQuerySuccess(List<Classes> lista) {
        Adapter = new Adaptador_Classes(lista,activity);
        recyclerView.setAdapter(Adapter);
    }
    @Override
    public void onQueryError(String errorMessage) {
        Toast.makeText(activity,errorMessage,Toast.LENGTH_LONG).show();
    }
    @Override
    public void onDeleteSucces(List<Classes>  lista, Context contexto) {
        Toast.makeText(contexto,"Se elimino",Toast.LENGTH_SHORT).show();
    }
    public void reiniciarFragmento() {
        //recreate();
    }
    @Override
    public void onInsertSucces(List<Classes>  lista) {
        Adapter = new Adaptador_Classes(lista,activity);
        recyclerView.setAdapter(Adapter);
        Toast.makeText(activity,"se ha agregado la clase",Toast.LENGTH_LONG).show();

    }


    public void init(){
        class_consulta cls_cs = new class_consulta();
        cls_cs.setqueryResultListener(this);
        cls_cs.query_class(rq,activity);
    }

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
    @Override
    public void opcionEliminar(@NonNull final Classes clase, Context context) {delete_class(clase.getId_class(),context);}
    }
