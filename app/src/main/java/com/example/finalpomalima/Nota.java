package com.example.finalpomalima;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import Entidades.Alumno;

public class Nota extends AppCompatActivity {

    TextView txtDNIEstudiante_Notas, txtNombreEstudiante_Notas;
    EditText edtNota_Nota, edtObservacion_Nota;
    Button btnAñadir_Nota, btnRegresar_Nota;

    private DatabaseReference mDatabase;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nota);

        //Para a la conexión
        FirebaseApp.initializeApp(this);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        txtDNIEstudiante_Notas = findViewById(R.id.txtDNIEstudiante_Notas);
        txtNombreEstudiante_Notas = findViewById(R.id.txtNombreEstudiante_Notas);
        edtNota_Nota = findViewById(R.id.edtNota_Nota);
        edtObservacion_Nota = findViewById(R.id.edtObservacion_Nota);
        btnAñadir_Nota = findViewById(R.id.btnAñadir_Nota);
        btnRegresar_Nota = findViewById(R.id.btnRegresar_Nota);

        SharedPreferences preferencias = getSharedPreferences("Global", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencias.edit();
        txtDNIEstudiante_Notas.setText(preferencias.getString("ALUM_dni", ""));
        txtNombreEstudiante_Notas.setText(preferencias.getString("ALUM_nombre", ""));

//        progressDialog = new ProgressDialog(Nota.this);
//        progressDialog.setMessage("Consultando Lista Estudiantes");
//        progressDialog.show();
//        progressDialog.hide();

    }
}