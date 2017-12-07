package com.example.asus.sportiway.Activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.sportiway.APIService.SportywayService;
import com.example.asus.sportiway.Model.ModelNotification;
import com.example.asus.sportiway.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    EditText edtEmail, edtPassword;
    TextView txtRegister;
    Button btnMasuk;
    Typeface custom_font;

    Retrofit retrofit;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        binding();
        customFont();
        retrofit = new Retrofit.Builder()
                .baseUrl("https://sportiway.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Override
    public void onClick(View view) {
        if(view == txtRegister){
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
        } else if(view == btnMasuk){
            firebaseLogin();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }

    private void binding(){
        edtEmail = (EditText) findViewById(R.id.edt_email);
        edtPassword = (EditText) findViewById(R.id.edt_password);
        txtRegister = (TextView) findViewById(R.id.txt_register);
        btnMasuk = (Button) findViewById(R.id.btn_masuk);
        custom_font = Typeface.createFromAsset(getAssets(),  "fonts/avenir.otf");

        txtRegister.setOnClickListener(this);
        btnMasuk.setOnClickListener(this);
    }

    private void customFont(){
        txtRegister.setTypeface(custom_font);
        edtEmail.setTypeface(custom_font);
        edtPassword.setTypeface(custom_font);
        btnMasuk.setTypeface(custom_font);
    }

    private void firebaseLogin(){

        try {
            mAuth = FirebaseAuth.getInstance();
            mAuth.signInWithEmailAndPassword(edtEmail.getText().toString(), edtPassword.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(LoginActivity.this, "Email dan kata sandi salah", Toast.LENGTH_LONG).show();
                            } else {
                                mDatabase = FirebaseDatabase.getInstance().getReference("/users/" + mAuth.getCurrentUser().getUid() + "/fcm_token");
                                mDatabase.setValue(FirebaseInstanceId.getInstance().getToken());
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                            }
                        }
                    });
        } catch (Exception e){
            Toast.makeText(this, "Email atau kata sandi tidak boleh kosong", Toast.LENGTH_LONG).show();
            return;
        }
    }
}
