package com.example.watersupply;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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

import java.util.HashMap;
import java.util.Map;

public class ChangePasswordActivity extends AppCompatActivity {

    EditText currPasswordET;
    EditText newPasswordET;
    Button submitBT;

    private GlobalPreference globalPreference;
    private String ip;
    private String uid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        globalPreference = new GlobalPreference(this);
        ip = globalPreference.getIp();
        uid = globalPreference.getID();

        currPasswordET = findViewById(R.id.currentPasswordET);
        newPasswordET = findViewById(R.id.newPasswordET);
        submitBT = findViewById(R.id.cSubmitButton);

        submitBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatePassword();
            }
        });
    }

    private void updatePassword() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://" + ip + "/water_supply/api/changePassword.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equals("success")){
                    Toast.makeText(ChangePasswordActivity.this, "Password Updated", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(ChangePasswordActivity.this,UserProfileActivity.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(ChangePasswordActivity.this, ""+response, Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ChangePasswordActivity.this, ""+error, Toast.LENGTH_SHORT).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("uid", uid);
                params.put("currentPass", currPasswordET.getText().toString());
                params.put("newPass", newPasswordET.getText().toString());
                return params;

            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}