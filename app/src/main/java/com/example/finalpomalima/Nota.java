package com.example.finalpomalima;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import Entidades.Alumno;
import Entidades.Materia;

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

        String correo = preferencias.getString("CodigoDocente", "");
        String nrc = preferencias.getString("ME_nrc", "");
        String dni = preferencias.getString("ALUM_dni", "");

        Materia item_materia = new Materia();
        item_materia.setNrc(nrc);
        item_materia.setNombre_materia(preferencias.getString("ME_nombre", ""));

        Alumno item_alumno = new Alumno();
        item_alumno.setDni(preferencias.getString("ALUM_dni", ""));
        item_alumno.setNombre(preferencias.getString("ALUM_nombre", ""));

        existencia_estudiante(dni,item_alumno);
        existencia_materia(dni, nrc, item_materia);

        btnAñadir_Nota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edtNota_Nota.getText().equals("")||edtObservacion_Nota.getText().equals("")){
                    Toast.makeText(Nota.this, getText(R.string.rellene_los_campos), Toast.LENGTH_SHORT).show();
                }
                else{
                    if( 0 <= Double.valueOf(edtNota_Nota.getText().toString())  && Double.valueOf(edtNota_Nota.getText().toString()) <= 20){
                        progressDialog = new ProgressDialog(Nota.this);
                        progressDialog.setMessage("Registrando Nota");
                        progressDialog.show();

                        Entidades.Nota item_nota = new Entidades.Nota();
                        item_nota.setNota(edtNota_Nota.getText().toString());
                        item_nota.setObservacion(edtObservacion_Nota.getText().toString());

                        mDatabase.child(correo).child(nrc).child("alumnos").child(dni).child("notas").child(item_nota.getObservacion()).setValue(item_nota);

                        mDatabase.child(dni).child("cursos").child(nrc).child("notas").child(item_nota.getObservacion()).setValue(item_nota);

                        progressDialog.hide();
                        Toast.makeText(Nota.this, getText(R.string.nota_registrada), Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(Nota.this, ListaEstudiante.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                    }else{
                        Toast.makeText(Nota.this, getText(R.string.rango_nota), Toast.LENGTH_SHORT).show();
                    }

                }

            }
        });
        btnRegresar_Nota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Nota.this, ListaEstudiante.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });

    }

    public void existencia_estudiante(String dni, Alumno item_alumno){

        Query mDatosBusqueda = mDatabase.child(dni);
        mDatosBusqueda.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String validacion_exist = dataSnapshot.child("dni").getValue(String.class);
                if (validacion_exist == null) {
                    mDatabase.child(dni).setValue(item_alumno);
                }

            }
            @Override
            public void onCancelled( DatabaseError error) {
                Toast.makeText(Nota.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void existencia_materia(String dni, String nrc, Materia item_materia){

        Query mDatosBusqueda = mDatabase.child(dni).child("cursos").child(nrc);
        mDatosBusqueda.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String validacion_exist = dataSnapshot.child("nrc").getValue(String.class);
                if (validacion_exist == null) {
                    mDatabase.child(dni).child("cursos").child(nrc).setValue(item_materia);
                }

            }
            @Override
            public void onCancelled( DatabaseError error) {
                Toast.makeText(Nota.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}