package com.example.lifesaver;

public class Hospital {

    private String name, type, city;
    private Integer available, total;

    public Hospital(String name, String type, String city, Integer available, Integer total) {
        this.name = name;
        this.type = type;
        this.city = city;
        this.available = available;
        this.total = total;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getAvailable() {
        return available;
    }

    public void setAvailable(Integer available) {
        this.available = available;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}
