package persistencia;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONObject;

public class StudentsConsulta  extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener{

    //private LoginResultListener loginResultListener;
    //private StudentsResultListener userResultListener;

    private UserConsulta.UserRegisterListener userRegisterListener;

    private UserConsulta.UserUpdateListener userUpdateListener;

    private UserConsulta.UserDeleteListener userDeleteListener;

    @Override
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onResponse(JSONObject response) {

    }
}
