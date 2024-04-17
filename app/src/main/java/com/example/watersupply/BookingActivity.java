package com.example.watersupply;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

public class BookingActivity extends AppCompatActivity {

    EditText canET;
    EditText litresET;
    Button orderBT;
    ImageView backIV;

    private GlobalPreference globalPreference;
    private String ip,uid;
    private String sid;

    private String amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        globalPreference = new GlobalPreference(this);
        ip = globalPreference.getIp();
        uid = globalPreference.getID();

        sid = getIntent().getStringExtra("id");
        Toast.makeText(this, "supplier.."+sid, Toast.LENGTH_SHORT).show();

        canET = findViewById(R.id.canNumberEditText);
        litresET = findViewById(R.id.litresEditText);
        orderBT = findViewById(R.id.orderButton);
        backIV = findViewById(R.id.backImageView);

        orderBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (litresET.getText().toString().equals("")){
                    Toast.makeText(BookingActivity.this, "Please Fill the Fields", Toast.LENGTH_SHORT).show();
                }else{

                    requiredCans();

                   // Toast.makeText(BookingActivity.this, "Cans...", Toast.LENGTH_SHORT).show();

                    totalAmount();

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            placeOrder();
                        }
                    },3000);



                }


            }
        });

        backIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BookingActivity.this,SupplierActivity.class);
                startActivity(intent);
            }
        });
    }

    private void totalAmount() {

        int cans = Integer.parseInt(canET.getText().toString());

         int  price = cans * 40;

        //Toast.makeText(this, ""+price, Toast.LENGTH_SHORT).show();

        amount = String.valueOf(price);

    }

    private void placeOrder() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://"+ ip +"/water_supply/api/placeOrder.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (response.equals("success")){
                    Toast.makeText(BookingActivity.this, "Booked", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(BookingActivity.this,AddressActivity.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(BookingActivity.this, ""+response, Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(BookingActivity.this, ""+error, Toast.LENGTH_SHORT).show();

            }
        }){
            @Nullable
            @Override
            protected Map<String,String> getParams() throws AuthFailureError{
                Map<String,String> params = new HashMap<>();
                params.put("uid",uid);
                params.put("sid",sid);
                params.put("cans",canET.getText().toString());
                params.put("amount",amount);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void requiredCans() {

        String litres = litresET.getText().toString();

        int lit = Integer.valueOf(litres);

        int cans = lit / 20 ;

        String noofcans = String.valueOf(cans);

        canET.setText(noofcans);
        canET.setEnabled(false);
    }

}