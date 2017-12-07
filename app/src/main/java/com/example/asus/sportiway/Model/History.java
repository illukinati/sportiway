package com.example.asus.sportiway.Model;

import java.util.ArrayList;

/**
 * Created by Asus on 06/09/2017.
 */

public class History {
    private String name;
    private String address;
    private String date;
    private String time;
    private String type;
    private String reputation;
    private String shared;

    public History(String name, String address, String date, String time, String type, String reputation, String shared) {
        this.name = name;
        this.address = address;
        this.date = date;
        this.time = time;
        this.type = type;
        this.reputation = reputation;
        this.shared = shared;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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

    public String getReputation() {
        return reputation;
    }

    public void setReputation(String reputation) {
        this.reputation = reputation;
    }

    public String getShared() {
        return shared;
    }

    public void setShared(String shared) {
        this.shared = shared;
    }

    public static ArrayList<History> createEventList(int numContacts) {
        ArrayList<History> histories = new ArrayList<History>();

        histories.add(new History("Sepak Bola Mania", "GOR Ganesha Jl Soehat", "6 September 2017", "17.00-22.00", "Sepak Bola", "50", "12"));
        histories.add(new History("Jogging Jigong", "Alun-alun Nganjuk", "4 September 2017", "05.00-07.00", "Jogging", "550", "122"));
        return histories;
    }
}
