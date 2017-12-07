package com.example.asus.sportiway.APIService;

import com.example.asus.sportiway.Model.Events;
import com.example.asus.sportiway.Model.ModelEvent;
import com.example.asus.sportiway.Model.ModelNotification;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by Asus on 06/09/2017.
 */

public interface SportywayService {
    @GET
    Call<ModelNotification> getNotif(@Url String url);

    @GET
    Call<ModelEvent> getEvent(@Url String url);

    @GET
    Call<ModelNotification> getJoin(@Url String url);

    @GET
    Call<ModelNotification> getMerge(@Url String url);

    @GET
    Call<Events> getEventDetail(@Url String url);

}
