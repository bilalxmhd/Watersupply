package com.example.watersupply;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
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
import com.example.watersupply.Services.LocationUploadService;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SupplierRegisterActivity extends AppCompatActivity {

    EditText nameET;
    EditText addressET;
    EditText numberET;
    EditText emailET;
    EditText passwordET;
    Button registerBT;
    Button idProofBT;
    TextView idTV;

    private GlobalPreference globalPreference;
    private String ip;
    private String encodeImage;
    private boolean val;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supplier_register);

        globalPreference = new GlobalPreference(this);
        ip = globalPreference.getIp();

        nameET = findViewById(R.id.sNameEditText);
        addressET = findViewById(R.id.sAddressEditText);
        numberET = findViewById(R.id.sNumberEditText);
        emailET = findViewById(R.id.sEmailEditText);
        passwordET = findViewById(R.id.sPasswordEditText);
        registerBT = findViewById(R.id.sRegisterButton);
        idProofBT = findViewById(R.id.idProofButton);
        idTV = findViewById(R.id.idTextView);

        idProofBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select Picture"), 100);
            }
        });

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
        else if (passwordET.getText().equals("") || passwordET.getText().length() < 3) {
            passwordET.setError("Password Empty or It Doesnot contain 3 letter");
        }
        else if (emailET.getText().length()>0) {
            val =  validateEmail(emailET);
            if(val==true){
                sRegister();
            }
            else{
                Toast.makeText(SupplierRegisterActivity.this,"Please Check Your Email id",Toast.LENGTH_LONG).show();
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK){
            if (requestCode == 100){
                Uri filepath = data.getData();

                try {
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filepath);
                    bitmap.compress(Bitmap.CompressFormat.JPEG,100,baos);
                    byte[] imageBytes = baos.toByteArray();
                    encodeImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
                    idTV.setText("idproof.jpg");
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }

    }


    private void sRegister() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://"+ ip +"/water_supply/api/sRegister.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equals("success")){
                    Intent intent = new Intent(SupplierRegisterActivity.this,SupplierLoginActivity.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(SupplierRegisterActivity.this,""+response,Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SupplierRegisterActivity.this,""+error,Toast.LENGTH_SHORT).show();
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
                params.put("password",passwordET.getText().toString());
                params.put("idproof",encodeImage);
                return params;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(SupplierRegisterActivity.this);
        requestQueue.add(stringRequest);
    }

    private boolean validateEmail(EditText emailET) {
        String email = emailET.getText().toString();

        if(!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            // Toast.makeText(UserRegisterActivity.this,"Email Validated",Toast.LENGTH_SHORT).show();
            return true;
        }else {
            Toast.makeText(SupplierRegisterActivity.this, "Invalid email", Toast.LENGTH_SHORT).show();
            return false;

        }
    }
}