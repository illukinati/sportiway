package com.example.asus.sportiway.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.sportiway.APIService.SportywayService;
import com.example.asus.sportiway.Model.Events;
import com.example.asus.sportiway.Model.Users;
import com.example.asus.sportiway.Preferences.SessionManager;
import com.example.asus.sportiway.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProfileActivity extends AppCompatActivity {

    Toolbar toolbar;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    TextView txtTitleName, txtLevel, txtName, txtMakeEvent, txtJoinEvent, txtReputation,
    txtEmail, txtPhone, txtAddress, txtHobby;
    ImageView imgChara, imgAva;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        binding();

        Intent intent = getIntent();
        String uid = intent.getStringExtra("uid");

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference("users/" + uid);
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Users users = dataSnapshot.getValue(Users.class);
                txtEmail.setText(users.email);
                txtName.setText(users.name);
                txtTitleName.setText(users.name + "'s Profile");
                txtPhone.setText(users.phone);
                txtAddress.setText(users.address);
                txtHobby.setText(users.sport_type);
                txtReputation.setText(Integer.toString(users.reputation));
                txtMakeEvent.setText(Integer.toString(users.event_made));
                txtJoinEvent.setText(Integer.toString(users.event_joined));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        mDatabase.addListenerForSingleValueEvent(valueEventListener);
    }

    private void binding(){
        txtTitleName = (TextView) findViewById(R.id.txt_title_name);
        txtLevel = (TextView) findViewById(R.id.txt_level);
        txtName = (TextView) findViewById(R.id.txt_name);
        txtMakeEvent = (TextView) findViewById(R.id.txt_make_event);
        txtJoinEvent = (TextView) findViewById(R.id.txt_join_event);
        txtReputation = (TextView) findViewById(R.id.txt_reputation);
        txtEmail = (TextView) findViewById(R.id.txt_email);
        txtPhone = (TextView) findViewById(R.id.txt_phone);
        txtAddress = (TextView) findViewById(R.id.txt_address);
        txtHobby = (TextView) findViewById(R.id.txt_hobby);
        imgAva = (ImageView) findViewById(R.id.img_ava);
        imgChara = (ImageView) findViewById(R.id.img_chara);

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
