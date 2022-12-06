package com.example.finalpomalima;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import Entidades.Materia;

public class RegistroMateria extends AppCompatActivity {

    EditText edtRegMateria_RegMat, edtRegNrc_RegMat;
    Button btnCancelarReg_RegMat, btnRegistrarMat_RegMat;

    private DatabaseReference mDatabase;
    private FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_materia);

        //Para a la conexión
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        mDatabase = firebaseDatabase.getReference();
        mDatabase= FirebaseDatabase.getInstance().getReference();

        edtRegMateria_RegMat = findViewById(R.id.edtRegMateria_RegMat);
        edtRegNrc_RegMat = findViewById(R.id.edtRegNrc_RegMat);
        btnRegistrarMat_RegMat = findViewById(R.id.btnRegistrarMat_RegMat);
        btnCancelarReg_RegMat = findViewById(R.id.btnCancelarMat_RegMat);

        btnRegistrarMat_RegMat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registrar();
            }
        });

        btnCancelarReg_RegMat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(RegistroMateria.this, MainActivity.class);
                startActivity(i);
            }
        });
    }

    private void registrar() {
        Materia materia = new Materia();
        String regmateria = edtRegMateria_RegMat.getText().toString();
        String nrc = edtRegNrc_RegMat.getText().toString();
        String id= mDatabase.push().getKey();

        materia.setId(id);
        materia.setRegmateria(regmateria);
        materia.setNrc(nrc);
        mDatabase.child("Materia").child(id).setValue(materia);
        Toast.makeText(this, "Registro Exito", Toast.LENGTH_SHORT).show();

    }

}