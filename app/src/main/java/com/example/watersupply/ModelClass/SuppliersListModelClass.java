package com.example.watersupply.ModelClass;

public class SuppliersListModelClass {
    String id;
    String name;
    String address;
    String number;
    String rating;


    public SuppliersListModelClass(String id,String name,String address,String number,String rating){
        this.id = id;
        this.name = name;
        this.address = address;
        this.number = number;
        this.rating = rating;

    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getNumber() {
        return number;
    }

    public String getRating() {
        return rating;
    }


}
