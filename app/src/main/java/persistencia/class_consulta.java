package persistencia;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

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

//Se Implementa EL Response Listener para poder tener los metodos que se ejecutaran luego de haber echo la "Consulta"
//esto lo explicara el PABLO mejor supongo
public class class_consulta extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener  {

    //Se Crean 3 objetos de 3 Intefaces que se crearan abajo
    //idea robada de pablo
    private QueryResultListener queryResultListener;
    private DeleteResultListener deleteResultListener;
    private InsertResultListener insertResultListener;

    Context contexto;///te las comes sin...
    List<Classes> elements;

    //constructor
    public class_consulta() {
    }

    //constructor con contexto

    public class_consulta(Context contexto) {
        this.contexto = contexto;
    }


    //se crean las interfaces
    //para lo que yo las uso es para poder mandar elementos de esta actividad al fragmento
    //y asi poder actualizar el recyclerView
    //entonces se van a ir usando dependiendo si son nulos o no
    //por eso en el fragmento se edita la interfaz asi podemos acceder sus metodos
    public interface QueryResultListener {
        void onQuerySuccess(List<Classes>  elements);
        void onQueryError(String errorMessage);
    }
    public interface DeleteResultListener {
        void onDeleteSucces(List<Classes>  elements,Context contexto);
    }
    public interface InsertResultListener {
        void onInsertSucces(List<Classes>  elements);
    }

    //metos para poder edtar los Listener
    //nicamente son nombres mamones del Pablo
    public void setInsertResultListener(InsertResultListener insertResultListener) {
        this.insertResultListener = insertResultListener;
    }

    public void setqueryResultListener(QueryResultListener queryResultListener) {
        this.queryResultListener = queryResultListener;
    }
    public void setDeleteResultListener(DeleteResultListener deleteResultListener) {
        this.deleteResultListener = deleteResultListener;
    }


    //Metodo que se ejecuta si hubo un error de del PHP
    @Override
    public void onErrorResponse(VolleyError error) {
        String errorMessage = "Error al conectar en la base de datos";
        error.printStackTrace();
        Log.i("ERROR conectar", error.toString());
        if (queryResultListener != null) {
            queryResultListener.onQueryError(errorMessage);
        }

    }
    //metodo que se ejecuta si no hubo error
    @Override
    public void onResponse(JSONObject response) {
        //Creamos los objetos que necesitaremos
        Classes clase = null;
        JSONArray jsonArray = response.optJSONArray("classes");
        JSONObject jsonObject= null;
        elements = new ArrayList<>();
        //aca creo un if para asegurarme que el JSON no es nulo
        if(!jsonArray.isNull(0)) {
            try {
                //creo un ciclo for para asignar todos los arrays del JSOn a un solo JSon
                // dependiendo del iterador
                //para luego asignar los valores del JSON a la clase antes creada
                for (int i = 0; i < jsonArray.length(); i++) {
                    clase = new Classes();
                    jsonObject = jsonArray.getJSONObject(i);
                    clase.setClass_name(jsonObject.optString("className"));
                    clase.setId_class(jsonObject.optString("idClass"));

                    //luego de asignar los datos, asigno la clase a la lista de clases
                    elements.add(clase);

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


    // es exactamente igual al anterior pero no se xq me daaba error entonces tube que crear otro listener
    //de ahi nocambia nada mas que este llama a otra interfaz y manda los elementos
    //Listener para eliminar
    Response.Listener<JSONObject> deleteListener = new Response.Listener<JSONObject>() {
        @Override
        public void onResponse(JSONObject response) {
            Classes clase = null;
            JSONArray jsonArray = response.optJSONArray("classes");
            JSONObject jsonObject= null;
            elements = new ArrayList<>();
            if(!jsonArray.isNull(0)) {
                try {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        clase = new Classes();
                        jsonObject = jsonArray.getJSONObject(i);
                        clase.setClass_name(jsonObject.optString("className"));
                        clase.setId_class(jsonObject.optString("idClass"));

                        elements.add(clase);

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
            Classes clase = null;
            JSONArray jsonArray = response.optJSONArray("classes");
            JSONObject jsonObject= null;
            elements = new ArrayList<>();
            if(!jsonArray.isNull(0)) {
                try {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        clase = new Classes();
                        jsonObject = jsonArray.getJSONObject(i);
                        clase.setClass_name(jsonObject.optString("className"));
                        clase.setId_class(jsonObject.optString("idClass"));

                        elements.add(clase);
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
    public void query_class(String idUser, RequestQueue rq, Context ctx){
        try {
            String ip = "http://4teacher.atspace.tv";
            String url = ip + "/query_classes2.php?idUser="+idUser;
            //http://4teacher.atspace.tv/query_classes2.php?idUser=1
            JsonRequest<JSONObject> jrq;
            jrq = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
            rq.add(jrq);

        } catch (Exception error) {Log.i("error", error.toString());}

    }
    public void add_class(String className,String idUser, RequestQueue rq,Context context) {
        try {
            String ip = "http://4teacher.atspace.tv";
            String url = ip + "/insert_class.php?name="+className+"&teacher="+idUser;
            JsonRequest<JSONObject> jrq;
            jrq = new JsonObjectRequest(Request.Method.GET, url, null, insertListener, this);
            rq.add(jrq);

        } catch (Exception error) {Log.i("error", error.toString());}
    }

    public void delete_class(String idclass,String idUser,RequestQueue rq, Context context) {
        try {
            String ip = "http://4teacher.atspace.tv";
            String url = ip + "/delete_class.php? idClass="+idclass+"& teacher="+idUser;
            JsonRequest<JSONObject> jrq;
            jrq = new JsonObjectRequest(Request.Method.GET, url, null, deleteListener, this);
            rq.add(jrq);

        } catch (Exception error) {Log.i("error", error.toString());}
    }
}
