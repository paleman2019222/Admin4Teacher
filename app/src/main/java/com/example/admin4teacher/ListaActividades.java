package com.example.admin4teacher;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.admin4teacher.Interfaces.AuxiliarActivities;
import com.example.admin4teacher.adapter.AdaptadorActividadesbyCourse;
import com.getbase.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import persistencia.Activities;
import persistencia.Activities_Consulta;

public class ListaActividades extends AppCompatActivity implements
        Activities_Consulta.InsertActivityResultListener,
        Activities_Consulta.DeletetActivityResultListener,
        Activities_Consulta.QueryActivityResultListener,
        AuxiliarActivities {
    List<Activities> elements;
    AdaptadorActividadesbyCourse Adapter;
    RecyclerView RV;//recicler view contenedor de tarjetas de actividades
    RequestQueue rq;
    String idCourse;
    Activities_Consulta consulta;
    FloatingActionButton add_activity;
    Calendar calendario;
    EditText dialogEditTextPhone;
    Context ctx;

    public ListaActividades(Context ctx) {
        this.ctx = ctx;
    }
    public ListaActividades() {}


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_actividades);
        RV = (RecyclerView) findViewById(R.id.layout_RV_estudents_curso);
        add_activity = (FloatingActionButton)findViewById(R.id.idFabAgregarActivity);
        rq = Volley.newRequestQueue(getApplicationContext());
        elements = new ArrayList<>();
        Adapter = new AdaptadorActividadesbyCourse(elements,this);
        idCourse = getIntent().getStringExtra("id");
        consulta = new Activities_Consulta();
        RV.setHasFixedSize(true);
        RV.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false));



        View dialogView = getLayoutInflater().inflate(R.layout.dialog_add_activities, null);
        add_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                EditText dialogEditTextName = dialogView.findViewById(R.id.editTextName);
                EditText dialogEditTextLastName = dialogView.findViewById(R.id.editTextLastName);
                dialogEditTextPhone = dialogView.findViewById(R.id.editTextPhone);
                calendario = Calendar.getInstance();
                DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        calendario.set(Calendar.YEAR,year);
                        calendario.set(Calendar.MONTH,month);
                        calendario.set(Calendar.DAY_OF_MONTH,day);
                        updateLabel();
                    }};
                dialogEditTextPhone.setOnClickListener( view1 ->{

                    new DatePickerDialog(ListaActividades.this,dateSetListener,
                            calendario.get(Calendar.YEAR),
                            calendario.get(Calendar.MONTH),
                            calendario.get(Calendar.DAY_OF_MONTH)).show();
                });



                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(ListaActividades.this);
                dialogBuilder.setTitle("Ingrese los datos");
                dialogBuilder.setView(dialogView);
                dialogBuilder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if(!dialogEditTextName.getText().toString().isEmpty()&&
                                !dialogEditTextLastName.getText().toString().isEmpty()&&
                                !dialogEditTextPhone.getText().toString().isEmpty() ) {
                            String nombre = dialogEditTextName.getText().toString();
                            String titulo = dialogEditTextLastName.getText().toString();
                            String date = dialogEditTextPhone.getText().toString();
                            addActivity(nombre,titulo,date);
                        }else{
                            Toast.makeText(getApplicationContext(), "debes agregar un nombre a la clase", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                dialogBuilder.setNegativeButton("Cancelar", null);

                // Muestra el cuadro de diálogo
                AlertDialog dialog = dialogBuilder.create();
                dialog.show();
            }
        });



        init();
    }
    private void updateLabel(){
        String myformat = "yyyy/MM/dd";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myformat, Locale.US);
        dialogEditTextPhone.setText(dateFormat.format(calendario.getTime()));
    }
    void init(){
        consulta.setQueryActivityResultListener(this);
        consulta.find_Activity_query(idCourse,rq,ListaActividades.this);
    }

    public void deleteActivity(String idActivity,String idCourse,RequestQueue rqq, Context context){
            Activities_Consulta consulta2 = new Activities_Consulta(context);
            consulta2.setDeletetActivityResultListener(this);
            consulta2.delete_activity(idActivity,idCourse, rqq,  context);

    }
    void addActivity(String title,String description,String date){
        consulta.setInsertActivityResultListener(this);
        consulta.add_activity(title,description,date,idCourse,rq,ListaActividades.this);
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
        Toast.makeText(getApplicationContext(),"Se elimino la clase",Toast.LENGTH_SHORT).show();
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

    @Override
    public void opcionEliminar(Activities activities, Context context) {
        //deleteActivity(activities,context);
    }
}