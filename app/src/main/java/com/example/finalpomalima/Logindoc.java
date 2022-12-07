package com.example.finalpomalima;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;

//import org.jetbrains.annotations.NotNull;

public class Logindoc extends AppCompatActivity {

    Button btnDocLogin;
    EditText edtEmailDoc, edtPassDoc;

    AwesomeValidation awesomeValidation;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logindoc);

        edtEmailDoc= findViewById(R.id.edtEmailDoc);
        edtPassDoc = findViewById(R.id.edtPassDoc);
        btnDocLogin = findViewById(R.id.btnDocLogin);

        firebaseAuth = FirebaseAuth.getInstance();

        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        FirebaseUser user = mAuth.getCurrentUser();

        if(user != null){
            irPrincipal();
        }

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        awesomeValidation.addValidation(this,R.id.edtEmailDoc, Patterns.EMAIL_ADDRESS,R.string.invalid_email);
        awesomeValidation.addValidation(this,R.id.edtPassDoc,".{6,}",R.string.invalid_pass);

        btnDocLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(awesomeValidation.validate()){

                    String email = edtEmailDoc.getText().toString();
                    String pass = edtPassDoc.getText().toString();

                    firebaseAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful()){
                                irPrincipal();


                            }else{
                                String errorCode = ((FirebaseAuthException) task.getException()).getErrorCode();
                                ToastError(errorCode);

                            }

                        }
                    });
                }
            }
        });

    }

    private void irPrincipal() {
        SharedPreferences preferencias = getSharedPreferences("Global", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencias.edit();

        if(edtEmailDoc.getText().toString().equals(""));
        else{
//        editor.clear().apply();
            String[] idparts = edtEmailDoc.getText().toString().split("\\@");
            String correo_id = idparts[0]; // id_unico
            editor.putString("CodigoDocente", correo_id);
            editor.commit();
        }

        Intent i = new Intent(this,MainActivity.class);
//        i.putExtra("mail",edtEmailDoc.getText().toString());
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
        finish();
    }

    private void ToastError(String error) {

        switch (error) {

            case "ERROR_INVALID_CUSTOM_TOKEN":
                Toast.makeText(Logindoc.this, "El formato del token personalizado es incorrecto. Por favor revise la documentación", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_CUSTOM_TOKEN_MISMATCH":
                Toast.makeText(Logindoc.this, "El token personalizado corresponde a una audiencia diferente.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_INVALID_CREDENTIAL":
                Toast.makeText(Logindoc.this, "La credencial de autenticación proporcionada tiene un formato incorrecto o ha caducado.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_INVALID_EMAIL":
                Toast.makeText(Logindoc.this, "La dirección de correo electrónico está mal formateada.", Toast.LENGTH_LONG).show();
                edtEmailDoc.setError("La dirección de correo electrónico está mal formateada.");
                edtEmailDoc.requestFocus();
                break;

            case "ERROR_WRONG_PASSWORD":
                Toast.makeText(Logindoc.this, "La contraseña no es válida o el usuario no tiene contraseña.", Toast.LENGTH_LONG).show();
                edtPassDoc.setError("la contraseña es incorrecta ");
                edtPassDoc.requestFocus();
                edtPassDoc.setText("");
                break;

            case "ERROR_USER_MISMATCH":
                Toast.makeText(Logindoc.this, "Las credenciales proporcionadas no corresponden al usuario que inició sesión anteriormente..", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_REQUIRES_RECENT_LOGIN":
                Toast.makeText(Logindoc.this, "Esta operación es sensible y requiere autenticación reciente. Inicie sesión nuevamente antes de volver a intentar esta solicitud.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL":
                Toast.makeText(Logindoc.this, "Ya existe una cuenta con la misma dirección de correo electrónico pero diferentes credenciales de inicio de sesión. Inicie sesión con un proveedor asociado a esta dirección de correo electrónico.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_EMAIL_ALREADY_IN_USE":
                Toast.makeText(Logindoc.this, "La dirección de correo electrónico ya está siendo utilizada por otra cuenta..   ", Toast.LENGTH_LONG).show();
                edtEmailDoc.setError("La dirección de correo electrónico ya está siendo utilizada por otra cuenta.");
                edtEmailDoc.requestFocus();
                break;

            case "ERROR_CREDENTIAL_ALREADY_IN_USE":
                Toast.makeText(Logindoc.this, "Esta credencial ya está asociada con una cuenta de usuario diferente.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_USER_DISABLED":
                Toast.makeText(Logindoc.this, "La cuenta de usuario ha sido inhabilitada por un administrador..", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_USER_TOKEN_EXPIRED":
                Toast.makeText(Logindoc.this, "La credencial del usuario ya no es válida. El usuario debe iniciar sesión nuevamente.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_USER_NOT_FOUND":
                Toast.makeText(Logindoc.this, "No hay ningún registro de usuario que corresponda a este identificador. Es posible que se haya eliminado al usuario.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_INVALID_USER_TOKEN":
                Toast.makeText(Logindoc.this, "La credencial del usuario ya no es válida. El usuario debe iniciar sesión nuevamente.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_OPERATION_NOT_ALLOWED":
                Toast.makeText(Logindoc.this, "Esta operación no está permitida. Debes habilitar este servicio en la consola.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_WEAK_PASSWORD":
                Toast.makeText(Logindoc.this, "La contraseña proporcionada no es válida..", Toast.LENGTH_LONG).show();
                edtPassDoc.setError("La contraseña no es válida, debe tener al menos 6 caracteres");
                edtPassDoc.requestFocus();
                break;

        }
    }

}