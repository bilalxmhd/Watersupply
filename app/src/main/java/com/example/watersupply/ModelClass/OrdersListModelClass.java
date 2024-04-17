package com.example.watersupply.ModelClass;

public class OrdersListModelClass {

    String id;
    String name;
    String quantity;
    String date;
    String latitude;
    String longitude;

    public OrdersListModelClass(String id,String name,String quantity,String date,String latitude,String longitude){
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.date = date;
        this.latitude = latitude;
        this.longitude = longitude;

    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getDate() {
        return date;
    }

    public String getLatitude(){
        return latitude;
    }

    public String getLongitude(){
        return longitude;
    }

}
