package com.example.watersupply;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

public class UserProfileActivity extends AppCompatActivity {

    private static final String TAG = "ProfileActivity";

    TextView uNameTV;
    TextView uAddressTV;
    TextView uNumberTV;
    TextView uEmailTV;
    TextView uUsernameTV;
    ImageView backIV;
    ConstraintLayout profileCL;

    private GlobalPreference globalPreference;
    private String ip,uid;
    String userdata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        globalPreference = new GlobalPreference(this);
        ip = globalPreference.getIp();
        uid = globalPreference.getID();

       iniit();

        backIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserProfileActivity.this,UserHomeActivity.class);
                startActivity(intent);
            }
        });

        getUserDetails();

        profileCL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserProfileActivity.this,EditProfileActivity.class);
                intent.putExtra("userdata",userdata);
                startActivity(intent);
            }
        });


    }

    private void getUserDetails() {
        String URL = "http://"+ ip +"/water_supply/api/uProfile.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d(TAG, "onResponse: " + response);

                if (!response.equals("")){
                    try{

                        userdata = response;

                        JSONObject obj = new JSONObject(response);
                        JSONArray array = obj.getJSONArray("data");
                        JSONObject data = array.getJSONObject(0);

                        String name = data.getString("name");
                        String address = data.getString("address");
                        String number = data.getString("number");
                        String email = data.getString("email");
                        String username = data.getString("username");



                        uNameTV.setText(name);
                        uAddressTV.setText(address);
                        uNumberTV.setText(number);
                        uEmailTV.setText(email);
                        uUsernameTV.setText(username);

                    }catch (JSONException e){
                        e.printStackTrace();
                    }
                }
                else{
                    Toast.makeText(UserProfileActivity.this,"loading",Toast.LENGTH_LONG).show();
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
        uNameTV = findViewById(R.id.nameTextView);
        uAddressTV = findViewById(R.id.addressTextView);
        uNumberTV = findViewById(R.id.numberTextView);
        uEmailTV = findViewById(R.id.emailTextView);
        uUsernameTV = findViewById(R.id.usernameTextView);
        backIV = findViewById(R.id.back_imageView);
        profileCL = findViewById(R.id.profileCL);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        getUserDetails();
    }


}