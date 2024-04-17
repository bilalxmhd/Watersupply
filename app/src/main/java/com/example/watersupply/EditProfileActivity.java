package com.example.watersupply;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class EditProfileActivity extends AppCompatActivity {

    EditText nameET;
    EditText addressET;
    EditText numberET;
    EditText emailET;
    TextView changePasswordTV;
    TextView submitTV;
    ImageView backIV;

    private GlobalPreference globalPreference;
    private String ip,uid;
    private Intent intent;
    String intentResponse;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        globalPreference = new GlobalPreference(this);
        ip = globalPreference.getIp();
        uid = globalPreference.getID();

        intent = getIntent();
        intentResponse = intent.getStringExtra("userdata");

        initial();

        setData(intentResponse);

        submitTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateData();
            }
        });

        changePasswordTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditProfileActivity.this,ChangePasswordActivity.class);
                startActivity(intent);
            }
        });

        backIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditProfileActivity.this,UserProfileActivity.class);
                startActivity(intent);
            }
        });

    }

    private void setData(String response) {
        try{
            JSONObject obj = new JSONObject(response);
            JSONArray array = obj.getJSONArray("data");
            JSONObject data = array.getJSONObject(0);

            String name = data.getString("name");
            String number = data.getString("number");
            String address = data.getString("address");
            String email = data.getString("email");

            nameET.setText(name);
            numberET.setText(number);
            addressET.setText(address);
            emailET.setText(email);

        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    private void updateData() {
        String URL = "http://"+ ip +"/water_supply/api/editProfile.php";


        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(EditProfileActivity.this,""+response,Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(EditProfileActivity.this,UserProfileActivity.class);
                startActivity(intent);
                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(EditProfileActivity.this,""+error,Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();

                params.put("name",nameET.getText().toString());
                params.put("number",numberET.getText().toString());
                params.put("address",addressET.getText().toString());
                params.put("email",emailET.getText().toString());
                params.put("uid",uid);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }


    private void initial() {
        nameET = findViewById(R.id.pNameEditText);
        numberET = findViewById(R.id.pNumberEditText);
        addressET = findViewById(R.id.pAddressEditText);
        emailET = findViewById(R.id.pEmailEditText);
        changePasswordTV = findViewById(R.id.changePasswordTextView);
        submitTV = findViewById(R.id.pSubmitButtonTextView);
        backIV = findViewById(R.id.BackImageButton);

    }
}