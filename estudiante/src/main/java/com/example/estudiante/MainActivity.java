package com.example.estudiante;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import Adapter.AdaptadorListaMaterias;
import Entidades.Materia;

public class MainActivity extends AppCompatActivity {

    RecyclerView RecyclerView_listMatEst;

    private DatabaseReference mDatabase;
    ArrayList<Materia> list_materias;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView_listMatEst = findViewById(R.id.RecyclerView_listMatEst);

        //Para a la conexión
        FirebaseApp.initializeApp(this);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        list_materias = new ArrayList<>();
        list_materias.clear();

        RecyclerView_listMatEst.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        RecyclerView_listMatEst.setHasFixedSize(true);

        SharedPreferences preferencias = getSharedPreferences("Global_Estudiante", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencias.edit();
        String dni = preferencias.getString("ALUM_dni", "");

        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Consultando Lista Materias");
        progressDialog.show();
        final boolean[] val = {false};

        Query mDatosBusqueda = mDatabase.child(dni);
        mDatosBusqueda.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot == null) {
                    val[0] = true;
                    progressDialog.hide();
                    Toast.makeText(MainActivity.this, getText(R.string.sin_materias), Toast.LENGTH_SHORT).show();

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

                        AdaptadorListaMaterias adapter = new AdaptadorListaMaterias(MainActivity.this, list_materias,
                                (item, vista) -> {
                                    //CONTENIDO
                                    editor.putString("ME_nrc", item.getNrc());
                                    editor.putString("ME_nombre", item.getNombre_materia());
                                    editor.commit();
                                    Intent i = new Intent(MainActivity.this, ListNotaMotivo.class);
                                    startActivity(i);
                                });

                        RecyclerView_listMatEst.setAdapter(adapter);
                    }
                }
            }

            @Override
            public void onCancelled( DatabaseError error) {
                Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });

//        btnVolver_ListMateria.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(ListaMateria.this, MainActivity.class);
//                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(i);
//            }
//        });
    }
}