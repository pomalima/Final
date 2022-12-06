package com.example.finalpomalima;

import androidx.annotation.NonNull;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import Entidades.Materia;

public class IngresarLista extends AppCompatActivity {

    Button btnSubirLista_IngList, btnGuardarSuLista_IngList, btnCancelarSuLista_IngList;
    TextView archivo_IngList;

    private DatabaseReference mDatabase;
    public  static final int READ_REQUEST_CODE = 42;
    public  static final int PERMISSION_REQUEST_STORAGE = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingresar_lista);
        SharedPreferences preferencias = getSharedPreferences("Global", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencias.edit();

        //Request Permission
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
        != PackageManager.PERMISSION_GRANTED){
         requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_STORAGE);
        }


        btnSubirLista_IngList = findViewById(R.id.btnSubirLista_IngList);
        archivo_IngList = findViewById(R.id.archivo_IngList);
        btnGuardarSuLista_IngList = findViewById(R.id.btnGuardarSuLista_IngList);
        btnCancelarSuLista_IngList = findViewById(R.id.btnCancelarSuLista_IngList);

        //Para a la conexión
        FirebaseApp.initializeApp(this);
        mDatabase = FirebaseDatabase.getInstance().getReference();


        btnSubirLista_IngList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                seleccionar_excel();
            }
        });

        btnGuardarSuLista_IngList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String correo = preferencias.getString("CodigoDocente", "");
                String nrc = preferencias.getString("nrc", "");

                Materia materia = new Materia();
                materia.setNombre_materia(preferencias.getString("nombre_materia", ""));
                materia.setNrc(nrc);

                mDatabase.child(correo).child(nrc).setValue(materia);
                Toast.makeText(IngresarLista.this, getText(R.string.registro_exitoso), Toast.LENGTH_SHORT).show();

                Intent i = new Intent(IngresarLista.this, MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            }
        });

        btnCancelarSuLista_IngList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(IngresarLista.this, RegistroMateria.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });
    }

    public void leer(Uri uri) {

        FileInputStream inputStream = null;

        String datos = "";

        try {
            inputStream = new FileInputStream(this.getContentResolver().openFileDescriptor(uri, "r").getFileDescriptor());

            POIFSFileSystem fileSystem = new POIFSFileSystem(inputStream);

            HSSFWorkbook workbook = new HSSFWorkbook(fileSystem);

            HSSFSheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.rowIterator();

            while (rowIterator.hasNext()) {
                HSSFRow row = (HSSFRow) rowIterator.next();
                Iterator<Cell> cellIterator = row.cellIterator();
                while (cellIterator.hasNext()) {
                    HSSFCell cell = (HSSFCell) cellIterator.next();

                    datos = datos+" - "+cell.toString();

                }
                datos = datos+"\n";
            }

            archivo_IngList.setText(datos);

        } catch (Exception e) {
                e.printStackTrace();
        }
    }

    public void seleccionar_excel(){
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("*/*");
//        String[] mimetypes = {"application/vnd.ms-excel", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"};
        String[] mimetypes = {"application/vnd.ms-excel"};
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimetypes);
        startActivityForResult(intent, READ_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == READ_REQUEST_CODE && resultCode == RESULT_OK) {
            if(data!=null){
                Uri uri = data.getData();
                leer(uri);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
            Toast.makeText(IngresarLista.this, getText(R.string.permiso_accedido), Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(IngresarLista.this, getText(R.string.permiso_no_accedido), Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}