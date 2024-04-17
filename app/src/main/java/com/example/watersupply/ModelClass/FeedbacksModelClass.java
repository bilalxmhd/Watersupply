package com.example.watersupply.ModelClass;

public class FeedbacksModelClass {

    String id;
    String username;
    String feedback;
    String complaint;


    public FeedbacksModelClass(String id,String username,String feedback,String complaint){
        this.id = id;
        this.username = username;
        this.feedback = feedback;
        this.complaint = complaint;

    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getFeedback() {
        return feedback;
    }

    public String getComplaint() {
        return complaint;
    }

}
