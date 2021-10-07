package com.example.examen1moviles_elias;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.examen1moviles_elias.adapter.ListEstudianteAdapter;
import com.example.examen1moviles_elias.model.Estudiante;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ListEstudianteActivity extends AppCompatActivity implements ListEstudianteAdapter.OnCardListener{
    private List<Estudiante> estudianteList;
    private RecyclerView recyclerViewEstudiantes;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private Estudiante estudianteSelected;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_estudiante);
        recyclerViewEstudiantes = findViewById(R.id.recycler_view_est);
        initFirebase();
        listarDatos();
    }
    private void listarDatos() {
        estudianteList = new ArrayList<>();
        databaseReference.child("Estudiante").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                estudianteList.clear();
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    Estudiante estudiante =  dataSnapshot.getValue(Estudiante.class);
                    estudianteList.add(estudiante);
                }
                ListEstudianteAdapter listEstAdapter = new ListEstudianteAdapter(estudianteList, ListEstudianteActivity.this, ListEstudianteActivity.this);
                RecyclerView recyclerView = findViewById(R.id.recycler_view_est);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(ListEstudianteActivity.this));
                recyclerView.setAdapter(listEstAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void initFirebase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_list_estudiante, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.item_add_est) {
            finish();
            startActivity(new Intent(this, AddEstudianteActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed(){
        finish();
        startActivity(new Intent(this, Home.class));
    }

    @Override
    public void onCardClick(int position) {
        estudianteSelected = estudianteList.get(position);
        final CharSequence[] items = {
                "Editar",
                "Eliminar"
        };
        AlertDialog.Builder menu = new AlertDialog.Builder(this);
        menu.setTitle("Opciones");
        menu.setItems(items, (dialogInterface, i) -> {
            switch (i){
                case 0:
                    Intent intent = new Intent(ListEstudianteActivity.this, EditEstudianteActivity.class);
                    intent.putExtra("ESTUDIANTE_ID", estudianteSelected.getUid());
                    startActivity(intent);
                    break;
                case 1:
                            AlertDialog.Builder alert = new AlertDialog.Builder(ListEstudianteActivity.this);
                    alert.setMessage("¿Desea eliminar este registro?");
                    alert.setPositiveButton("Si", (dialog, a)->{
                        Estudiante d = new Estudiante();
                        d.setUid(estudianteSelected.getUid());
                        databaseReference.child("Estudiante").child(d.getUid()).removeValue();
                        Toast.makeText(this, "Registro Eliminado", Toast.LENGTH_SHORT).show();
                    });
                    alert.setNegativeButton("No", (dialog, a)-> dialog.dismiss());
                    alert.show();
                    break;
            }
        });
        menu.show();
    }
}