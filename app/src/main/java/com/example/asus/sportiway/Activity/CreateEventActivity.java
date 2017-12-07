package com.example.asus.sportiway.Activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.asus.sportiway.Model.FirebaseEvents;
import com.example.asus.sportiway.Preferences.SessionManager;
import com.example.asus.sportiway.R;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.HashMap;
import java.util.Map;

public class CreateEventActivity extends AppCompatActivity implements View.OnClickListener {

    Toolbar toolbar;
    EditText edtNama, edtTime, edtDate, edtPrice, edtQuota;
    RadioButton rdoMale, rdoFemale, rdoBoth, rdoFree, rdoPrice, rdoUnlimited, rdoQuota;
    Button btnCreate;
    ImageView imgIcon;
    TextView txtAddress;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    Typeface custom_font;

    int PLACE_PICKER_REQUEST = 1;
    Place place;

    int price, quota;
    String gender = "male", latitude= "0", longitude = "0", category="Sepak Bola";
    boolean bayar, terbatas;

    SessionManager sessionManager;

    private void binding() {
        edtNama = (EditText) findViewById(R.id.edt_name);
        edtTime = (EditText) findViewById(R.id.edt_time);
        edtDate = (EditText) findViewById(R.id.edt_date);
        edtPrice = (EditText) findViewById(R.id.edt_price);
        edtQuota = (EditText) findViewById(R.id.edt_quota);
        rdoMale = (RadioButton) findViewById(R.id.rdo_male);
        rdoFemale = (RadioButton) findViewById(R.id.rdo_female);
        rdoBoth = (RadioButton) findViewById(R.id.rdo_both);
        rdoFree = (RadioButton) findViewById(R.id.rdo_free);
        rdoPrice = (RadioButton) findViewById(R.id.rdo_price);
        rdoUnlimited = (RadioButton) findViewById(R.id.rdo_unlimited);
        rdoQuota = (RadioButton) findViewById(R.id.rdo_quota);
        btnCreate = (Button) findViewById(R.id.btn_create);
        txtAddress = (TextView) findViewById(R.id.txt_address);
        imgIcon = (ImageView) findViewById(R.id.img_icon);

        custom_font = Typeface.createFromAsset(getAssets(), "fonts/avenir.otf");

        sessionManager = new SessionManager(this);

        rdoMale.setOnClickListener(this);
        rdoFemale.setOnClickListener(this);
        rdoBoth.setOnClickListener(this);
        rdoFree.setOnClickListener(this);
        rdoPrice.setOnClickListener(this);
        rdoUnlimited.setOnClickListener(this);
        rdoQuota.setOnClickListener(this);
        btnCreate.setOnClickListener(this);
        txtAddress.setOnClickListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        binding();
        rdoMale.setChecked(true);
        rdoFree.setChecked(true);
        rdoUnlimited.setChecked(true);
        mAuth = FirebaseAuth.getInstance();


        Intent intent = getIntent();
        category = intent.getStringExtra("category");
        switch (category){
            case "Sepak Bola":
                imgIcon.setImageResource(R.drawable.bola);
                break;
            case "Jogging":
                imgIcon.setImageResource(R.drawable.sepatu);
                break;
            case "Bulu Tangkis":
                imgIcon.setImageResource(R.drawable.bultang);
                break;
            case "Basket":
                imgIcon.setImageResource(R.drawable.basket);
                break;
            default :
                imgIcon.setImageResource(R.drawable.bola);
                break;
        }

    }

    @Override
    public void onClick(View view) {
        if (view == rdoMale) {
            rdoMale.setChecked(true);
            rdoFemale.setChecked(false);
            rdoBoth.setChecked(false);
            gender = "male";
        } else if (view == rdoFemale) {
            rdoMale.setChecked(false);
            rdoFemale.setChecked(true);
            rdoBoth.setChecked(false);
            gender = "female";
        } else if (view == rdoBoth) {
            rdoMale.setChecked(false);
            rdoFemale.setChecked(false);
            rdoBoth.setChecked(true);
            gender = "both";
        } else if (view == rdoFree) {
            rdoFree.setChecked(true);
            rdoPrice.setChecked(false);
            bayar = false;
        } else if (view == rdoPrice) {
            rdoFree.setChecked(false);
            rdoPrice.setChecked(true);
            bayar = true;
        } else if (view == rdoUnlimited) {
            rdoUnlimited.setChecked(true);
            rdoQuota.setChecked(false);
            terbatas = false;
        } else if (view == rdoQuota) {
            rdoUnlimited.setChecked(false);
            rdoQuota.setChecked(true);
            terbatas = true;
        } else if (view == btnCreate) {
            FirebaseApp.initializeApp(this);

            mDatabase = FirebaseDatabase.getInstance().getReference();
            String key = mDatabase.push().getKey();
            writeNewEvent(key);

            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        } else if(view == txtAddress){
            int PLACE_PICKER_REQUEST = 1;
            PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

            try {
                startActivityForResult(builder.build(this), PLACE_PICKER_REQUEST);
            } catch (GooglePlayServicesRepairableException e) {
                e.printStackTrace();
            } catch (GooglePlayServicesNotAvailableException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                place = PlacePicker.getPlace(data, this);
                String toastMsg = String.format(place.getAddress().toString());
                latitude = Double.toString(place.getLatLng().latitude);
                longitude = Double.toString(place.getLatLng().longitude);
                txtAddress.setText(toastMsg);
            }
        }
    }

    private void writeNewEvent(String key) {
        price = (bayar) ? Integer.parseInt(edtPrice.getText().toString()) : 0;
        quota = (terbatas) ? 0 : Integer.parseInt(edtQuota.getText().toString());

        FirebaseEvents.ParticipantBean participantBean = new FirebaseEvents.ParticipantBean(FirebaseAuth.getInstance().getCurrentUser().getUid());
        FirebaseEvents.Location location = new FirebaseEvents.Location(latitude, longitude);
        FirebaseEvents firebaseEvents= new FirebaseEvents(txtAddress.getText().toString(), edtDate.getText().toString(), gender, mAuth.getCurrentUser().getUid(), edtNama.getText().toString(),
                price, quota, edtTime.getText().toString(), category);
        Map<String, Object> postValues = firebaseEvents.toEvents(location, participantBean);

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/event/" + key, postValues);

        sessionManager.createEventIdSession(FirebaseAuth.getInstance().getCurrentUser().getUid(), key);

        mDatabase.updateChildren(childUpdates);
        FirebaseMessaging.getInstance().subscribeToTopic(key);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
