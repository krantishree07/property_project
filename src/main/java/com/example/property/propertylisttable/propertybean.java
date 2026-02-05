package com.example.property.propertylisttable;

public class propertybean {
    private int rid;
    private String contact;
    private String name;
    private String type;
    private String nature;
    private String facing;
    private float rightdim;
    private float leftdim;
    private float frontdim;
    private float backdim;
    private String address;
    private String area;
    private String city;
    private String corporationno;
    private float size;
    private int price;
    private String stauts; // shows "Available" or "Unavailable"

    public propertybean(int rid, String contact, String name, String type, String nature, String facing,
                        float rightdim, float leftdim, float frontdim, float backdim, String address,
                        String area, String city, String corporationno, float size, int price, String stauts) {
        this.rid = rid;
        this.contact = contact;
        this.name = name;
        this.type = type;
        this.nature = nature;
        this.facing = facing;
        this.rightdim = rightdim;
        this.leftdim = leftdim;
        this.frontdim = frontdim;
        this.backdim = backdim;
        this.address = address;
        this.area = area;
        this.city = city;
        this.corporationno = corporationno;
        this.size = size;
        this.price = price;
        this.stauts = stauts;
    }

    // Getters
    public int getRid() { return rid; }
    public String getContact() { return contact; }
    public String getName() { return name; }
    public String getType() { return type; }
    public String getNature() { return nature; }
    public String getFacing() { return facing; }
    public float getRightdim() { return rightdim; }
    public float getLeftdim() { return leftdim; }
    public float getFrontdim() { return frontdim; }
    public float getBackdim() { return backdim; }
    public String getAddress() { return address; }
    public String getArea() { return area; }
    public String getCity() { return city; }
    public String getCorporationno() { return corporationno; }
    public float getSize() { return size; }
    public int getPrice() { return price; }
    public String getStauts() { return stauts; }
}
