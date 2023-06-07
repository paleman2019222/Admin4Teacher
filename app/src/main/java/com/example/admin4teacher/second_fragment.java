package com.example.admin4teacher;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.admin4teacher.adapter.Adaptador_Actividades;
import com.getbase.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import persistencia.Activities;
import persistencia.Activities_Consulta;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link second_fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class second_fragment extends Fragment implements Activities_Consulta.InsertActivityResultListener,
        Activities_Consulta.DeletetActivityResultListener,
        Activities_Consulta.QueryActivityResultListener{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    List<Activities> elements;
    Adaptador_Actividades Adapter;
    RecyclerView recyclerView;
    Activities_Consulta Consulta;
    Context contexto;
    RequestQueue rq;
    AppCompatActivity activity;
    String idUser;

    public second_fragment( Context contexto,String id) {
        this.contexto = contexto;
        this.idUser = id;
    }

    public second_fragment() {
        // Required empty public constructor
    }

    public static second_fragment newInstance(String param1, String param2) {
        second_fragment fragment = new second_fragment();
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
        activity = (AppCompatActivity) getActivity();
        rq = Volley.newRequestQueue(contexto);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_second_fragment, container, false);

        elements = new ArrayList<>();
        Adapter = new Adaptador_Actividades(elements,getContext());

        recyclerView = root.findViewById(R.id.layout_actividades);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(contexto,LinearLayoutManager.VERTICAL,false));
        FloatingActionButton bttn_add_activity = (FloatingActionButton)root.findViewById(R.id.idFabAgregar_Actividad);
        bttn_add_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        init();
        return root;
    }

    void init(){
        Consulta = new Activities_Consulta();
        Consulta.setQueryActivityResultListener(this);
        Consulta.query_activity(idUser,rq,getContext());
    }
    void delete(){

    }
    void add(){

    }


    @Override
    public void onInsertActivitySucces(List<Activities> list) {
        Adapter.setItems(list);
        recyclerView.setAdapter(Adapter);
    }

    @Override
    public void onInsertActivityError(String errorMenssage) {
        Toast.makeText(getContext(),errorMenssage,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDeleteActivitySucces(List<Activities> list) {
        Toast.makeText(getContext(),"Se elimino la clase",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDeleteActivityError(String errorMenssage) {
        Toast.makeText(getContext(),errorMenssage,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onQueryActivitySucces(List<Activities> list) {
        Log.i("elementos",list.toString());
        Adapter.setItems(list);
        recyclerView.setAdapter(Adapter);
    }

    @Override
    public void onQueryActivityError(String errorMenssage) {
        Toast.makeText(contexto,errorMenssage,Toast.LENGTH_SHORT).show();
    }
}
