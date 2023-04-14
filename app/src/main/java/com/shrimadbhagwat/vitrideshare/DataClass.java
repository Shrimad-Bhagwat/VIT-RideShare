package com.shrimadbhagwat.vitrideshare;

public class DataClass {

    private  String name;
    private  String from_location;
    private  String to_location;
    private  String date;
    private  String contact;


    public DataClass(String name, String from_location, String to_location, String date, String contact) {
        this.name = name;
        this.from_location = from_location;
        this.to_location = to_location;
        this.date = date;
        this.contact = contact;
    }

    public String getName() {
        return name;
    }

    public String getFrom_location() {
        return from_location;
    }

    public String getTo_location() {
        return to_location;
    }

    public String getDate() {
        return date;
    }

    public String getContact() {
        return contact;
    }

    public DataClass(){

    }

}
