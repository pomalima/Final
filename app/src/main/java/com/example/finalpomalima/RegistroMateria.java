package com.example.finalpomalima;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RegistroMateria extends AppCompatActivity {

    Button btnCancelarReg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_materia);

        btnCancelarReg = findViewById(R.id.btnCancelarMat);
        btnCancelarReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(RegistroMateria.this, MainActivity.class);
                startActivity(i);
            }
        });
    }
}