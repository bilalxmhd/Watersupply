package com.example.watersupply;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

public class SupplierProfileActivity extends AppCompatActivity {

    private static final String TAG = "SupplierProfileActivity";

    TextView sNameTV;
    TextView sAddressTV;
    TextView sNumberTV;
    TextView sEmailTV;

    ImageView backIV;

    private GlobalPreference globalPreference;
    private String ip,uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supplier_profile);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        globalPreference = new GlobalPreference(this);
        ip = globalPreference.getIp();
        uid = globalPreference.getID();

        iniit();

        backIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SupplierProfileActivity.this,SupplierHomeActivity.class);
                startActivity(intent);
            }
        });

        getSuppliDetails();
    }

    private void getSuppliDetails() {
        String URL = "http://"+ ip +"/water_supply/api/sProfile.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d(TAG, "onResponse: " + response);

                if (!response.equals("")){
                    try{

                        //userdata = response;

                        JSONObject obj = new JSONObject(response);
                        JSONArray array = obj.getJSONArray("data");
                        JSONObject data = array.getJSONObject(0);

                        String name = data.getString("name");
                        String address = data.getString("address");
                        String number = data.getString("number");
                        String email = data.getString("email");



                        sNameTV.setText(name);
                        sAddressTV.setText(address);
                        sNumberTV.setText(number);
                        sEmailTV.setText(email);

                    }catch (JSONException e){
                        e.printStackTrace();
                    }
                }
                else{
                    Toast.makeText(SupplierProfileActivity.this,"loading",Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("uid",uid);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    private void iniit() {
        sNameTV = findViewById(R.id.sNameTextView);
        sAddressTV = findViewById(R.id.sAddressTextView);
        sEmailTV = findViewById(R.id.sEmailTextView);
        sNumberTV = findViewById(R.id.sNumberTextView);
        backIV = findViewById(R.id.back_imageView2);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        getSuppliDetails();
    }
}