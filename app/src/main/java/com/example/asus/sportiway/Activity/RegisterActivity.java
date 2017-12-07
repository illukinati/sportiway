package com.example.asus.sportiway.Activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.sportiway.Model.Users;
import com.example.asus.sportiway.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    EditText edtEmail, edtPassword, edtConfirmPassword;
    TextView txtLogin;
    Button btnRegister;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    Typeface custom_font;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        binding();
        customFont();
    }

    @Override
    public void onClick(View view) {
        if (view == txtLogin) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        } else if (view == btnRegister) {
            FirebaseApp.initializeApp(this);

            mAuth = FirebaseAuth.getInstance();
            mAuth.createUserWithEmailAndPassword(edtEmail.getText().toString(), edtPassword.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(RegisterActivity.this, "Error login: " + task.getResult().toString(), Toast.LENGTH_SHORT).show();
                            }

                            mDatabase = FirebaseDatabase.getInstance().getReference();
                            writeNewUser(mAuth.getCurrentUser().getUid(), edtEmail.getText().toString());

                            Intent intent = new Intent(getApplicationContext(), CharacterActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });
        }
    }

    private void writeNewUser(String userId, String email) {
        String key = mDatabase.child("users").push().getKey();

        FirebaseUser userAuth = mAuth.getCurrentUser();

        Users.Chara chara = new Users.Chara("kriting", 1);
        Users users = new Users("", "", email, 0, 0, FirebaseInstanceId.getInstance().getToken(), "", "", "+62", 0, "", chara);
        Map<String, Object> postValues = users.toMap(chara);

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/users/" + userId, postValues);

        mDatabase.updateChildren(childUpdates);
    }

    private void binding() {
        edtEmail = (EditText) findViewById(R.id.edt_email);
        edtPassword = (EditText) findViewById(R.id.edt_password);
        edtConfirmPassword = (EditText) findViewById(R.id.edt_confirm_password);
        btnRegister = (Button) findViewById(R.id.btn_register);
        txtLogin = (TextView) findViewById(R.id.txt_login);

        custom_font = Typeface.createFromAsset(getAssets(), "fonts/avenir.otf");

        btnRegister.setOnClickListener(this);
        txtLogin.setOnClickListener(this);
    }

    private void customFont() {
        edtEmail.setTypeface(custom_font);
        edtPassword.setTypeface(custom_font);
        edtConfirmPassword.setTypeface(custom_font);
        btnRegister.setTypeface(custom_font);
        txtLogin.setTypeface(custom_font);
    }
}
