package com.example.asus.sportiway.Fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.sportiway.APIService.SportywayService;
import com.example.asus.sportiway.Activity.CategoryActivity;
import com.example.asus.sportiway.Activity.CreateEventActivity;
import com.example.asus.sportiway.Activity.EventDetailActivity;
import com.example.asus.sportiway.Model.ModelEvent;
import com.example.asus.sportiway.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MapFragment extends Fragment implements OnMapReadyCallback {

    MapView mapView;
    GoogleMap map;

    ImageView imgAddEvent;
    ArrayList<ModelEvent.EventBean> modelEvents;

    public MapFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_map, container, false);

        mapView = (MapView) v.findViewById(R.id.mapview);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        imgAddEvent = (ImageView) v.findViewById(R.id.img_add_event);
        imgAddEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CategoryActivity.class);
                startActivity(intent);
            }
        });

        return v;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        getListMarker();
        map = googleMap;
        double latitude = -6.192288;
        double longitude = 106.8391543;
        LatLng lokasi = new LatLng(latitude, longitude);
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(lokasi, 15));
        map.getMinZoomLevel();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    private void getListMarker() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://sportiway.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final SportywayService apiService = retrofit.create(SportywayService.class);

        Call<ModelEvent> call = apiService.getEvent("event-list");
        call.enqueue(new Callback<ModelEvent>() {
            @Override
            public void onResponse(Call<ModelEvent> call, Response<ModelEvent> response) {
                final ModelEvent modelEvent = response.body();
                modelEvents = new ArrayList<ModelEvent.EventBean>();

                //final int idx = 0;
                for (int i = 0; i < modelEvent.getEvent().size(); i++) {
                    final int idx = i;
                    modelEvents.add(new ModelEvent.EventBean(
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

                    if(modelEvent.getEvent().get(i).getType().equalsIgnoreCase("Sepak Bola"))
                        map.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.markerbola)).
                            position(new LatLng(Double.parseDouble(modelEvent.getEvent().get(i).getLatitude()), Double.parseDouble(modelEvent.getEvent().get(i).getLongitude()))));
                    else if(modelEvent.getEvent().get(i).getType().equalsIgnoreCase("Jogging"))
                        map.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.markerjogging)).
                                position(new LatLng(Double.parseDouble(modelEvent.getEvent().get(i).getLatitude()), Double.parseDouble(modelEvent.getEvent().get(i).getLongitude()))));
                    else if(modelEvent.getEvent().get(i).getType().equalsIgnoreCase("Basket"))
                        map.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.markerbasket)).
                                position(new LatLng(Double.parseDouble(modelEvent.getEvent().get(i).getLatitude()), Double.parseDouble(modelEvent.getEvent().get(i).getLongitude()))));
                    else if(modelEvent.getEvent().get(i).getType().equalsIgnoreCase("Bulu Tangkis"))
                        map.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.markerbulutangkis)).
                                position(new LatLng(Double.parseDouble(modelEvent.getEvent().get(i).getLatitude()), Double.parseDouble(modelEvent.getEvent().get(i).getLongitude()))));

                    map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                        @Override
                        public boolean onMarkerClick(Marker marker) {
                            Intent intent = new Intent(getActivity(), EventDetailActivity.class);
                            intent.putExtra("eventId", modelEvent.getEvent().get(idx).getEventId());
                            getActivity().startActivity(intent);
                            return false;
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<ModelEvent> call, Throwable t) {
                Log.d("Error", t.getMessage());
            }
        });
    }
}