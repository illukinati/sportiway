package com.example.asus.sportiway.Model;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Asus on 06/09/2017.
 */

public class FirebaseEvents {
    public String address;
    public String date;
    public String gender;
    public String host;
    public String name;
    public int price;
    public int quota;
    public String time;
    public String type;
    public List<ParticipantBean> participantBeanList;

    public FirebaseEvents(String address, String date, String gender, String host, String name, int price, int quota, String time, String type) {
        this.address = address;
        this.date = date;
        this.gender = gender;
        this.host = host;
        this.name = name;
        this.price = price;
        this.quota = quota;
        this.time = time;
        this.type = type;
    }

    public static class ParticipantBean{
        public String participant;
        public ParticipantBean(){}

        public ParticipantBean(String participant) {
            this.participant = participant;
        }
        @Exclude
        public Map<String, Object> toParticipant() {
            HashMap<String, Object> result = new HashMap<>();
            result.put(participant, true);
            return result;
        }
    }
    public static class Location{
        public String latitude;
        public String longitude;

        public Location(){}

        public Location(String latitude, String longitude) {
            this.latitude = latitude;
            this.longitude = longitude;
        }

        @Exclude
        public Map<String, Object> toLocation() {
            HashMap<String, Object> result = new HashMap<>();
            result.put("latitude", latitude);
            result.put("longitude", longitude);
            return result;
        }
    }

    @Exclude
    public Map<String, Object> toEvents(FirebaseEvents.Location location, ParticipantBean participantBean) {
        HashMap<String, Object> result = new HashMap<>();


        Map<String, Object> postValues = location.toLocation();
        Map<String, Object> participantValues = participantBean.toParticipant();

        result.put("address", address);
        result.put("date", date);
        result.put("gender", gender);
        result.put("host", host);
        result.put("location", postValues);
        result.put("name", name);
        result.put("price", price);
        result.put("quota", quota);
        result.put("time", time);
        result.put("type", type);
        result.put("participant", participantValues);

        return result;
    }
}
