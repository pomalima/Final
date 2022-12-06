package com.example.finalpomalima;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import Adapter.AdaptadorListaAlumnos;
import Adapter.AdaptadorListaMaterias;
import Entidades.Alumno;
import Entidades.Materia;

public class ListaEstudiante extends AppCompatActivity {

    Button btnVolver_ListaEst;
    RecyclerView RecyclerView_ListaEst;

    private DatabaseReference mDatabase;
    ArrayList<Alumno> list_alumnos;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_estudiante);

        btnVolver_ListaEst = findViewById(R.id.btnVolver_ListaEst);
        RecyclerView_ListaEst = findViewById(R.id.RecyclerView_ListaEst);

        //Para a la conexión
        FirebaseApp.initializeApp(this);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        list_alumnos = new ArrayList<>();
        list_alumnos.clear();

        RecyclerView_ListaEst.setLayoutManager(new LinearLayoutManager(ListaEstudiante.this));
        RecyclerView_ListaEst.setHasFixedSize(true);

        SharedPreferences preferencias = getSharedPreferences("Global", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencias.edit();
        String cod_docente = preferencias.getString("CodigoDocente", "");
        String nrc = preferencias.getString("ME_nrc", "");

        progressDialog = new ProgressDialog(ListaEstudiante.this);
        progressDialog.setMessage("Consultando Lista Estudiantes");
        progressDialog.show();
        final boolean[] val = {false};
        Query mDatosBusqueda = mDatabase.child(cod_docente).child(nrc);
        mDatosBusqueda.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot == null) {
                    val[0] = true;
                    progressDialog.hide();
                    Toast.makeText(ListaEstudiante.this, getText(R.string.sin_materias), Toast.LENGTH_SHORT).show();

                } else {

                    if (val[0]) ;
                    else {
                        Alumno item_alumno;
                        for(DataSnapshot data: dataSnapshot.child("alumnos").getChildren()){
                            item_alumno = new Alumno();
                            item_alumno.setDni(data.child("dni").getValue(String.class));
                            item_alumno.setNombre(data.child("nombre").getValue(String.class));
                            list_alumnos.add(item_alumno);
                        }
                        progressDialog.hide();

                        AdaptadorListaAlumnos adapter = new AdaptadorListaAlumnos(ListaEstudiante.this, list_alumnos,
                                (item, vista) -> {
                                    //CONTENIDO
                                    editor.putString("ALUM_dni", item.getDni());
                                    editor.putString("ALUM_nombre", item.getNombre());
                                    editor.commit();
                                    Intent i = new Intent(ListaEstudiante.this, Nota.class);
                                    startActivity(i);
                                });

                        RecyclerView_ListaEst.setAdapter(adapter);
                    }
                }
            }

            @Override
            public void onCancelled( DatabaseError error) {
                Toast.makeText(ListaEstudiante.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}