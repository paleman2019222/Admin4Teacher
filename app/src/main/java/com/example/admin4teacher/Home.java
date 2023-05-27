package com.example.admin4teacher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Home extends AppCompatActivity {

    first_fragment primer_frag = new first_fragment();
    second_fragment segundo_frag = new second_fragment();
    third_fragment tercer_frag = new third_fragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        BottomNavigationView navegation = findViewById(R.id.bottom_navegation);
       /* navegation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.first_fragment) {
                    loadFragment(primer_frag);
                    return true;
                }
                if(item.getItemId() == R.id.second_fragment) {
                    loadFragment(segundo_frag);
                    return true;
                }
                if(item.getItemId() == R.id.third_fragment) {
                    loadFragment(tercer_frag);
                    return true;
                }

                return false;
            }
        });*/

        loadFragment(primer_frag);
    }

    public void loadFragment(Fragment fragmetn){
        FragmentTransaction transicion = getSupportFragmentManager().beginTransaction();
        transicion.replace(R.id.Contenedor_frames,fragmetn);
        transicion.commit();
    }
}