package com.example.lifesaver;

public class Patient {

    private String name, address;
    private Integer age;
    private Long number;

    public Patient() {
    }

    public Patient(String name, String address, Integer age, Long number) {
        this.name = name;
        this.address = address;
        this.age = age;
        this.number = number;
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

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }
}
