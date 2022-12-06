package com.example.finalpomalima;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ListaMateria extends AppCompatActivity {

    Button btnVolverListMateria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_materia);

        btnVolverListMateria = findViewById(R.id.btnVolverListMateria);
        btnVolverListMateria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ListaMateria.this, MainActivity.class);
                startActivity(i);
            }
        });
    }
}