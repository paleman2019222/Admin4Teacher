package persistencia;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.admin4teacher.Home;
import com.example.admin4teacher.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class UserConsulta extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener {
    //TextView label = (TextView) findViewById(R.id.textError);
    private LoginResultListener loginResultListener;
    private UserResultListener userResultListener;

    private UserRegisterListener userRegisterListener;

    private UserUpdateListener userUpdateListener;

    private UserDeleteListener userDeleteListener;



    public void setUserDeleteListener(UserDeleteListener userDeleteListener) {
        this.userDeleteListener = userDeleteListener;
    }

    public void setUserUpdateListener(UserUpdateListener userUpdateListener) {
        this.userUpdateListener = userUpdateListener;
    }

    public void setUserRegisterListener(UserRegisterListener userRegisterListener) {
        this.userRegisterListener = userRegisterListener;
    }

    public void setUserResultListener(UserResultListener userResultListener) {
        this.userResultListener = userResultListener;
    }



    public interface UserResultListener {
        void onUserReceived(User user);
    }

    public interface UserRegisterListener{
        void onRegisterSuccess();
        void onRegisterError(String errorMessage);
    }

    public interface UserUpdateListener{
        void onUpdateSuccess();
        void onUpdateError(String errorMessage);
    }

    public interface UserDeleteListener{
        void onDeleteSucces();
        void onDeleteError(String errorMessage);
    }

    public void setLoginResultListener(LoginResultListener loginResultListener) {
        this.loginResultListener = loginResultListener;
    }

    public interface LoginResultListener {
        void onLoginSuccess(User user);
        void onLoginError(String errorMessage);

    }
    @Override
    public void onErrorResponse(VolleyError error) {
        String errorMessage = "Verifique los datos e intentelo nuevamente";
       // Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_SHORT).show();
        error.printStackTrace();
        Log.i("ERROR", error.toString());
        if (loginResultListener != null) {
            loginResultListener.onLoginError(errorMessage);

        }
        if(userRegisterListener!=null){
            userRegisterListener.onRegisterError("No se ha podido registrar");
        }
        if(userUpdateListener!=null){
            userUpdateListener.onUpdateError("No se ha podido actualizar verifique los datos");
        }
        if(userDeleteListener!=null){
            userDeleteListener.onDeleteError("No se ha podido eliminar la cuenta");
        }

       // label.setText(error.toString());
    }

    @Override
    public void onResponse(JSONObject response) {

        JSONArray jsonArray = response.optJSONArray("datos");
        JSONObject jsonObject= null;
        persistencia.User usuario = new User();
        if(jsonArray!=null){



            try {
                jsonObject = jsonArray.getJSONObject(0);
                usuario.setUser(jsonObject.optString("user"));
                usuario.setName(jsonObject.optString("name"));
                usuario.setLastname(jsonObject.optString("lastname"));
                usuario.setPassword(jsonObject.optString("password"));
                usuario.setEmail(jsonObject.optString("email"));
                usuario.setPhone(jsonObject.optString("phone"));
                usuario.setIdUser(jsonObject.optString("idUser"));

            } catch (JSONException e){
                throw new RuntimeException(e);
            }
        }
        if (loginResultListener != null & userResultListener!=null) {
            loginResultListener.onLoginSuccess(usuario);
            userResultListener.onUserReceived(usuario);
        }
        if(userRegisterListener!=null){
            userRegisterListener.onRegisterSuccess();

        }
        if(userUpdateListener!=null){
            userUpdateListener.onUpdateSuccess();
        }
        if(userDeleteListener!=null){
            userDeleteListener.onDeleteSucces();
        }

    }


    public void iniciarSesion(String user, String password, RequestQueue rq, Context ctx) {

        try {
            String ip = "http://4teacher.atspace.tv";


            String url = ip + "/sesion.php?user=" + user +
                    "&pwd=" + password;
            JsonRequest jrq;
            jrq = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
            rq.add(jrq);
            //VolleySingleton.getInstanciaVolley(getApplicationContext()).addToRequestQueue(jrq);
        } catch (Exception error) {
            // label.setText(error.toString());
        }

}

    public void registrarUsuario(RequestQueue rq, String name, String lastname, String email, String phone, String user, String password) {

        String ip = "http://4teacher.atspace.tv";
        String url = ip + "/saveUser.php?name="+name+"&lastname="+lastname+"&email="+email+"&phone="+phone+"&user="+user+"&password="+password;

        try {
            JsonRequest jrq;
            jrq = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
            rq.add(jrq);
        }catch (Exception error){

        }
    }

    public void editarUsuario(RequestQueue rq, String name, String lastname, String email, String phone, String user, String iduser){
        String ip = "http://4teacher.atspace.tv";
        String url = ip+"/updateUser.php?name="+name+"&lastname="+lastname+"&phone="+phone+"&email="+email+"&user="+user+"&iduser="+iduser;

        try {
            JsonRequest jrq;
            jrq = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
            rq.add(jrq);
        }catch (Exception error){

        }

    }

    public void eliminarUsuario(RequestQueue rq, String idUser){
        String ip = "http://4teacher.atspace.tv";
        String url = ip+"/deleteUser.php?iduser="+idUser;

        try {
            JsonRequest jrq;
            jrq = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
            rq.add(jrq);
        }catch (Exception error){

        }

    }



    public void webServiceEliminar(Context context, RequestQueue rq, String idUser) {


        String ip="http://4teacher.atspace.tv";

        String url=ip+"/deleteUser.php?idUser="+idUser;

        StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {


                if (response.trim().equalsIgnoreCase("elimina")){

                    Toast.makeText(context,"Se ha Eliminado con exito",Toast.LENGTH_SHORT).show();
                }else{
                    if (response.trim().equalsIgnoreCase("noExiste")){

                        Log.i("RESPUESTA: ",""+response);
                    }else{

                        Log.i("RESPUESTA: ",""+response);
                    }

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"No se ha podido conectar",Toast.LENGTH_SHORT).show();
            }
        });


        rq.add(stringRequest);
        //VolleySingleton.getIntanciaVolley(getContext()).addToRequestQueue(stringRequest);
    }



}
