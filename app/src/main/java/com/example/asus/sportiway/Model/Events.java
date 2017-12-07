package com.example.asus.sportiway.Model;

import java.util.List;

/**
 * Created by Asus on 06/09/2017.
 */

public class Events {

    /**
     * address : Jl. Raden Saleh Raya No.49, Cikini, Menteng, Kota Jakarta Pusat, Daerah Khusus Ibukota Jakarta 10330, Indonesia
     * date : 7 September 2017
     * gender : both
     * host : cZrzBKACkBSYYxg96LMbYV7s9Zs1
     * latitude : -6.1931075000000035
     * longitude : 106.84112890625003
     * name : Lari Pagi
     * participant : ["id1"]
     * price : 0
     * quota : 0
     * time : 05.00-07.00
     * type : Jogging
     */

    private String address;
    private String date;
    private String gender;
    private String host;
    private String latitude;
    private String longitude;
    private String name;
    private int price;
    private int quota;
    private String time;
    private String type;
    private List<String> participant;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuota() {
        return quota;
    }

    public void setQuota(int quota) {
        this.quota = quota;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<String> getParticipant() {
        return participant;
    }

    public void setParticipant(List<String> participant) {
        this.participant = participant;
    }
}
