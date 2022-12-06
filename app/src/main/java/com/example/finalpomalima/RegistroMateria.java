package com.example.finalpomalima;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseError;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import Entidades.Materia;

public class RegistroMateria extends AppCompatActivity {

    EditText edtRegMateria_RegMat, edtRegNrc_RegMat;
    Button btnCancelarReg_RegMat, btnRegistrarMat_RegMat;

    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_materia);

        //Para a la conexión
        FirebaseApp.initializeApp(this);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        edtRegMateria_RegMat = findViewById(R.id.edtRegMateria_RegMat);
        edtRegNrc_RegMat = findViewById(R.id.edtRegNrc_RegMat);
        btnRegistrarMat_RegMat = findViewById(R.id.btnRegistrarMat_RegMat);
        btnCancelarReg_RegMat = findViewById(R.id.btnCancelarMat_RegMat);

        btnRegistrarMat_RegMat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edtRegMateria_RegMat.getText().toString().equals("") || edtRegNrc_RegMat.getText().toString().equals("")){
                    Toast.makeText(RegistroMateria.this, getText(R.string.rellene_los_campos), Toast.LENGTH_SHORT).show();
                }
                else{
                    registrar();
                }
            }
        });

        btnCancelarReg_RegMat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(RegistroMateria.this, MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });
    }

    private void registrar() {

        final boolean[] val = {false};
        SharedPreferences preferencias = getSharedPreferences("Global", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencias.edit();
        String correo = preferencias.getString("CodigoDocente", "");
        Materia materia = new Materia();
        String nom_materia = edtRegMateria_RegMat.getText().toString();
        String nrc = edtRegNrc_RegMat.getText().toString();
//        String id= mDatabase.push().getKey();

        materia.setNombre_materia(nom_materia);
        materia.setNrc(nrc);


        Query mDatosBusqueda = mDatabase.child(correo).child(nrc);
        mDatosBusqueda.addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(DataSnapshot dataSnapshot){

                String validacion_exist = dataSnapshot.child("nombre_materia").getValue(String.class);
                if(validacion_exist == null){
                    val[0] = true;
                    editor.putString("nrc", nrc);
                    editor.putString("nombre_materia", materia.getNombre_materia());
                    editor.commit();

                    Intent i = new Intent(RegistroMateria.this, IngresarLista.class);
                    startActivity(i);

                }
                else{
                    if(val[0]);
                    else{
                        Toast.makeText(RegistroMateria.this, getText(R.string.existe_nrc), Toast.LENGTH_SHORT).show();
                    }
                }
//                for(DataSnapshot data: dataSnapshot.getChildren()){
//                    String apellidomaterno = data.child("apellidomaterno").getValue(String.class);
//                    String apellidopaterno = data.child("apellidopaterno").getValue(String.class);
//                    String nombre = data.child("nombre").getValue(String.class);
//                    String telefono = data.child("telefono").getValue(String.class);
//                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(RegistroMateria.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }

}