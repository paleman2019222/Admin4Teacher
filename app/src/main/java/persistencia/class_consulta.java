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

public class class_consulta extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener  {

    private QueryResultListener queryResultListener;

    private DeleteResultListener deleteResultListener;
    private InsertResultListener insertResultListener;

    Context contexto;
    List<Classes> elements;

    public class_consulta() {
    }

    public class_consulta(Context contexto) {
        this.contexto = contexto;
    }



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

    public void setInsertResultListener(InsertResultListener insertResultListener) {
        this.insertResultListener = insertResultListener;
    }

    public void setqueryResultListener(QueryResultListener queryResultListener) {
        this.queryResultListener = queryResultListener;
    }
    public void setDeleteResultListener(DeleteResultListener deleteResultListener) {
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
                if (queryResultListener != null) {
                    queryResultListener.onQuerySuccess(elements);
                }


            } catch (JSONException e) {
                Log.i("error", e.toString());
            }
        }

    }

    //Listener para eliminar
    Response.Listener<JSONObject> deleteListener = new Response.Listener<JSONObject>() {
        @Override
        public void onResponse(JSONObject response) {
            Classes clase = null;
            JSONArray jsonArray = response.optJSONArray("classes");
            JSONObject jsonObject= null;
            elements = new ArrayList<>();
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
    };
    //listener para registrar
    Response.Listener<JSONObject> insertListener = new Response.Listener<JSONObject>() {
        @Override
        public void onResponse(JSONObject response) {
            Classes clase = null;
            JSONArray jsonArray = response.optJSONArray("classes");
            JSONObject jsonObject= null;
            elements = new ArrayList<>();
            try {
                for (int i = 0; i < jsonArray.length(); i++) {
                    clase = new Classes();
                    jsonObject = jsonArray.getJSONObject(i);
                    clase.setClass_name(jsonObject.optString("className"));
                    clase.setId_class(jsonObject.optString("idClass"));

                    elements.add(clase);
                }
                if (insertResultListener != null ) {
                    insertResultListener.onInsertSucces(elements);
                }

            } catch (JSONException e) {Log.i("error", e.toString());}
        }
    };
    //listener para eliminar



    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
    public void query_class(RequestQueue rq, Context ctx){
        try {
            String ip = "http://4teacher.atspace.tv";
            String url = ip + "/query_classes.php";
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

    public void delete_class(String idclass,RequestQueue rq, Context context) {
        try {
            String ip = "http://4teacher.atspace.tv";
            String url = ip + "/delete_class.php? idClass="+idclass;
            JsonRequest<JSONObject> jrq;
            jrq = new JsonObjectRequest(Request.Method.GET, url, null, deleteListener, this);
            rq.add(jrq);

        } catch (Exception error) {Log.i("error", error.toString());}
    }
}
