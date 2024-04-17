package com.example.watersupply;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class SupplierHomeActivity extends AppCompatActivity {

    CardView sprofileCV;
    CardView bookingsCV;
    CardView paymentsCV;
    CardView feedbacksCV;
    TextView supplierNameTV;
    TextView sLogoutTV;

    private GlobalPreference globalPreference;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supplier_home);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        globalPreference = new GlobalPreference(this);
        name = globalPreference.getName();

        sprofileCV = findViewById(R.id.card_sProfile);
        bookingsCV = findViewById(R.id.card_sbookings);
        paymentsCV = findViewById(R.id.card_spayment);
        feedbacksCV = findViewById(R.id.card_sfeedback);
        supplierNameTV = findViewById(R.id.suppliernameTextView);
        sLogoutTV = findViewById(R.id.slogoutTextView);

        supplierNameTV.setText(name);

        sprofileCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SupplierHomeActivity.this,SupplierProfileActivity.class);
                startActivity(intent);
            }
        });

        bookingsCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SupplierHomeActivity.this,OrdersActivity.class);
                startActivity(intent);
            }
        });

        paymentsCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SupplierHomeActivity.this,ViewPaymentsActivity.class);
                startActivity(intent);
            }
        });

        feedbacksCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SupplierHomeActivity.this,ViewFeedbackActivity.class);
                startActivity(intent);
            }
        });

        sLogoutTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SupplierHomeActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });


    }
}