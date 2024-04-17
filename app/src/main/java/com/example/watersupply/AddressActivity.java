package com.example.watersupply;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;

public class AddressActivity extends AppCompatActivity {

    RadioButton addressRB;
    RadioButton locationRB;
    EditText addressET;
    Button submitBT;

    LinearLayout addressLL;
    LinearLayout locationLL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);

        addressET = findViewById(R.id.dAddressEditText);
        addressRB = findViewById(R.id.addressRB);
        locationRB = findViewById(R.id.locationRB);
        submitBT = findViewById(R.id.submitButtonn);
        addressLL = findViewById(R.id.addressLL);
        locationLL = findViewById(R.id.locationLL);

        addressRB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                locationLL.setVisibility(View.INVISIBLE);
            }
        });

        locationLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addressLL.setVisibility(View.INVISIBLE);

            }
        });

        submitBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AddressActivity.this,SuccessActivity.class);
                startActivity(intent);
            }
        });
    }
}