package com.example.watersupply;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class PredictionActivity extends AppCompatActivity {

    RadioGroup userTypeRG;
    EditText membersET;
    Button predictBT;
    String userType;

    private GlobalPreference globalPreference;
    private String ip;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prediction);

        globalPreference = new GlobalPreference(this);
        ip = globalPreference.getIp();

        userTypeRG = findViewById(R.id.userTypeRG);
        membersET = findViewById(R.id.membersEditText);
        predictBT = findViewById(R.id.predictButton);

        userTypeRG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                RadioButton radioButton = findViewById(checkedId);
                userType = radioButton.getText().toString();

               // Toast.makeText(PredictionActivity.this, ""+userType, Toast.LENGTH_SHORT).show();
            }
        });

        predictBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                predictData();
            }
        });

    }



   private void predictData() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://"+ ip +"/water_supply/api/prediction.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
               // Toast.makeText(PredictionActivity.this, ""+response, Toast.LENGTH_SHORT).show();

                if (response.equals("")){

                    predictData();

                }else{
                    Intent intent = new Intent(PredictionActivity.this,WaterActivity.class);
                    intent.putExtra("requiredWater",response);
                    startActivity(intent);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
               // Toast.makeText(PredictionActivity.this, ""+error, Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            @Nullable
            protected Map<String,String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("userType",userType);
                params.put("members",membersET.getText().toString());
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(PredictionActivity.this);
        requestQueue.add(stringRequest);
    }


}