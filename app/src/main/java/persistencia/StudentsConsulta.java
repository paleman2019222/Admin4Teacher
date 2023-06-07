package persistencia;

import android.content.Context;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.example.admin4teacher.ListElement;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class StudentsConsulta  extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener{

    private QueryStudentsResultListener queryStudentsResultListener;
    private DeleteStudentsResultListener deleteStudentsResultListener;
    private InsertStudentsResultListener insertStudentsResultListener;

    Context contexto;///te las comes sin...
    List<Students> elements;

    public StudentsConsulta(){
        //Constructor vacio
    }

    public StudentsConsulta(Context contexto) {
        this.contexto = contexto;
    }

    public interface QueryStudentsResultListener {
        void onQuerySuccess(List<Students> elements);
        void onQueryError(String errorMessage);
    }
    public interface DeleteStudentsResultListener {
        void onDeleteSucces(List<Students>  elements, Context contexto);
    }
    public interface InsertStudentsResultListener {
        void onInsertSucces(List<Students>  elements);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        String errorMessage = "Error al conectar en la base de datos";
        error.printStackTrace();
        String JSONvacio = "com.android.volley.ParseError: org.json.JSONException: Value [] of type org.json.JSONArray cannot be converted to JSONObject";
        if(error.toString().equals(JSONvacio)){
            errorMessage = "No hay estudiantes por mostrar";
        }
        Log.i("ERROR conectar", error.toString());
        if (queryStudentsResultListener != null) {
            queryStudentsResultListener.onQueryError(errorMessage);
        }

    }

    @Override
    public void onResponse(JSONObject response) {
        //Creamos los objetos que necesitaremos
        Students student = null;
        JSONArray jsonArray = response.optJSONArray("students");
        JSONObject jsonObject= null;
        elements = new ArrayList<>();
        //aca creo un if para asegurarme que el JSON no es nulo
        if(!jsonArray.isNull(0)) {
            try {
                //creo un ciclo for para asignar todos los arrays del JSOn a un solo JSon
                // dependiendo del iterador
                //para luego asignar los valores del JSON a la clase antes creada
                for (int i = 0; i < jsonArray.length(); i++) {
                    student = new Students();
                    jsonObject = jsonArray.getJSONObject(i);
                    student.setIdStudent(jsonObject.optString("idStudent"));
                    student.setIdClass(jsonObject.optString("idClass"));
                    student.setCarnet(jsonObject.optString("carnetStudent"));
                    student.setName(jsonObject.optString("studentName"));
                    student.setLastname(jsonObject.optString("studentLastname"));
                    student.setEmail(jsonObject.optString("email"));
                    //luego de asignar los datos, asigno la clase a la lista de clases
                    elements.add(student);

                }
                //si el listener no es nulo  ejecuto el metodo de la interfaz
                // mandandole la lista llena de classes
                if (queryStudentsResultListener != null) {
                    queryStudentsResultListener.onQuerySuccess(elements);
                }


            } catch (JSONException e) {
                Log.i("error", e.toString());
            }

        }

    }

    public void setQueryStudentsResultListener(QueryStudentsResultListener queryStudentsResultListener) {
        this.queryStudentsResultListener = queryStudentsResultListener;
    }

    public void setDeleteStudentsResultListener(DeleteStudentsResultListener deleteStudentsResultListener) {
        this.deleteStudentsResultListener = deleteStudentsResultListener;
    }

    public void setInsertStudentsResultListener(InsertStudentsResultListener insertStudentsResultListener) {
        this.insertStudentsResultListener = insertStudentsResultListener;
    }

    Response.Listener<JSONObject> DeleteStudentListener = new Response.Listener<JSONObject>() {
        @Override
        public void onResponse(JSONObject response) {
            Students student = null;
            JSONArray jsonArray = response.optJSONArray("students");
            JSONObject jsonObject= null;
            elements = new ArrayList<>();
            if(!jsonArray.isNull(0)) {
                try {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        student = new Students();
                        jsonObject = jsonArray.getJSONObject(i);
                        student.setIdStudent(jsonObject.optString("idStudent"));
                        student.setIdClass(jsonObject.optString("idClass"));
                        student.setCarnet(jsonObject.optString("carnetStudent"));
                        student.setName(jsonObject.optString("studentName"));
                        student.setLastname(jsonObject.optString("studentLastname"));
                        student.setEmail(jsonObject.optString("email"));
                        elements.add(student);

                    }
                    if (deleteStudentsResultListener != null) {
                        deleteStudentsResultListener.onDeleteSucces(elements, contexto);
                    }

                } catch (JSONException e) {Log.i("error delete", e.toString());}
            }
        }
    };

    Response.Listener<JSONObject> insertListener = new Response.Listener<JSONObject>() {
        @Override
        public void onResponse(JSONObject response) {
            Students student = null;
            JSONArray jsonArray = response.optJSONArray("students");
            JSONObject jsonObject= null;
            elements = new ArrayList<>();
            if(!jsonArray.isNull(0)) {
                try {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        student = new Students();
                        jsonObject = jsonArray.getJSONObject(i);
                        student.setIdStudent(jsonObject.optString("idStudent"));
                        student.setIdClass(jsonObject.optString("idClass"));
                        student.setCarnet(jsonObject.optString("carnetStudent"));
                        student.setName(jsonObject.optString("studentName"));
                        student.setLastname(jsonObject.optString("studentLastname"));
                        student.setEmail(jsonObject.optString("email"));
                        elements.add(student);

                    }
                    if (insertStudentsResultListener != null) {
                        insertStudentsResultListener.onInsertSucces(elements);
                    }

                } catch (JSONException e) {
                    Log.i("error", e.toString());
                }
            }
        }
    };

    public void query_student(String idclass, RequestQueue rq){
        try {
            String ip = "http://4teacher.atspace.tv";
            String url = ip + "/query_students.php?idClass="+idclass;
            //String url = "http://4teacher.atspace.tv/query_classes2.php?idUser=46";
            JsonRequest<JSONObject> jrq;
            jrq = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
            rq.add(jrq);

        } catch (Exception error) {Log.i("error", error.toString());}

    }

    public void add_student(String carnet,String name, String lastname, String email, String idclass, RequestQueue rq,Context context) {
        try {
            String ip = "http://4teacher.atspace.tv";
            String url = ip + "/insert_students.php?carnet="+carnet+"&name="+name+"&lastname="+lastname+"&email="+email+"&idclass="+idclass;
            JsonRequest<JSONObject> jrq;
            jrq = new JsonObjectRequest(Request.Method.GET, url, null, insertListener, this);
            rq.add(jrq);

        } catch (Exception error) {Log.i("error", error.toString());}
    }

    public void delete_student(String idstudent,String idclass,RequestQueue rq, Context context) {
        try {
            String ip = "http://4teacher.atspace.tv";
            String url = ip + "/delete_students.php?idstudent="+idstudent+"&idclass="+idclass;//"& teacher="+idUser;
            JsonRequest<JSONObject> jrq;
            jrq = new JsonObjectRequest(Request.Method.GET, url, null, DeleteStudentListener, this);
            rq.add(jrq);

        } catch (Exception error) {Log.i("error", error.toString());}
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }

}
