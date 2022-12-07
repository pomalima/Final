package com.example.estudiante;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Loginest extends AppCompatActivity {

    EditText edtcodigoEst, edtPassEst;
    Button btnEstudiante;

    private DatabaseReference mDatabase;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginest);

        edtcodigoEst = findViewById(R.id.edtcodigoEst);
        edtPassEst = findViewById(R.id.edtPassEst);
        btnEstudiante = findViewById(R.id.btnEstudiante);

        SharedPreferences preferencias = getSharedPreferences("Global_Estudiante", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencias.edit();
        editor.clear().commit();

        //Para a la conexión
        FirebaseApp.initializeApp(this);
        mDatabase = FirebaseDatabase.getInstance().getReference();



        btnEstudiante.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(edtcodigoEst.getText().toString().equals("") || edtPassEst.getText().toString().equals("")){
                    Toast.makeText(Loginest.this, getText(R.string.rellene_los_campos), Toast.LENGTH_SHORT).show();
                }
                else {
                    if(edtcodigoEst.getText().toString().equals(edtPassEst.getText().toString())){
                        ConsultaEstudiante(editor);
                    }
                    else{
                        Toast.makeText(Loginest.this, getText(R.string.invalid_campos), Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

    }

    private void ConsultaEstudiante(SharedPreferences.Editor editor) {

        progressDialog = new ProgressDialog(Loginest.this);
        progressDialog.setMessage("Consultando Estudiante");
        progressDialog.show();
        final boolean[] val = {false};

        Query mDatosBusqueda = mDatabase.child(edtcodigoEst.getText().toString());
        mDatosBusqueda.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String validacion_exist = dataSnapshot.child("dni").getValue(String.class);
                if (validacion_exist == null) {
                    val[0] = true;
                    progressDialog.hide();
                    Toast.makeText(Loginest.this, getText(R.string.sin_matricula_materias), Toast.LENGTH_SHORT).show();

                } else {

                    if (val[0]) ;
                    else {

                        editor.putString("ALUM_dni", edtcodigoEst.getText().toString());
                        editor.commit();
                        Intent i = new Intent(Loginest.this, MainActivity.class);
                        startActivity(i);
                        progressDialog.hide();

                    }
                }
            }

            @Override
            public void onCancelled( DatabaseError error) {
                Toast.makeText(Loginest.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}