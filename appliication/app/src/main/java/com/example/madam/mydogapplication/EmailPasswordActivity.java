package com.example.madam.mydogapplication;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class EmailPasswordActivity extends AppCompatActivity implements View.OnClickListener{

    //переменные для аунтификации через FireBase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    //объявление объектов активити
    private EditText ETemail;
    private EditText ETpassword;

    //создание активити
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_password);

        //проверка авторизации
        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                } else {
                    // User is signed out
                }
            }
        };
        //конец "проверка авторизации"

        //объявление объектов активити
        ETemail = findViewById(R.id.et_email);
        ETpassword = findViewById(R.id.et_password);

        findViewById(R.id.btn_sign_in).setOnClickListener(this);
        findViewById(R.id.btn_registration).setOnClickListener(this);
    }

    //событие нажатие кнопки
    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btn_sign_in)
        {
            signin(ETemail.getText().toString(),ETpassword.getText().toString());
        }else if (view.getId() == R.id.btn_registration)
        {
            registration(ETemail.getText().toString(),ETpassword.getText().toString());
        }

    }
    //конец "событие нажатие кнопки"

    //авторизация
    public void signin(String email , String password)
    {
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    Toast.makeText(EmailPasswordActivity.this, "Aвторизация успешна", Toast.LENGTH_SHORT).show();
                }else
                    Toast.makeText(EmailPasswordActivity.this, "Ошибка авторизации", Toast.LENGTH_SHORT).show();

            }
        });
    }
    //конец "авторизация"

    //регистрация
    public void registration (String email , String password){
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(EmailPasswordActivity.this, "Регистрация успешна", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(EmailPasswordActivity.this, "Ошибка регистрации", Toast.LENGTH_SHORT).show();
            }
        });
    }
    //конец "регистрация"


}
