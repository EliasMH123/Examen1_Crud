package com.example.examen1moviles_elias;

import androidx.annotation.NonNull;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.UUID;

public class EditEstudianteActivity extends AppCompatActivity {
    private String uidEstudiante;
    EditText nombres, codigo, dni, direccion, correo, carrera;
    ImageButton updBtn;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    String emailPattern2 = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+\\.+[a-z]+";
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_estudiante);
        uidEstudiante = getIntent().getStringExtra("ESTUDIANTE_ID");
        nombres = findViewById(R.id.txt_edit_nombres);
        codigo = findViewById(R.id.txt_edit_codigo);
        correo = findViewById(R.id.txt_edit_correo);
        dni = findViewById(R.id.txt_edit_dni);
        direccion = findViewById(R.id.txt_edit_direccion);
        carrera = findViewById(R.id.txt_edit_carrera);

        updBtn = findViewById(R.id.btn_edit_est);
        initFirebase();
        overwriteValues();

        updBtn.setOnClickListener(view -> {
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
                d.setUid(uidEstudiante);
                d.setNombre_apellido(vNombres);
                d.setCodigo(vCodigo);
                d.setCorreo(vCorreo);
                d.setDni(vDni);
                d.setDireccion(vDireccion);
                d.setCarrera_profes(vCarrera);
                databaseReference.child("Estudiante").child(d.getUid()).setValue(d);
                Toast.makeText(
                        this,
                        "Registro actualizado!",
                        Toast.LENGTH_SHORT
                ).show();
                limpiarCajas();
                finish();
                startActivity(new Intent(this, ListEstudianteActivity.class));
            }
        });
    }

    private void overwriteValues() {
        databaseReference.child("Estudiante").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    Estudiante estudiante = dataSnapshot.getValue(Estudiante.class);
                    if(estudiante.getUid().equals(uidEstudiante)){
                        nombres.setText(estudiante.getNombre_apellido());
                        codigo.setText(estudiante.getCodigo());
                        correo.setText(estudiante.getCorreo());
                        dni.setText(estudiante.getDni());
                        direccion.setText(estudiante.getDireccion());
                        carrera.setText(estudiante.getCarrera_profes());
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });
    }

    private void initFirebase(){
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance() ;
        databaseReference = firebaseDatabase.getReference();
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

}