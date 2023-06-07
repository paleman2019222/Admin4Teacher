package com.example.admin4teacher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.admin4teacher.Interfaces.AuxiliarCourses;
import com.example.admin4teacher.Interfaces.AuxiliarStudents;
import com.example.admin4teacher.adapter.Adaptador_Classes;
import com.example.admin4teacher.adapter.Adaptador_Courses;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

import persistencia.Classes;
import persistencia.Course;
import persistencia.CoursesConsulta;
import persistencia.Students;
import persistencia.StudentsConsulta;
import persistencia.class_consulta;

//En esta actividad se muestra el listado de cursos de la BD
public class Estudiantes extends AppCompatActivity implements StudentsConsulta.QueryStudentsResultListener, StudentsConsulta.InsertStudentsResultListener, StudentsConsulta.DeleteStudentsResultListener, AuxiliarStudents {



    List<Students> elements;
    ListAdapter Adapter;
    RecyclerView recyclerView;
    TextView txt;
    RequestQueue rq;

    AppCompatActivity activity ;
    FloatingActionButton add_student;
    Context ctx;

    String idClass, idStudent;


    public Estudiantes(Context ctx) {this.ctx = ctx;}

    public Estudiantes() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estudiantes);
        //txt = (TextView)findViewById(R.id.textView6);
        elements = new ArrayList<>();
        ctx = this;
        rq = Volley.newRequestQueue(getApplicationContext());
        Adapter = new ListAdapter(elements,this);
        Bundle extras = getIntent().getExtras();
        idClass = extras.getString("id");

        recyclerView = findViewById(R.id.layout_RV_estudents);
        add_student = (FloatingActionButton)findViewById(R.id.idFabAgregarStudent);

        //cambio las dimeciones del recicler y el manajer para que sea un gril de 2 columnas
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));


        //este boton corresponde al boton flotante agregar
        //aun no he agregado el cuadro de dialogo, pero funciona
        add_student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LayoutInflater inflater = LayoutInflater.from(ctx);
                View dialogView = inflater.inflate(R.layout.layout_add_student, null);

                TextInputLayout boxName = dialogView.findViewById(R.id.txtDialogStudentName);
                TextInputLayout boxLast = dialogView.findViewById(R.id.txtDialogStudentLast);
                TextInputLayout boxCarnet = dialogView.findViewById(R.id.txtDialogStudentCarnet);
                TextInputLayout boxEmail = dialogView.findViewById(R.id.txtDialogStudentEmail);

                EditText dialogEditTextName = boxName.getEditText();
                EditText dialogEditTextLast = boxLast.getEditText();
                EditText dialogEditTextCarnet = boxCarnet.getEditText();
                EditText dialogEditTextEmail = boxEmail.getEditText();

                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(Estudiantes.this);
                dialogBuilder.setTitle("Ingrese los datos del Estudiante");
                dialogBuilder.setView(dialogView);
                dialogBuilder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(!dialogEditTextName.getText().toString().isEmpty()) {
                            String nombre = dialogEditTextName.getText().toString();
                            String apellido = dialogEditTextLast.getText().toString();
                            String carnet = dialogEditTextCarnet.getText().toString();
                            String email = dialogEditTextEmail.getText().toString();
                            add_student(carnet,nombre,apellido,email, idClass,rq,ctx);
                        }else{
                            Toast.makeText(getApplicationContext(), "debes agregar un nombre a el estudiante", Toast.LENGTH_SHORT).show();
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

    }



    @Override
    public void onQuerySuccess(List<Students> elements) {
        Adapter.setItems(elements);
        recyclerView.setAdapter(Adapter);
    }

    @Override
    public void onQueryError(String errorMessage) {
        Toast.makeText(getApplicationContext(),errorMessage,Toast.LENGTH_LONG).show();
    }




    public void init(){
        StudentsConsulta students = new StudentsConsulta();
        students.setQueryStudentsResultListener(this);
        students.query_student(idClass,rq);
    }
    //lo mimo para los otros metodos solo que esos edito diferentes listener para mostrar diferentes Toast

    public void add_student(String carnet, String name, String lastname, String email, String idclass,RequestQueue rq, Context ctx){
        StudentsConsulta consulta = new StudentsConsulta();
        consulta.setInsertStudentsResultListener(this);
        consulta.add_student(carnet,name,lastname,email,idclass,rq,ctx);
    }

    public void delete_student(String idStudent,String idClass,Context context){
        RequestQueue rqq = Volley.newRequestQueue(context);
        StudentsConsulta studenconsulta = new StudentsConsulta(context);
        studenconsulta.setDeleteStudentsResultListener(this);
        studenconsulta.delete_student(idStudent,idClass,rqq,context);
    }

    @Override
    public void onDeleteSucces(List<Students> elements, Context contexto) {
                Toast.makeText(this, "Eliminado correctamente", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onInsertSucces(List<Students> elements) {
        Adapter.setItems(elements);
        recyclerView.setAdapter(Adapter);
    }

    @Override
    public void opcionEliminar(Students student, Context context) {
        delete_student(student.getIdStudent(),student.getIdClass(),context);
    }


    //Metodo implementado de la Interfaz AuxiliarClasses el cual me permitira eliminar una clas

}