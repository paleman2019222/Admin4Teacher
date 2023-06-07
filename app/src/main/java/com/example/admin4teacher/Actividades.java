package com.example.admin4teacher;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class Actividades extends AppCompatActivity {
    String titulo,descripsion,fecha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividades);
        titulo = getIntent().getStringExtra("title");
        descripsion = getIntent().getStringExtra("description");
        fecha = getIntent().getStringExtra("date");

        TextView title = (TextView) findViewById(R.id.titulo_actividad_2);
        TextView description = (TextView) findViewById(R.id.txt_descripcion_2);
        TextView date = (TextView) findViewById(R.id.txt_fecha_actividad_2);

        title.setText(titulo);
        description.setText(descripsion);
        date.setText(fecha);
    }

}