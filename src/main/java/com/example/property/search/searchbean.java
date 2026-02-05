package com.example.property.search;

public class searchbean {

    private int rid;
    private int price;
    private String facing;
    private String name;
    private String contact;
    private String address;
    private int stauts; // Status field (0 = Unavailable, 1 = Available)

    // Constructors
    public searchbean() {}

    public searchbean(int rid, int price, String facing, String name, String contact, String address, int stauts) {
        this.rid = rid;
        this.price = price;
        this.facing = facing;
        this.name = name;
        this.contact = contact;
        this.address = address;
        this.stauts = stauts;
    }

    // Getters and Setters
    public int getRid() {
        return rid;
    }

    public void setRid(int rid) {
        this.rid = rid;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getFacing() {
        return facing;
    }

    public void setFacing(String facing) {
        this.facing = facing;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getStauts() {
        return stauts;
    }

    public void setStauts(int stauts) {
        this.stauts = stauts;
    }
}
