package com.example.examen1moviles_elias;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class Home extends AppCompatActivity {
    Button showEscuelas, showEstudiantes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        showEstudiantes = (Button) findViewById(R.id.selectestudiantes);

        showEstudiantes.setOnClickListener(view ->{
            startActivity(new Intent(this, ListEstudianteActivity.class));
            finish();
        });

    }
}