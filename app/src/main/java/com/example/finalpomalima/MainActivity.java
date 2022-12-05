package com.example.finalpomalima;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    Button btnCerrar, btnRegMateria, btnListarMateria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnRegMateria = findViewById(R.id.btnRegMateria);
        btnListarMateria = findViewById(R.id.btnListarMateria);

        btnCerrar = findViewById(R.id.btnCerrar);
        btnCerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(MainActivity.this,"Sesion Cerrada!", Toast.LENGTH_SHORT).show();
                gologing();
            }
        });

        btnRegMateria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, RegistroMateria.class);
                startActivity(i);
            }
        });

        btnListarMateria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, ListaMateria.class);
                startActivity(i);
            }
        });


    }

    private void gologing() {
        Intent i = new Intent(this,Logindoc.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
    }
}