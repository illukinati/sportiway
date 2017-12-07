package com.example.asus.sportiway.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.asus.sportiway.APIService.SportywayService;
import com.example.asus.sportiway.Adapter.EventAdapter;
import com.example.asus.sportiway.Model.Events;
import com.example.asus.sportiway.Model.ModelEvent;
import com.example.asus.sportiway.R;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EventFragment extends Fragment {

    ArrayList<ModelEvent.EventBean> eventses;
    RecyclerView rvEvents;
    Retrofit retrofit;

    public EventFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event, container, false);
        rvEvents = (RecyclerView) view.findViewById(R.id.rvEvents);

        retrofit = new Retrofit.Builder()
                .baseUrl("https://sportiway.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

//        eventses = Events.createEventList(20);
        getEventList();
        rvEvents.setHasFixedSize(true);
        rvEvents.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;
    }

    private void getEventList() {
        final SportywayService apiService = retrofit.create(SportywayService.class);

        Call<ModelEvent> call = apiService.getEvent("event-list");
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

                EventAdapter adapter = new EventAdapter(getContext(), eventses);
                rvEvents.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<ModelEvent> call, Throwable t) {
                Log.d("Error", t.getMessage());
            }
        });

    }
}
