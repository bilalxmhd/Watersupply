package com.example.watersupply.ModelClass;

public class PaymentsListModelClass {

    String id;
    String username;
    String amount;
    String date;

    public PaymentsListModelClass(String id,String username,String amount,String date){
        this.id = id;
        this.username = username;
        this.amount = amount;
        this.date = date;

    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getAmount() {
        return amount;
    }

    public String getDate() {
        return date;
    }

}
