package com.example.asus.sportiway.Model;

import java.util.List;

/**
 * Created by Asus on 07/09/2017.
 */

public class ModelEvent {

    private List<EventBean> event;

    public List<EventBean> getEvent() {
        return event;
    }

    public void setEvent(List<EventBean> event) {
        this.event = event;
    }

    public static class EventBean {
        /**
         * eventId : -KtNT9UwYGXnGpMQdyAt
         * latitude : -6.192722500000009
         * longitude : 106.84076171874997
         * type : Sepak Bola
         * name : Bola Kampung
         * quota : 0
         * time : 15.00-17.00
         * address : Jl. Raden Saleh Raya No.54A, Cikini, Menteng, Kota Jakarta Pusat, Daerah Khusus Ibukota Jakarta 10330, Indonesia
         * price : 10000
         */

        private String eventId;
        private String latitude;
        private String longitude;
        private String type;
        private String name;
        private int quota;
        private String time;
        private String address;
        private int price;
        private String date;
        private String gender;

        public EventBean(String eventId, String latitude, String longitude, String type, String name, int quota, String time, String address, int price, String date, String gender) {
            this.eventId = eventId;
            this.latitude = latitude;
            this.longitude = longitude;
            this.type = type;
            this.name = name;
            this.quota = quota;
            this.time = time;
            this.address = address;
            this.price = price;
            this.date = date;
            this.gender = gender;
        }

        public String getEventId() {
            return eventId;
        }

        public void setEventId(String eventId) {
            this.eventId = eventId;
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

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
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

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
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
    }
}
