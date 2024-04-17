package com.example.watersupply;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class WaterActivity extends AppCompatActivity {

    private String water;
    private String cans;
    int num;
    TextView waterTV;

    private TextView tWaterTV;
    private TextView cansTV;
    private ImageView backIV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        tWaterTV = findViewById(R.id.TitleTextView);
        backIV = findViewById(R.id.BackImageButton);
        tWaterTV.setText("Required Water");

        backIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WaterActivity.this,UserHomeActivity.class);
                startActivity(intent);
            }
        });

       water = getIntent().getStringExtra("requiredWater");

       waterTV = findViewById(R.id.waterRequiredTextView);
      // cansTV = findViewById(R.id.cansTextView);

       waterTV.setText(water);





    }
}