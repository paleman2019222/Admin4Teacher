package com.example.admin4teacher;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.List;

import persistencia.Students;
import persistencia.StudentsConsulta;
import persistencia.class_consulta;

public class Estudiantes extends AppCompatActivity implements StudentsConsulta.QueryStudentsResultListener, StudentsConsulta.DeleteStudentsResultListener, StudentsConsulta.InsertStudentsResultListener, SearchView.OnQueryTextListener {


    List<Students> elements;
    ListElement student;
    ListAdapter adapter;
    RecyclerView recycler;
    Students estudiante;

    Context context;
    String idClass;
    RequestQueue rq;; //request

    //public ArrayList<ListElement> listEstudiantes;

    //ListAdapter adaptador = new ListAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estudiantes);
        rq = Volley.newRequestQueue(getApplicationContext());
        elements = new ArrayList<Students>();
        adapter = new ListAdapter(elements,getApplicationContext());
        recycler = findViewById(R.id.layout_RV_estudents);

        recycler.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        StudentsConsulta consulta = new StudentsConsulta();
        consulta.setQueryStudentsResultListener(this);
        consulta.query_student("119",rq,this);
        //consulta.query_student();
        //elements.add(student);
        //recycler.setAdapter(adapter);

        SearchView svSearch = (SearchView) findViewById(R.id.busqueda_students);
        //initListener();

    }


    @Override
    public void onQuerySuccess(List<Students> elements) {
        adapter.setItems(elements);
        recycler.setAdapter(adapter);
    }

    @Override
    public void onQueryError(String errorMessage) {
        Toast.makeText(this,errorMessage,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDeleteSucces(List<Students> elements, Context contexto) {

    }

    @Override
    public void onInsertSucces(List<Students> elements) {
        adapter.setItems(elements);
        recycler.setAdapter(adapter);

    }

    public void agregar(){
        StudentsConsulta consulta = new StudentsConsulta();
        consulta.setInsertStudentsResultListener(this);
        consulta.add_student(idClass,rq,this);
    }


    //private void initListener(){svSearch.setOnQueryTextListener(this);}

    @Override
    public boolean onQueryTextSubmit(String query) {return false;}

    @Override
    public boolean onQueryTextChange(String newText) {
        adapter.filtro(newText);
        return false;
    }
}