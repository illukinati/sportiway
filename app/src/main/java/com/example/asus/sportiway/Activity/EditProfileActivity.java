package com.example.asus.sportiway.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.sportiway.Model.Users;
import com.example.asus.sportiway.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.HashMap;
import java.util.Map;

public class EditProfileActivity extends AppCompatActivity implements View.OnClickListener{

    Toolbar toolbar;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    EditText edtName, edtEmail, edtPhone, edtAddress, edtHobby;
    RadioButton rdoMale, rdoFemale;
    Button btnSave;
    TextView txtReputation, txtLevel;
    CardView cvPassword;
    LinearLayout lnrReputation;
    ImageView imgCharacter;

    String gender = "male", character = "satu";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        binding();

        Intent intent = getIntent();
        if(intent.getStringExtra("key") != null){
            if(intent.getStringExtra("character") != null){
                cvPassword.setVisibility(View.GONE);
                lnrReputation.setVisibility(View.INVISIBLE);
                this.character = intent.getStringExtra("character");
            }
        }

        switch (character){
            case "satu":
                imgCharacter.setImageResource(R.drawable.lvl1);
                break;
            case "dua":
                imgCharacter.setImageResource(R.drawable.lvl10);
                break;
            case "tiga":
                imgCharacter.setImageResource(R.drawable.lvl30);
                break;
            default:
                break;
        }

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference("users/" + mAuth.getCurrentUser().getUid());
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Users users = dataSnapshot.getValue(Users.class);
                edtEmail.setText(users.email);
                edtName.setText(users.name);
//                edtPhone.setText(users.phone);
                edtAddress.setText(users.address);
                edtHobby.setText(users.sport_type);
                if(users.gender.equalsIgnoreCase("male")){
                    rdoMale.setChecked(true);
                    rdoFemale.setChecked(false);
                } else if (users.gender.equalsIgnoreCase("female")){
                    rdoMale.setChecked(false);
                    rdoFemale.setChecked(true);
                } else {
                    rdoMale.setChecked(false);
                    rdoFemale.setChecked(false);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        mDatabase.addListenerForSingleValueEvent(valueEventListener);
    }

    @Override
    public void onClick(View view) {
        if(view == btnSave){
            updateUser();
        } else if (view == rdoMale){
            rdoMale.setChecked(true);
            rdoFemale.setChecked(false);
            gender = "male";
        } else if (view == rdoFemale){
            rdoMale.setChecked(false);
            rdoFemale.setChecked(true);
            gender = "female";
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void binding(){
        edtEmail = (EditText) findViewById(R.id.edt_email);
        edtName = (EditText) findViewById(R.id.edt_name);
        edtPhone = (EditText) findViewById(R.id.edt_phone);
        edtAddress = (EditText) findViewById(R.id.edt_address);
        edtHobby = (EditText) findViewById(R.id.edt_hobby);
        rdoMale = (RadioButton) findViewById(R.id.rdo_male);
        rdoFemale = (RadioButton) findViewById(R.id.rdo_female);
        btnSave = (Button) findViewById(R.id.btn_save);
        txtReputation = (TextView) findViewById(R.id.txt_reputation);
        txtLevel = (TextView) findViewById(R.id.txt_level);
        cvPassword = (CardView) findViewById(R.id.cv_password);
        lnrReputation = (LinearLayout) findViewById(R.id.lnr_reputation);
        imgCharacter = (ImageView) findViewById(R.id.img_character);

        btnSave.setOnClickListener(this);
        rdoFemale.setOnClickListener(this);
        rdoMale.setOnClickListener(this);
    }

    private void updateUser(){

        mDatabase.child("name").setValue(edtName.getText().toString());
        mDatabase.child("phone").setValue(edtPhone.getText().toString());
        mDatabase.child("address").setValue(edtAddress.getText().toString());
        mDatabase.child("sport_type").setValue(edtHobby.getText().toString());
        mDatabase.child("chara").child("avatar").setValue(character);
        mDatabase.child("chara").child("level").setValue("1");
        mDatabase.child("gender").setValue(gender);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
        Toast.makeText(this, "Update success", Toast.LENGTH_SHORT).show();
    }
}
