package com.example.asus.sportiway.Model;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by Asus on 06/09/2017.
 */

@IgnoreExtraProperties
public class Users {
    public String address;
    public String birthdate;
    public String email;
    public int event_joined;
    public int event_made;
    public String fcm_token;
    public String gender;
    public String name;
    public String phone;
    public int reputation;
    public String sport_type;
    public Chara chara;
    public List<EventsBean> history;

    public Users() {}

    public Users(String address, String birthdate, String email, int event_joined, int event_made, String fcm_token, String gender, String name, String phone, int reputation, String sport_type, Chara chara) {
        this.address = address;
        this.birthdate = birthdate;
        this.email = email;
        this.event_joined = event_joined;
        this.event_made = event_made;
        this.fcm_token = fcm_token;
        this.gender = gender;
        this.name = name;
        this.phone = phone;
        this.reputation = reputation;
        this.sport_type = sport_type;
        this.chara = chara;
    }


    public static class EventsBean{
        public String event;

        public EventsBean(String event) {
            this.event = event;
        }
    }

    public static class Chara{
        public String avatar;
        public int level;

        public Chara(){}

        public Chara(String avatar, int level) {
            this.avatar = avatar;
            this.level = level;
        }

        @Exclude
        public Map<String, Object> toChara() {
            HashMap<String, Object> result = new HashMap<>();
            result.put("avatar", avatar);
            result.put("level", level);
            return result;
        }
    }


    @Exclude
    public Map<String, Object> toMap(Chara chara) {
        HashMap<String, Object> result = new HashMap<>();


        Map<String, Object> postValues = chara.toChara();

        result.put("address", address);
        result.put("birthdate", birthdate);
        result.put("chara", postValues);
        result.put("email", email);
        result.put("event_joined", event_joined);
        result.put("event_made", event_made);
        result.put("fcm_token", fcm_token);
        result.put("gender", gender);
        result.put("history", history);
        result.put("name", name);
        result.put("phone", phone);
        result.put("reputation", reputation);
        result.put("sport_type", sport_type);

        return result;
    }
}
