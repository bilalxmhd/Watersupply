package com.example.watersupply;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class UserHomeActivity extends AppCompatActivity {

    CardView profileCV;
    CardView predictionCV;
    CardView suppliersCV;
    CardView bookingsCV;
    CardView ordersCV;
    CardView feedbackCV;
    TextView usernameTV;
    TextView logoutTV;

    private GlobalPreference globalPreference;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);

       ActionBar actionBar = getSupportActionBar();
       actionBar.hide();

       globalPreference = new GlobalPreference(this);
       name = globalPreference.getName();

       profileCV = findViewById(R.id.card_profile);
       predictionCV = findViewById(R.id.card_prediction);
       suppliersCV = findViewById(R.id.card_viewSuppliers);
       bookingsCV = findViewById(R.id.card_bookings);
       ordersCV = findViewById(R.id.card_orders);
       feedbackCV = findViewById(R.id.card_feedback);
       usernameTV = findViewById(R.id.usernameTextView);
       logoutTV = findViewById(R.id.logoutTextView);

       usernameTV.setText(name);


       profileCV.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(UserHomeActivity.this,UserProfileActivity.class);
               startActivity(intent);
           }
       });

       predictionCV.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(UserHomeActivity.this,PredictionActivity.class);
               startActivity(intent);
           }
       });

       suppliersCV.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(UserHomeActivity.this,SupplierActivity.class);
               startActivity(intent);
           }
       });

       bookingsCV.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(UserHomeActivity.this,BookingDetailsActivity.class);
               startActivity(intent);
           }
       });

        ordersCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserHomeActivity.this,MyOrdersActivity.class);
                startActivity(intent);
            }
        });


       feedbackCV.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(UserHomeActivity.this,FeedbackActivity.class);
               startActivity(intent);
           }
       });

       logoutTV.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(UserHomeActivity.this,MainActivity.class);
               startActivity(intent);
           }
       });
    }
}