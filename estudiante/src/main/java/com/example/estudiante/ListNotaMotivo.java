package com.example.estudiante;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

import Adapter.AdaptadorListaNotas;
import Entidades.Nota;

public class ListNotaMotivo extends AppCompatActivity {

    RecyclerView RecyclerView_listNotMotivoEst;
    Button btnVolver_ListNotaMotivo;

    private DatabaseReference mDatabase;
    ArrayList<Nota> list_notas;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_nota_motivo);

        RecyclerView_listNotMotivoEst = findViewById(R.id.RecyclerView_listNotMotivoEst);
        btnVolver_ListNotaMotivo = findViewById(R.id.btnVolver_ListNotaMotivo);

        //Para a la conexión
        FirebaseApp.initializeApp(this);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        list_notas = new ArrayList<>();
        list_notas.clear();

        RecyclerView_listNotMotivoEst.setLayoutManager(new LinearLayoutManager(ListNotaMotivo.this));
        RecyclerView_listNotMotivoEst.setHasFixedSize(true);

        SharedPreferences preferencias = getSharedPreferences("Global_Estudiante", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencias.edit();
        String dni = preferencias.getString("ALUM_dni", "");
        String nrc = preferencias.getString("ME_nrc", "");

        progressDialog = new ProgressDialog(ListNotaMotivo.this);
        progressDialog.setMessage("Consultando Notas");
        progressDialog.show();
        final boolean[] val = {false};

        Query mDatosBusqueda = mDatabase.child(dni).child("cursos").child(nrc).child("notas");
        mDatosBusqueda.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot == null) {
                    val[0] = true;
                    progressDialog.hide();
                    Toast.makeText(ListNotaMotivo.this, getText(R.string.sin_materias), Toast.LENGTH_SHORT).show();

                } else {

                    if (val[0]) ;
                    else {
                        Nota item_nota;
                        for(DataSnapshot data: dataSnapshot.getChildren()){
                            item_nota = new Nota();
                            item_nota.setNota(data.child("nota").getValue(String.class));
                            item_nota.setObservacion(data.child("observacion").getValue(String.class));
                            list_notas.add(item_nota);
                        }
                        progressDialog.hide();

                        AdaptadorListaNotas adapter = new AdaptadorListaNotas(ListNotaMotivo.this, list_notas);

                        RecyclerView_listNotMotivoEst.setAdapter(adapter);
                    }
                }
            }

            @Override
            public void onCancelled( DatabaseError error) {
                Toast.makeText(ListNotaMotivo.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        btnVolver_ListNotaMotivo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ListNotaMotivo.this, MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });

    }
}