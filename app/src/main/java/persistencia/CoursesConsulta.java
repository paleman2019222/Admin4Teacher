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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CoursesConsulta extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener {

    private CoursesConsulta.QueryResultListener queryResultListener;
    private CoursesConsulta.DeleteResultListener deleteResultListener;
    private CoursesConsulta.InsertResultListener insertResultListener;


    Context contexto;///te las comes sin...
    List<Course> elements;

    //constructor
    public CoursesConsulta() {
    }

    //constructor con contexto

    public CoursesConsulta(Context contexto) {
        this.contexto = contexto;
    }


    public interface QueryResultListener {
        void onQuerySuccess(List<Course> elements);
        void onQueryError(String errorMessage);
    }
    public interface DeleteResultListener {
        void onDeleteSucces(List<Course>  elements, Context contexto);
    }
    public interface InsertResultListener {
        void onInsertSucces(List<Course>  elements);
    }

    public void setInsertResultListener(CoursesConsulta.InsertResultListener insertResultListener) {
        this.insertResultListener = insertResultListener;
    }

    public void setqueryResultListener(CoursesConsulta.QueryResultListener queryResultListener) {
        this.queryResultListener = queryResultListener;
    }
    public void setDeleteResultListener(CoursesConsulta.DeleteResultListener deleteResultListener) {
        this.deleteResultListener = deleteResultListener;
    }


    @Override
    public void onErrorResponse(VolleyError error) {
        String errorMessage = "Error al conectar en la base de datos";
        error.printStackTrace();
        Log.i("ERROR conectar", error.toString());
        if (queryResultListener != null) {
            queryResultListener.onQueryError(errorMessage);
        }
    }

    @Override
    public void onResponse(JSONObject response) {
        //Creamos los objetos que necesitaremos
        Course course = null;
        JSONArray jsonArray = response.optJSONArray("courses");
        JSONObject jsonObject= null;
        elements = new ArrayList<>();
        //aca creo un if para asegurarme que el JSON no es nulo
        if(!jsonArray.isNull(0)) {
            try {
                //creo un ciclo for para asignar todos los arrays del JSOn a un solo JSon
                // dependiendo del iterador
                //para luego asignar los valores del JSON a la clase antes creada
                for (int i = 0; i < jsonArray.length(); i++) {
                    course = new Course();
                    jsonObject = jsonArray.getJSONObject(i);
                    course.setCourseName(jsonObject.optString("courseName"));
                    course.setIdCourse(jsonObject.optString("idClass"));

                    //luego de asignar los datos, asigno la clase a la lista de clases
                    elements.add(course);

                }
                //si el listener no es nulo  ejecuto el metodo de la interfaz
                // mandandole la lista llena de classes
                if (queryResultListener != null) {
                    queryResultListener.onQuerySuccess(elements);
                }


            } catch (JSONException e) {
                Log.i("error", e.toString());
            }

        }
    }


    Response.Listener<JSONObject> deleteListener = new Response.Listener<JSONObject>() {
        @Override
        public void onResponse(JSONObject response) {
            Course course = null;
            JSONArray jsonArray = response.optJSONArray("courses");
            JSONObject jsonObject= null;
            elements = new ArrayList<>();
            if(!jsonArray.isNull(0)) {
                try {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        course = new Course();
                        jsonObject = jsonArray.getJSONObject(i);
                        course.setCourseName(jsonObject.optString("courseName"));
                        course.setDescription(jsonObject.optString("description"));
                        course.setIdClass(jsonObject.optString("idClass"));

                        elements.add(course);

                    }
                    if (deleteResultListener != null) {
                        deleteResultListener.onDeleteSucces(elements, contexto);
                    }

                } catch (JSONException e) {Log.i("error delete", e.toString());}
            }
        }
    };
    //listener para registrar
    // es exactamente igual al anterior pero no se xq me daaba error entonces tube que crear otro listener
    //de ahi nocambia nada mas que este llama a otra interfaz y manda los elementos x2
    Response.Listener<JSONObject> insertListener = new Response.Listener<JSONObject>() {
        @Override
        public void onResponse(JSONObject response) {
            Course course = null;
            JSONArray jsonArray = response.optJSONArray("classes");
            JSONObject jsonObject= null;
            elements = new ArrayList<>();
            if(!jsonArray.isNull(0)) {
                try {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        course = new Course();
                        jsonObject = jsonArray.getJSONObject(i);
                        course.setCourseName(jsonObject.optString("courseName"));
                        course.setDescription(jsonObject.optString("description"));
                        course.setIdClass(jsonObject.optString("idClass"));

                        elements.add(course);
                    }
                    if (insertResultListener != null) {
                        insertResultListener.onInsertSucces(elements);
                    }

                } catch (JSONException e) {
                    Log.i("error", e.toString());
                }
            }
        }
    };
    //listener para eliminar


    //ya aca solo son los metodos para hacer la conexion al servidor
    //nada del otro mundo lo unico que cambia entre estos son los atributos que pide
    //y los archivos que se piden del servidor
    //unicamente eso joves
    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
    public void query_class(String idClass, RequestQueue rq, Context ctx){
        try {
            String ip = "http://4teacher.atspace.tv";
            String url = ip + "/query_courses.php?idClass="+idClass;
            //http://4teacher.atspace.tv/query_classes2.php?idUser=1
            JsonRequest<JSONObject> jrq;
            jrq = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
            rq.add(jrq);

        } catch (Exception error) {Log.i("error", error.toString());}

    }
    public void add_course(String courseName,String description, String idClass, RequestQueue rq,Context context) {
        try {
            String ip = "http://4teacher.atspace.tv";
            String url = ip + "/insert_course.php?nameClass="+courseName+"&description="+description+"&idClass="+idClass;
            JsonRequest<JSONObject> jrq;
            jrq = new JsonObjectRequest(Request.Method.GET, url, null, insertListener, this);
            rq.add(jrq);

        } catch (Exception error) {Log.i("error", error.toString());}
    }

    public void delete_course(String idCourse,String idClass,RequestQueue rq, Context context) {
        try {
            String ip = "http://4teacher.atspace.tv";
            String url = ip + "/deleteCourse.php?idCourse="+idCourse+"&idClass="+idClass    ;
            JsonRequest<JSONObject> jrq;
            jrq = new JsonObjectRequest(Request.Method.GET, url, null, deleteListener, this);
            rq.add(jrq);

        } catch (Exception error) {Log.i("error", error.toString());}
    }
}
