package com.example.admin4teacher;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;

import persistencia.User;
import persistencia.UserConsulta;

public class RegisterView extends AppCompatActivity implements UserConsulta.UserRegisterListener, UserConsulta.UserResultListener {

    private TextInputEditText txtName, txtLastname, txtEmail, txtPhone, txtUser, txtPassword;
    private Button btnRegister;
    UserConsulta usc = new UserConsulta();
    User user = new User();

    RequestQueue rq;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_view);
        txtName = (TextInputEditText) findViewById(R.id.txtNombre);
        txtLastname = (TextInputEditText) findViewById(R.id.txtApellido);
        txtEmail = (TextInputEditText) findViewById(R.id.txtEmail);
        txtPhone = (TextInputEditText) findViewById(R.id.txtTelefono);
        txtUser = (TextInputEditText) findViewById(R.id.txtUser);
        txtPassword=(TextInputEditText) findViewById(R.id.txtPassword);
        btnRegister = (Button) findViewById(R.id.btnSignUp);
        rq = Volley.newRequestQueue(getApplicationContext());
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               registrarU();
            }
        });
    }

    private void registrarU(){
        usc.setUserRegisterListener(this);
        usc.registrarUsuario(rq,txtName.getText().toString(),txtLastname.getText().toString(),
                txtEmail.getText().toString(),txtPhone.getText().toString(),txtUser.getText().toString(),txtPassword.getText().toString());
    }




    @Override
    public void onRegisterSuccess() {
        Toast.makeText(getApplicationContext(),"Usuario registrado, por favor inicie sesión", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
    }

    @Override
    public void onRegisterError(String errorMessage) {
        Toast.makeText(getApplicationContext(),"Error al registrar", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUserReceived(User user) {
        Toast.makeText(getApplicationContext(),"Si se pudo"+user.getUser(), Toast.LENGTH_SHORT).show();
    }
}