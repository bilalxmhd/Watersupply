package com.example.watersupply;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.watersupply.Services.LocationUploadService;

import java.util.HashMap;
import java.util.Map;

public class UserRegisterActivity extends AppCompatActivity {

    EditText nameET;
    EditText addressET;
    EditText numberET;
    EditText emailET;
    EditText usernameET;
    EditText passwordET;
    Button registerBT;
    private boolean val;

    private GlobalPreference globalPreference;
    private String ip,latitude,longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);

        Intent mIntent = new Intent(getApplicationContext(), LocationUploadService.class);
        startService(mIntent);

        globalPreference = new GlobalPreference(this);
        ip = globalPreference.getIp();
        latitude = globalPreference.getLatitude();
        longitude = globalPreference.getLongitude();


       // Toast.makeText(UserRegisterActivity.this,"load.."+ip,Toast.LENGTH_SHORT).show();

        nameET = findViewById(R.id.uNameEditText);
        addressET = findViewById(R.id.uAddressEditText);
        numberET = findViewById(R.id.uNumberEditText);
        emailET = findViewById(R.id.uEmailEditText);
        usernameET = findViewById(R.id.uUsernameEdiText);
        passwordET = findViewById(R.id.uPasswordEditText);
        registerBT = findViewById(R.id.uregisterButton);

        registerBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insert();
            }
        });
    }

    private void insert() {

        if (nameET.getText().toString().equals("")) {
            nameET.setError("Please Enter name");
        }
        if (addressET.getText().toString().equals("")) {
            addressET.setError("Please Enter address");
        }
        else if (numberET.getText().equals("") || numberET.getText().length() > 10 || numberET.getText().length() < 10) {
            numberET.setError("Invalid Phone number ");
        }
        else if (emailET.getText().equals("")) {
            emailET.setError("Please Enter Email");
        }
        if (usernameET.getText().toString().equals("")) {
            usernameET.setError("Please Enter username");
        }
        else if (passwordET.getText().equals("") || passwordET.getText().length() < 3) {
            passwordET.setError("Password Empty or It Doesnot contain 3 letter");
        }
        else if (emailET.getText().length()>0) {
            val =  validateEmail(emailET);
            if(val==true){
                uRegister();
            }
            else{
                Toast.makeText(UserRegisterActivity.this,"Please Check Your Email id",Toast.LENGTH_LONG).show();
            }
        }
    }

    private void uRegister() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://"+ ip +"/water_supply/api/uRegister.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equals("success")){
                    Intent intent = new Intent(UserRegisterActivity.this,UserLoginActivity.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(UserRegisterActivity.this,""+response,Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(UserRegisterActivity.this,""+error,Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            @Nullable
            protected Map<String,String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("name",nameET.getText().toString());
                params.put("address",addressET.getText().toString());
                params.put("number",numberET.getText().toString());
                params.put("email",emailET.getText().toString());
                params.put("username",usernameET.getText().toString());
                params.put("password",passwordET.getText().toString());
                params.put("latitude",latitude);
                params.put("longitude",longitude);
                return params;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(UserRegisterActivity.this);
        requestQueue.add(stringRequest);

    }

    private boolean validateEmail(EditText emailET) {
        String email = emailET.getText().toString();

        if(!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()){
           // Toast.makeText(UserRegisterActivity.this,"Email Validated",Toast.LENGTH_SHORT).show();
            return true;
        }else {
            Toast.makeText(UserRegisterActivity.this, "Invalid email", Toast.LENGTH_SHORT).show();
            return false;

        }
    }
}