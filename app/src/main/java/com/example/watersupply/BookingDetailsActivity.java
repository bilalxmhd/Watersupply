package com.example.watersupply;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.watersupply.Adapter.BookingDetailsAdapter;
import com.example.watersupply.Adapter.OrdersListAdapter;
import com.example.watersupply.ModelClass.BookingDetailsModelClass;
import com.example.watersupply.ModelClass.OrdersListModelClass;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class BookingDetailsActivity extends AppCompatActivity {

    private static String TAG ="BookingDetailsActivity";

    RecyclerView bookingRV;
    ArrayList<BookingDetailsModelClass> list;
    private GlobalPreference globalPreference;
    private String ip,uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_details);

        globalPreference = new GlobalPreference(this);
        ip = globalPreference.getIp();
        uid = globalPreference.getID();

        bookingRV = findViewById(R.id.bookingDetailsRV);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        bookingRV.setLayoutManager(layoutManager);

        getBookingDetails();

    }

    private void getBookingDetails() {
        list = new ArrayList<>();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://" + ip + "/water_supply/api/getBookingDetails.php?uid="+uid, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if(response.equals("failed")){
                    Toast.makeText(BookingDetailsActivity.this, ""+response, Toast.LENGTH_SHORT).show();
                }
                else {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            String id = object.getString("id");
                            String sid = object.getString("supplier_id");
                            String sname = object.getString("name");
                            String quantity = object.getString("quantity");
                            String amount = object.getString("amount");
                            String date = object.getString("date");

                            list.add(new BookingDetailsModelClass(id,sid,sname,quantity,amount,date));
                        }

                        BookingDetailsAdapter adapter = new BookingDetailsAdapter(list,BookingDetailsActivity.this);
                        bookingRV.setAdapter(adapter);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onResponse: "+error);
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(BookingDetailsActivity.this);
        requestQueue.add(stringRequest);
    }
}