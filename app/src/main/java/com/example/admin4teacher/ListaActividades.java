package com.example.admin4teacher;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.admin4teacher.adapter.Adaptador_Actividades;

import java.util.ArrayList;
import java.util.List;

import persistencia.Activities;
import persistencia.Activities_Consulta;

public class ListaActividades extends AppCompatActivity implements
        Activities_Consulta.InsertActivityResultListener,
        Activities_Consulta.DeletetActivityResultListener,
        Activities_Consulta.QueryActivityResultListener{
    List<Activities> elements;
    Adaptador_Actividades Adapter;
    RecyclerView RV;//recicler view contenedor de tarjetas de actividades
    RequestQueue rq;
    String idCourse, idActivity;
    Activities_Consulta consulta;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_actividades);
        RV = (RecyclerView) findViewById(R.id.layout_RV_estudents_curso);

        elements = new ArrayList<>();
        Adapter = new Adaptador_Actividades(elements,getApplicationContext());
        rq = Volley.newRequestQueue(getApplicationContext());
        consulta = new Activities_Consulta();

        RV.setHasFixedSize(true);
        RV.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false));

        init();
    }
    void init(){
        consulta.setQueryActivityResultListener(this);
        consulta.find_Activity_query("9",rq,getApplicationContext());
    }

    public void deleteActivity(String id){
        consulta.setDeletetActivityResultListener(this);
        consulta.find_Activity_query(id,rq,getApplicationContext());
    }
    void addActivity(){
        consulta.setInsertActivityResultListener(this);
        consulta.find_Activity_query(idCourse,rq,getApplicationContext());
    }
    @Override
    public void onInsertActivitySucces(List<Activities>  lista) {
        Adapter.setItems(lista);
        RV.setAdapter(Adapter);
    }

    @Override
    public void onInsertActivityError(String errorMenssage) {
        Toast.makeText(getApplicationContext(),errorMenssage,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDeleteActivitySucces(List<Activities>  lista) {
        Adapter.setItems(lista);
        RV.setAdapter(Adapter);
    }

    @Override
    public void onDeleteActivityError(String errorMenssage) {
        Toast.makeText(getApplicationContext(),errorMenssage,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onQueryActivitySucces(List<Activities> lista) {
        Adapter.setItems(lista);
        RV.setAdapter(Adapter);
    }

    @Override
    public void onQueryActivityError(String errorMenssage) {
        Toast.makeText(getApplicationContext(),errorMenssage,Toast.LENGTH_SHORT).show();
    }
}