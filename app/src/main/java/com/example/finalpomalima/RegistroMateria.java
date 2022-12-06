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

    EditText edtRegMateria, edtRegNrc;
    Button btnCancelarReg, btnRegistrarMat;

    //private FirebaseFirestore mfirestore;

    private DatabaseReference mDatabase;
    private FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_materia);

       


        //mfirestore = FirebaseFirestore.getInstance();
        inciarFirebase();
        mDatabase= FirebaseDatabase.getInstance().getReference();
        edtRegMateria= findViewById(R.id.edtRegMateria);
        edtRegNrc = findViewById(R.id.edtRegNrc);
        btnRegistrarMat = findViewById(R.id.btnRegMateria);

        btnRegistrarMat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { registrar(); }
        });
        btnCancelarReg = findViewById(R.id.btnCancelarMat);
        btnCancelarReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(RegistroMateria.this, MainActivity.class);
                startActivity(i);
            }
        });
    }

    private void registrar() {
        String regmateria = edtRegMateria.getText().toString();
        String rnc = edtRegNrc.getText().toString();
        String id= mDatabase.push().getKey();
        Materia materia = new Materia(id,regmateria,rnc);

        materia.setId(id);
        mDatabase.child("Materia").child(id).setValue(materia);
        Toast.makeText(this, "Registro Exito", Toast.LENGTH_SHORT).show();
    }

    //Para a la conexión
    private void inciarFirebase() {
        FirebaseApp.initializeApp(this);
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        firebaseDatabase= FirebaseDatabase.getInstance();
        mDatabase = firebaseDatabase.getReference();

    }

    // private void materiaregistrada(String materia, String nrc) {

    //mfirestore.collection("cursos").add(map)
   // }
}