package com.example.finalpomalima;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
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

import Adapter.AdaptadorListaMaterias;
import Entidades.Alumno;
import Entidades.Materia;

public class ListaMateria extends AppCompatActivity {
    Button btnVolver_ListMateria;
    RecyclerView RecyclerView_ListMateria;

    private DatabaseReference mDatabase;
    ArrayList<Materia> list_materias;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_materia);
        btnVolver_ListMateria = findViewById(R.id.btnVolver_ListMateria);
        RecyclerView_ListMateria = findViewById(R.id.RecyclerView_ListMateria);

        //Para a la conexión
        FirebaseApp.initializeApp(this);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        list_materias = new ArrayList<>();
        list_materias.clear();

        RecyclerView_ListMateria.setLayoutManager(new LinearLayoutManager(ListaMateria.this));
        RecyclerView_ListMateria.setHasFixedSize(true);

        SharedPreferences preferencias = getSharedPreferences("Global", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencias.edit();
        String cod_docente = preferencias.getString("CodigoDocente", "");

        progressDialog = new ProgressDialog(ListaMateria.this);
        progressDialog.setMessage("Consultando Lista Materias");
        progressDialog.show();
        final boolean[] val = {false};

        Query mDatosBusqueda = mDatabase.child(cod_docente);
        mDatosBusqueda.addValueEventListener(new ValueEventListener() {
         @Override
         public void onDataChange(DataSnapshot dataSnapshot) {

             if (dataSnapshot == null) {
                 val[0] = true;
                 progressDialog.hide();
                 Toast.makeText(ListaMateria.this, getText(R.string.sin_materias), Toast.LENGTH_SHORT).show();

             } else {

                 if (val[0]) ;
                 else {
                     Materia item_materia;
                     for(DataSnapshot data: dataSnapshot.getChildren()){
                         item_materia = new Materia();
                         item_materia.setNrc(data.child("nrc").getValue(String.class));
                         item_materia.setNombre_materia(data.child("nombre_materia").getValue(String.class));
                         list_materias.add(item_materia);
                     }
                     progressDialog.hide();

                     AdaptadorListaMaterias adapter = new AdaptadorListaMaterias(ListaMateria.this, list_materias,
                             (item, vista) -> {
                                 //CONTENIDO
                                    editor.putString("ME_nrc", item.getNrc());
                                    editor.putString("ME_nombre", item.getNombre_materia());
                                    editor.commit();
                                 Intent i = new Intent(ListaMateria.this, ListaEstudiante.class);
                                 startActivity(i);
                             });

                     RecyclerView_ListMateria.setAdapter(adapter);
                 }
             }
         }

         @Override
         public void onCancelled( DatabaseError error) {
             Toast.makeText(ListaMateria.this, error.toString(), Toast.LENGTH_SHORT).show();
         }
        });

        btnVolver_ListMateria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ListaMateria.this, MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });
    }
}