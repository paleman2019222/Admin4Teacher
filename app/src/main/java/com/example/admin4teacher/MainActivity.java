package com.example.admin4teacher;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
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
import org.json.JSONException;
import org.json.JSONObject;

import persistencia.User;
import persistencia.UserConsulta;


public class MainActivity extends AppCompatActivity implements UserConsulta.LoginResultListener, UserConsulta.UserResultListener{
    RequestQueue rq;
    JsonRequest jrq;

    private TextInputEditText userTxt, passTxt;
    private Button btnLogin;
    TextView label;

    UserConsulta usc = new UserConsulta();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        userTxt = (TextInputEditText) findViewById(R.id.txtUser);
        passTxt=(TextInputEditText) findViewById(R.id.txtPassword);

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
    Context ctx = getApplicationContext();

        usc.setLoginResultListener(this);
        usc.setUserResultListener(this);
        usc.iniciarSesion(userTxt.getText().toString(), passTxt.getText().toString(), rq, ctx);
    }




    @Override
    public void onLoginSuccess(User user) {
        Intent i = new Intent(getApplicationContext(), Home.class);

        i.putExtra("nombre", user.getName());
        i.putExtra("apellido", user.getLastname());
        i.putExtra("usuario", user.getUser());
        i.putExtra("telefono", user.getPhone());
        i.putExtra("email", user.getEmail());
        i.putExtra("idUser", user.getIdUser());
        startActivity(i);
        finish();
    }

    @Override
    public void onLoginError(String errorMessage) {
        Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUserReceived(User user) {
        Toast.makeText(getApplicationContext(), "Bienvenido: " + user.getName(), Toast.LENGTH_SHORT).show();
    }


    public void toRegister(View view){
        Intent i = new Intent(getApplicationContext(), RegisterView.class);
        startActivity(i);

    }


}
