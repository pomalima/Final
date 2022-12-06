package com.example.estudiante;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class Loginest extends AppCompatActivity {

    EditText edtEmailEst, edtPassEst;
    Button btnEstudiante;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginest);

        edtEmailEst = findViewById(R.id.edtEmailEst);
        edtPassEst = findViewById(R.id.edtPassEst);
        btnEstudiante = findViewById(R.id.btnEstudiante);

    }
}