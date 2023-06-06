package com.example.admin4teacher;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
//En esta actividad se ven los cursos y estudiantes
public class Grado extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grado);
    }
    //Cambiar pantalla a cursos
    public void irCurso(View view){
        Intent i = new Intent(Grado.this,Cursos.class);
        startActivity(i);
    }

    //Cambiar pantalla a estudiantes
    public void irEstudiantes(View view){
        Intent i = new Intent(Grado.this,Estudiantes.class);
        startActivity(i);
    }

}