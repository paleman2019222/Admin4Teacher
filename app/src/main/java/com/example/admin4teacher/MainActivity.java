package com.example.admin4teacher;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener {
    RequestQueue rq;
    JsonRequest jrq;

    private TextInputEditText userTxt, passTxt;
    private Button btnLogin;
    TextView label;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        userTxt = (TextInputEditText) findViewById(R.id.txtUser);
        passTxt=(TextInputEditText) findViewById(R.id.txtPassword);
        label=(TextView) findViewById(R.id.textView123);
        btnLogin=(Button)  findViewById(R.id.btnLogin);
        rq = Volley.newRequestQueue(getApplicationContext());

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iniciarSesion();
            }
        });
    }



    private void iniciarSesion() {

        try {
            String ip = "http://4teacher.atspace.tv";


            String url = ip + "/sesion.php?user=" + userTxt.getText().toString() +
                    "&pwd=" + passTxt.getText().toString();
            jrq = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
            rq.add(jrq);

            //VolleySingleton.getInstanciaVolley(getApplicationContext()).addToRequestQueue(jrq);
        } catch (Exception error) {
            label.setText(error.toString());
        }

    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getApplicationContext(), "No se encontró el error " + error.toString(), Toast.LENGTH_SHORT).show();
        error.printStackTrace();
        Log.i("ERROR", error.toString());
        label.setText(error.toString());
    }

    @Override
    public void onResponse(JSONObject response) {
        Toast.makeText(getApplicationContext(), "Se ha encontrado el usuario " + userTxt.getText().toString(), Toast.LENGTH_SHORT).show();

        JSONArray jsonArray = response.optJSONArray("datos");
        JSONObject jsonObject= null;
    }
}
