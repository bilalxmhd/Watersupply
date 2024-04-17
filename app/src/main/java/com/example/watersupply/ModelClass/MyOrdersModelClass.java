package com.example.watersupply.ModelClass;

public class MyOrdersModelClass {

    String id;
    String sid;
    String sname;
    String quantity;
    String amount;
    String date;

    public MyOrdersModelClass(String id,String sid,String sname,String quantity,String amount,String date){
        this.id = id;
        this.sid = sid;
        this.sname = sname;
        this.quantity = quantity;
        this.amount = amount;
        this.date = date;

    }

    public String getId() {
        return id;
    }

    public String getSid() {
        return sid;
    }

    public String getSname() {
        return sname;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getAmount() {
        return amount;
    }

    public String getDate() {
        return date;
    }
}
