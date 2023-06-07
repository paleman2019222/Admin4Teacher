package persistencia;

import android.content.Context;
import android.util.Log;

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

public class Activities_Consulta implements Response.Listener<JSONObject>, Response.ErrorListener {
    Context contexto;
    List<Activities> elements;

    InsertActivityResultListener insertActivityResultListener;
    DeletetActivityResultListener deletetActivityResultListener;
    QueryActivityResultListener queryActivityResultListener;

    public Activities_Consulta(Context contexto) {this.contexto = contexto;}
    public Activities_Consulta() {}

    public void setInsertActivityResultListener(InsertActivityResultListener insertActivityResultListener) {
        this.insertActivityResultListener = insertActivityResultListener;
    }

    public void setDeletetActivityResultListener(DeletetActivityResultListener deletetActivityResultListener) {
        this.deletetActivityResultListener = deletetActivityResultListener;
    }

    public void setQueryActivityResultListener(QueryActivityResultListener queryActivityResultListener) {
        this.queryActivityResultListener = queryActivityResultListener;
    }

    public void query_activity( String idUser,RequestQueue rq, Context ctx){
        try {
            String ip = "http://4teacher.atspace.tv";
            String url = ip + "/query_activity.php?idUser="+idUser;
            //String url = "http://4teacher.atspace.tv/query_activity.php";
            JsonRequest<JSONObject> jrq;
            jrq = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
            rq.add(jrq);

        } catch (Exception error) {
            Log.i("error", error.toString());}

    }
    public void add_activity(String idCourse, RequestQueue rq, Context ctx){
        try {
            String ip = "http://4teacher.atspace.tv";
            String url = ip + "/insert_activity.php?idCourse="+idCourse;
            //String url = "http://4teacher.atspace.tv/query_activity.php?idCourse=1";
            JsonRequest<JSONObject> jrq;
            jrq = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
            rq.add(jrq);
        } catch (Exception error) {
            Log.i("error", error.toString());}

    }
    public void delete_activity(String idActivity, RequestQueue rq, Context ctx){
        try {
            String ip = "http://4teacher.atspace.tv";
            String url = ip + "/delete_activity.php?idCourse="+idActivity;
            //String url = "http://4teacher.atspace.tv/query_activity.php?idCourse=1";
            JsonRequest<JSONObject> jrq;
            jrq = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
            rq.add(jrq);
        } catch (Exception error) {
            Log.i("error", error.toString());}
    }
    public void find_Activity_query(String idCourse, RequestQueue rq, Context ctx){
        try {
            String ip = "http://4teacher.atspace.tv";
            String url = ip + "/query_activity.php?idCourse="+idCourse;
            //String url = "http://4teacher.atspace.tv/query_activity.php?idCourse=1";
            JsonRequest<JSONObject> jrq;
            jrq = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
            rq.add(jrq);

        } catch (Exception error) {
            Log.i("error", error.toString());}

    }
    public interface InsertActivityResultListener {
        void onInsertActivitySucces(List<Activities>  elements);
        void onInsertActivityError(String errorMenssage);
    }
    public interface DeletetActivityResultListener {
        void onDeleteActivitySucces(List<Activities>  elements);
        void onDeleteActivityError(String errorMenssage);
    }
    public interface QueryActivityResultListener {
        void onQueryActivitySucces(List<Activities>  elements);
        void onQueryActivityError(String errorMenssage);
    }


    @Override
    public void onErrorResponse(VolleyError error) {
        String errorMessage = "Error al conectar con la base de datos";
        // Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_SHORT).show();
        error.printStackTrace();
        Log.i("ERROR", error.toString());
        String JSONnulo = "com.android.volley.ParseError: org.json.JSONException: Value [] of type org.json.JSONArray cannot be converted to JSONObject";
        if(error.toString().equals(JSONnulo)){
            errorMessage = "No hay Actividades aun";
        }

        if (insertActivityResultListener != null ) {
            insertActivityResultListener.onInsertActivityError("No se pudo agregar la actividad");
        }
        if(deletetActivityResultListener!=null){
            deletetActivityResultListener.onDeleteActivityError("No se puedo eliminar la Actividad");

        }
        if(queryActivityResultListener!=null){
            queryActivityResultListener.onQueryActivityError(errorMessage);
        }

    }

    @Override
    public void onResponse(JSONObject response) {
        JSONArray jsonArray = response.optJSONArray("activities");
        JSONObject jsonObject= null;
        Activities actividad;
        elements = new ArrayList<>();
        if(jsonArray!=null){
            try {
                for (int i = 0; i < jsonArray.length(); i++) {
                    actividad = new Activities();
                    jsonObject = jsonArray.getJSONObject(i);
                    actividad.setIdActivity(jsonObject.optString("idActivity"));
                    actividad.setIdCourse(jsonObject.optString("idCourse"));
                    actividad.setTitle(jsonObject.optString("title"));
                    actividad.setDescription(jsonObject.optString("description"));
                    actividad.setFinishDate(jsonObject.optString("finishDate"));
                    actividad.setCourseName(jsonObject.optString("courseName"));
                    elements.add(actividad);
                }


            } catch (JSONException e){
                throw new RuntimeException(e);
            }
            if (insertActivityResultListener != null ) {
                insertActivityResultListener.onInsertActivitySucces(elements);
            }
            if(deletetActivityResultListener!=null){
                deletetActivityResultListener.onDeleteActivitySucces(elements);

            }
            if(queryActivityResultListener!=null){
                queryActivityResultListener.onQueryActivitySucces(elements);
            }
        }
    }
}
