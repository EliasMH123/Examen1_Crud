package com.example.examen1moviles_elias;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.examen1moviles_elias.model.Estudiante;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

public class AddEstudianteActivity extends AppCompatActivity {
    EditText nombres, codigo, dni, direccion, correo, carrera;
    ImageButton addBtn;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    String emailPattern2 = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+\\.+[a-z]+";
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_estudiante);
        nombres = findViewById(R.id.txt_add_nombres);
        codigo = findViewById(R.id.txt_add_codigo);
        correo = findViewById(R.id.txt_add_correo);
        dni = findViewById(R.id.txt_add_dni);
        direccion = findViewById(R.id.txt_add_direccion);
        carrera = findViewById(R.id.txt_add_carrera);

        addBtn = findViewById(R.id.btn_add_est);
        initFirebase();

        addBtn.setOnClickListener(view->{
            String vNombres = nombres.getText().toString().trim();
            String vCodigo = codigo.getText().toString().trim();
            String vCorreo = correo.getText().toString().trim();
            String vDni = dni.getText().toString().trim();
            String vDireccion = direccion.getText().toString().trim();
            String vCarrera = carrera.getText().toString().trim();

            if(vNombres == null || vNombres.equals("")){
                nombres.setError("Required");
            }else if(vCodigo == null || vCodigo.equals("")){
                codigo.setError("Required");
            }else if(vCorreo == null || vCorreo.equals("")){
                correo.setError("Required");
            }else if(!vCorreo.matches(emailPattern) && !vCorreo.matches(emailPattern2)){
                correo.setError("Invalid email");
            }else if(vDni == null || vDni.equals("")){
                dni.setError("Required");
            }else if(vDireccion == null || vDireccion.equals("")){
                direccion.setError("Required");
            }else if(vCarrera == null && vCarrera.equals("")){
                carrera.setError("Required");
            }else{
                Estudiante d = new Estudiante();
                d.setUid(UUID.randomUUID().toString());
                d.setNombre_apellido(vNombres);
                d.setCodigo(vCodigo);
                d.setCorreo(vCorreo);
                d.setDni(vDni);
                d.setDireccion(vDireccion);
                d.setCarrera_profes(vCarrera);
                databaseReference.child("Estudiante").child(d.getUid()).setValue(d);
                Toast.makeText(
                        this,
                        "Estudiante registrado!",
                        Toast.LENGTH_SHORT
                ).show();
                limpiarCajas();
            }
        });
    }

    private void initFirebase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance() ;
        databaseReference = firebaseDatabase.getReference();
    }
    @Override
    public void onBackPressed(){
        finish();
        startActivity(new Intent(this, ListEstudianteActivity.class));
    }
    private void limpiarCajas(){
        nombres.setText("");
        codigo.setText("");
        correo.setText("");
        dni.setText("");
        direccion.setText("");
        carrera.setText("");
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_estudiante, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.lista_estudiantes) {
            finish();
            startActivity(new Intent(this, ListEstudianteActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

}