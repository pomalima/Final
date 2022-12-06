package com.example.finalpomalima;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ListaMateria extends AppCompatActivity {

    Button btnVolver_ListMateria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_materia);

        btnVolver_ListMateria = findViewById(R.id.btnVolver_ListMateria);
        btnVolver_ListMateria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ListaMateria.this, MainActivity.class);
                startActivity(i);
            }
        });
    }
}