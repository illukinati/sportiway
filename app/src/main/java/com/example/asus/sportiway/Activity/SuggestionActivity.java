package com.example.asus.sportiway.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.asus.sportiway.APIService.SportywayService;
import com.example.asus.sportiway.Adapter.EventAdapter;
import com.example.asus.sportiway.Adapter.SuggestionAdapter;
import com.example.asus.sportiway.Model.ModelEvent;
import com.example.asus.sportiway.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SuggestionActivity extends AppCompatActivity {

    ArrayList<ModelEvent.EventBean> eventses;
    Retrofit retrofit;
    RecyclerView rvEvents;

    String keyId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggestion);
        retrofit = new Retrofit.Builder()
                .baseUrl("https://sportiway.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Intent intent = getIntent();
        keyId = intent.getStringExtra("eventId");
        rvEvents = (RecyclerView) findViewById(R.id.rvEvents);
        getEventList();
        rvEvents.setHasFixedSize(true);
        rvEvents.setLayoutManager(new LinearLayoutManager(this));

    }

    private void getEventList() {
        final SportywayService apiService = retrofit.create(SportywayService.class);

        Call<ModelEvent> call = apiService.getEvent("suggestion/?eventId=" + keyId);
        call.enqueue(new Callback<ModelEvent>() {
            @Override
            public void onResponse(Call<ModelEvent> call, Response<ModelEvent> response) {
                ModelEvent modelEvent = response.body();
                eventses = new ArrayList<ModelEvent.EventBean>();
                for (int i = 0; i < modelEvent.getEvent().size(); i++) {
                    eventses.add(new ModelEvent.EventBean(
                            modelEvent.getEvent().get(i).getEventId(),
                            modelEvent.getEvent().get(i).getLatitude(),
                            modelEvent.getEvent().get(i).getLongitude(),
                            modelEvent.getEvent().get(i).getType(),
                            modelEvent.getEvent().get(i).getName(),
                            modelEvent.getEvent().get(i).getQuota(),
                            modelEvent.getEvent().get(i).getTime(),
                            modelEvent.getEvent().get(i).getAddress(),
                            modelEvent.getEvent().get(i).getPrice(),
                            modelEvent.getEvent().get(i).getDate(),
                            modelEvent.getEvent().get(i).getGender()));
                }

                SuggestionAdapter adapter = new SuggestionAdapter(SuggestionActivity.this, eventses);
                rvEvents.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<ModelEvent> call, Throwable t) {
                Log.d("Error", t.getMessage());
            }
        });

    }
}
