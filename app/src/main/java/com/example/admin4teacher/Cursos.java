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
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.admin4teacher.Interfaces.AuxiliarCourses;
import com.example.admin4teacher.adapter.Adaptador_Classes;
import com.example.admin4teacher.adapter.Adaptador_Courses;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

import persistencia.Classes;
import persistencia.Course;
import persistencia.CoursesConsulta;
import persistencia.class_consulta;

//En esta actividad se muestra el listado de cursos de la BD
public class Cursos extends AppCompatActivity implements CoursesConsulta.DeleteResultListener, CoursesConsulta.InsertResultListener, CoursesConsulta.QueryResultListener, AuxiliarCourses {



    List<Course> elements;
    Adaptador_Courses Adapter;
    RecyclerView recyclerView;

    RequestQueue rq;

    AppCompatActivity activity ;
    FloatingActionButton add_course;
    Context ctx;
    View root;
    String idClass, nameCourse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cursos);

        elements = new ArrayList<>();
       // activity = (AppCompatActivity) getActivity();
        rq = Volley.newRequestQueue(ctx);
        Adapter = new Adaptador_Courses(elements,activity);
        Bundle extras = getIntent().getExtras();
        idClass = extras.getString("id");
        nameCourse = extras.getString("name");

        recyclerView = root.findViewById(R.id.layout_RV_classes);
        add_course = (FloatingActionButton)root.findViewById(R.id.idFabAgregarCurso);

        //cambio las dimeciones del recicler y el manajer para que sea un gril de 2 columnas
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));


        //este boton corresponde al boton flotante agregar
        //aun no he agregado el cuadro de dialogo, pero funciona
        add_course.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
                View dialogView = inflater.inflate(R.layout.layout_add_course, null);

                TextInputLayout box = dialogView.findViewById(R.id.txtDialogCourseName);
                EditText dialogEditTextName =  box.findViewById(R.id.editCourseName);
                EditText dialogEditTextDescription = box.findViewById(R.id.editdescription);



                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getApplicationContext());
                dialogBuilder.setTitle("Ingrese los datos de curso");
                dialogBuilder.setView(dialogView);
                dialogBuilder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(!dialogEditTextName.getText().toString().isEmpty()) {
                            String nombre = dialogEditTextName.getText().toString();
                            String description = dialogEditTextDescription.getText().toString();
                            add_course(nombre, description, idClass);
                        }else{
                            Toast.makeText(getApplicationContext(), "debes agregar un nombre a el curso", Toast.LENGTH_SHORT).show();
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
    public void opcionEliminar(@NonNull final Course curso, Context context) {
        delete_course(curso.getIdCourse(),curso.getIdClass(),context);
    }

    @Override
    public void onQuerySuccess(List<Course> elements) {

    }

    @Override
    public void onQueryError(String errorMessage) {

    }

    @Override
    public void onDeleteSucces(List<Course> elements, Context contexto) {

    }

    @Override
    public void onInsertSucces(List<Course> elements) {

    }


    public void init(){
        CoursesConsulta courseconsulta = new CoursesConsulta();
        courseconsulta.setqueryResultListener(this);
        courseconsulta.query_class(idClass,rq,activity);
    }
    //lo mimo para los otros metodos solo que esos edito diferentes listener para mostrar diferentes Toast

    public void add_course(String courseName, String description, String idClass){
        CoursesConsulta courseconsulta = new CoursesConsulta();
       courseconsulta.setInsertResultListener(this);
        courseconsulta.add_course(courseName,description,idClass,rq,activity);
    }

    public void delete_course(String idCourse,String idClass,Context context){
        RequestQueue rqq = Volley.newRequestQueue(context);
       CoursesConsulta courseconsulta = new CoursesConsulta(context);
        courseconsulta.setDeleteResultListener(this);
        courseconsulta.delete_course(idCourse,idClass,rqq,context);
    }


    //Metodo implementado de la Interfaz AuxiliarClasses el cual me permitira eliminar una clas

}