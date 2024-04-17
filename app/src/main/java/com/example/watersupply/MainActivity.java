package com.example.watersupply;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView userTV;
    TextView supplierTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        userTV = findViewById(R.id.userTV);
        supplierTV = findViewById(R.id.supplierTV);

        userTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              Intent intent = new Intent(MainActivity.this,UserLoginActivity.class);
              startActivity(intent);
            }
        });

        supplierTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,SupplierLoginActivity.class);
                startActivity(intent);
            }
        });
    }
}