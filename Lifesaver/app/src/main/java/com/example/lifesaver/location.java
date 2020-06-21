package com.example.lifesaver;

public class location {

    String key;

    Double latitude, longitude;

    public location(String key, Double latitude, Double longitude) {
        this.key = key;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
