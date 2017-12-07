package com.example.asus.sportiway.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.asus.sportiway.APIService.SportywayService;
import com.example.asus.sportiway.Model.Events;
import com.example.asus.sportiway.Model.ModelNotification;
import com.example.asus.sportiway.Preferences.SessionManager;
import com.example.asus.sportiway.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.messaging.FirebaseMessaging;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MergeEventActivity extends AppCompatActivity implements View.OnClickListener {

    Toolbar toolbar;
    TextView txtTitle, txtHostName, txtHostTelp, txtQuota, txtDate, txtTime, txtAddress, txtPrice, txtReputation, txtShare;
    ImageView imgType, imgAva, imgGender;
    Button btnAccept, btnDecline;

    String eventRequestId, eventTargetId;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merge_event);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        binding();

        Intent intent = getIntent();
        eventRequestId = intent.getStringExtra("eventRequestId");
        eventTargetId = intent.getStringExtra("eventTargetId");

        getEventDetail();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onClick(View view) {
        if (view == btnDecline) {
            finish();
        } else if (view == btnAccept) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                    .setNegativeButton("No", dialogClickListener).show();
        }
    }

    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                case DialogInterface.BUTTON_POSITIVE:
                    getMerge();
                    break;

                case DialogInterface.BUTTON_NEGATIVE:
                    //No button clicked
                    break;
            }
        }
    };

    private void binding() {
        txtTitle = (TextView) findViewById(R.id.txt_title);
        txtHostName = (TextView) findViewById(R.id.txt_host_name);
        txtHostTelp = (TextView) findViewById(R.id.txt_host_telp);
        txtQuota = (TextView) findViewById(R.id.txt_quota);
        txtDate = (TextView) findViewById(R.id.txt_date);
        txtTime = (TextView) findViewById(R.id.txt_time);
        txtAddress = (TextView) findViewById(R.id.txt_address);
        txtPrice = (TextView) findViewById(R.id.txt_price);
        txtReputation = (TextView) findViewById(R.id.txt_reputation);
        txtShare = (TextView) findViewById(R.id.txt_share);
        imgAva = (ImageView) findViewById(R.id.img_ava);
        imgGender = (ImageView) findViewById(R.id.img_icon_gender);
        imgType = (ImageView) findViewById(R.id.img_type);
        btnAccept = (Button) findViewById(R.id.btn_accept);
        btnDecline = (Button) findViewById(R.id.btn_decline);

        sessionManager = new SessionManager(this);
        btnAccept.setOnClickListener(this);
        btnDecline.setOnClickListener(this);
    }

    private void getEventDetail() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://sportiway.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final SportywayService apiService = retrofit.create(SportywayService.class);

        Call<Events> call = apiService.getEventDetail("event/?eventId=" + this.eventTargetId);
        call.enqueue(new Callback<Events>() {
            @Override
            public void onResponse(Call<Events> call, Response<Events> response) {
                Events events = response.body();

                txtTitle.setText(events.getName());
                txtQuota.setText(Double.toString(events.getQuota()));
                txtTime.setText(events.getTime());
                txtAddress.setText(events.getAddress());
                txtPrice.setText(Double.toString(events.getPrice()));
                txtDate.setText(events.getDate());

                switch (events.getType()) {
                    case "Sepak Bola":
                        imgType.setImageResource(R.drawable.bola);
                        break;
                    case "Jogging":
                        imgType.setImageResource(R.drawable.sepatu);
                        break;
                    case "Bulu Tangkis":
                        imgType.setImageResource(R.drawable.bultang);
                        break;
                    default:
                        imgType.setImageResource(R.drawable.bola);
                        break;
                }

                switch (events.getGender()) {
                    case "Male":
                        imgGender.setImageResource(R.drawable.ic_gender_male);
                        break;
                    case "Female":
                        imgGender.setImageResource(R.drawable.ic_gender_female);
                        break;
                    default:
                        imgGender.setImageResource(R.drawable.ic_gender_both);
                        break;
                }
            }

            @Override
            public void onFailure(Call<Events> call, Throwable t) {
                Log.d("Error", t.getMessage());
            }
        });

    }

    private void getMerge() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://sportiway.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final SportywayService apiService = retrofit.create(SportywayService.class);

        Call<ModelNotification> call = apiService.getMerge("merge/?eventRequestId=" + this.eventRequestId + "&eventTargetId=" + this.eventTargetId);
        call.enqueue(new Callback<ModelNotification>() {
            @Override
            public void onResponse(Call<ModelNotification> call, Response<ModelNotification> response) {
                ModelNotification modelNotification = response.body();
                FirebaseMessaging.getInstance().subscribeToTopic(modelNotification.getMessage().toString());
                sessionManager.createEventIdSession(FirebaseAuth.getInstance().getCurrentUser().getUid(), modelNotification.getMessage().toString());
                finish();
            }

            @Override
            public void onFailure(Call<ModelNotification> call, Throwable t) {
                Log.d("Error", t.getMessage());
            }
        });
    }
}
