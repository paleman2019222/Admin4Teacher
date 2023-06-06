package com.example.admin4teacher;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class Estudiantes extends AppCompatActivity {

    public ArrayList listEstudiantes;
    
    //ListAdapter adaptador = new ListAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estudiantes);
    }



}