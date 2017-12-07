package com.example.asus.sportiway.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.sportiway.APIService.SportywayService;
import com.example.asus.sportiway.Model.Events;
import com.example.asus.sportiway.Model.ModelEvent;
import com.example.asus.sportiway.Model.ModelNotification;
import com.example.asus.sportiway.Preferences.SessionManager;
import com.example.asus.sportiway.R;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EventDetailActivity extends AppCompatActivity implements View.OnClickListener {

    Toolbar toolbar;
    Button btnJoin;
    TextView txtTitle, txtQuota, txtTime, txtDate, txtAddress, txtPrice, txtReputation, txtShare;
    ImageView imgType, imgGender, imgShare;
    CardView cvJoined;

    ArrayList<ModelEvent.EventBean> modelEvents;
    String eventId;
    SessionManager sessionManager;
    String eventRequestId, eventTargetId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        binding();

        Intent intent = getIntent();
        eventId = intent.getStringExtra("eventId");
        if(intent.getStringExtra("type") != null){
            if (intent.getStringExtra("type").equals("merge")) {
                eventRequestId = intent.getStringExtra("eventRequestId");
                btnJoin.setText("Merge");
            }
        }

        getEventDetail();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onClick(View view) {
        if (view == btnJoin) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                    .setNegativeButton("No", dialogClickListener).show();
        } else if (view == cvJoined) {
//            Intent intent = new Intent(this, JoinedEvent);
        } else if(view == imgShare){
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_SEND);

            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, "Let's go join me to " + txtTitle.getText().toString() + " !\n" +
                "at " + txtDate.getText().toString() + ". In " + txtAddress.getText().toString() + ". #AyoSehatBareng");
            startActivity(Intent.createChooser(intent, "Share"));
        }
    }

    private void binding() {
        btnJoin = (Button) findViewById(R.id.btn_join);
        txtAddress = (TextView) findViewById(R.id.txt_address);
        txtDate = (TextView) findViewById(R.id.txt_date);
        txtPrice = (TextView) findViewById(R.id.txt_price);
        txtQuota = (TextView) findViewById(R.id.txt_quota);
        txtReputation = (TextView) findViewById(R.id.txt_reputation);
        txtShare = (TextView) findViewById(R.id.txt_share);
        txtTime = (TextView) findViewById(R.id.txt_time);
        txtTitle = (TextView) findViewById(R.id.txt_title);
        imgGender = (ImageView) findViewById(R.id.img_icon_gender);
        imgType = (ImageView) findViewById(R.id.img_type);
        imgShare = (ImageView) findViewById(R.id.img_share);
        cvJoined = (CardView) findViewById(R.id.cv_joined);

        btnJoin.setOnClickListener(this);
        cvJoined.setOnClickListener(this);
        imgShare.setOnClickListener(this);
    }

    private void getEventDetail() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://sportiway.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final SportywayService apiService = retrofit.create(SportywayService.class);

        Call<Events> call = apiService.getEventDetail("event/?eventId=" + this.eventId);
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

    private void getJoin() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://sportiway.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final SportywayService apiService = retrofit.create(SportywayService.class);

        Call<ModelNotification> call = apiService.getJoin("join/?eventId=" + this.eventId + "&uid=" + FirebaseAuth.getInstance().getCurrentUser().getUid());
        FirebaseMessaging.getInstance().subscribeToTopic(this.eventId);
        call.enqueue(new Callback<ModelNotification>() {
            @Override
            public void onResponse(Call<ModelNotification> call, Response<ModelNotification> response) {
                ModelNotification modelNotification = response.body();
                if (modelNotification.getMessage().equalsIgnoreCase("success")) {
                    finish();
                }
            }

            @Override
            public void onFailure(Call<ModelNotification> call, Throwable t) {
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

        Call<ModelNotification> call = apiService.getMerge("requestMerge/?eventRequestId=" + this.eventRequestId + "&eventTargetId=" + this.eventId);
        call.enqueue(new Callback<ModelNotification>() {
            @Override
            public void onResponse(Call<ModelNotification> call, Response<ModelNotification> response) {
                ModelNotification modelNotification = response.body();
                if (modelNotification.getMessage().equals("success")) {
                    FirebaseMessaging.getInstance().subscribeToTopic(modelNotification.getMessage().toString());
                    finish();
                }
            }

            @Override
            public void onFailure(Call<ModelNotification> call, Throwable t) {
                Log.d("Error", t.getMessage());
            }
        });
    }

    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                case DialogInterface.BUTTON_POSITIVE:
                    if (btnJoin.getText().toString().equalsIgnoreCase("Join"))
                        getJoin();
                    else
                        getMerge();
                    break;

                case DialogInterface.BUTTON_NEGATIVE:
                    //No button clicked
                    break;
            }
        }
    };
}
