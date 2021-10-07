package com.example.examen1moviles_elias;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    TextView lblCrearCuenta;
    EditText txtInputEmail, txtInputPassword;
    Button btnLogin;
    FirebaseAuth mAuth;
    private ProgressDialog mProgressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        setContentView(R.layout.activity_main);
        txtInputEmail = findViewById(R.id.input_email);
        txtInputPassword = findViewById(R.id.input_clave);
        btnLogin = findViewById(R.id.btn_login);
        lblCrearCuenta = findViewById(R.id.txtCrearCuenta);

        lblCrearCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,RegistroUser.class));
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verificarCredenciales();
            }
        });
        mProgressBar = new ProgressDialog(MainActivity.this);
        mAuth = FirebaseAuth.getInstance();
    }
    public void verificarCredenciales(){
        String email = txtInputEmail.getText().toString();
        String password = txtInputPassword.getText().toString();
        if(email.isEmpty() || !email.contains("@")){
            showError(txtInputEmail, "Email no valido");
        }else if(password.isEmpty()|| password.length()<7){
            showError(txtInputPassword, "Password invalida");
        }else{
            //Mostrar ProgressBar
            mProgressBar.setTitle("Login");
            mProgressBar.setMessage("Iniciando sesión, espere un momento..");
            mProgressBar.setCanceledOnTouchOutside(false);
            mProgressBar.show();
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        mProgressBar.dismiss();
                        Intent intent = new Intent(MainActivity.this, Home.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }else {
                        Toast.makeText(getApplicationContext(), "No se pudo iniciar sesion, verifique las credenciales", Toast.LENGTH_LONG).show();
                    }
                }
            });
            //Registrar usuario
            //Exitoso -> Mostrar toast
            //redireccionar - intent a login

            //ocultar progressBar
            mProgressBar.dismiss();
        }
    }
    private void showError(EditText input, String s){
        input.setError(s);
        input.requestFocus();
    }
}