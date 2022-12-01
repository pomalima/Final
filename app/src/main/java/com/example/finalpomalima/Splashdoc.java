package com.example.finalpomalima;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class Splashdoc extends AppCompatActivity {

    TextView docente;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashdoc);
        docente = findViewById(R.id.txtdocente);
        //PASAR LUEGO DE 3 SEGUNDOS
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                llamar_main(docente);
            }
        }, 3000);
    }


    public void llamar_main(View view){
        Intent i = new Intent(Splashdoc.this, Logindoc.class);
        startActivity(i);
    }
}