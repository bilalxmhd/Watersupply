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
import com.example.watersupply.Adapter.MyOrdersAdapter;
import com.example.watersupply.ModelClass.BookingDetailsModelClass;
import com.example.watersupply.ModelClass.MyOrdersModelClass;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class MyOrdersActivity extends AppCompatActivity {

    private static String TAG ="MyOrdersActivity";

    RecyclerView myordersRV;
    ArrayList<MyOrdersModelClass> list;
    private GlobalPreference globalPreference;
    private String ip,uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_orders);

        globalPreference = new GlobalPreference(this);
        ip = globalPreference.getIp();
        uid = globalPreference.getID();

        myordersRV = findViewById(R.id.myOrdersRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        myordersRV.setLayoutManager(layoutManager);

        getOrdersList();
    }

    private void getOrdersList() {
        list = new ArrayList<>();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://" + ip + "/water_supply/api/myOrders.php?uid="+uid, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if(response.equals("failed")){
                    Toast.makeText(MyOrdersActivity.this, ""+response, Toast.LENGTH_SHORT).show();
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

                            list.add(new MyOrdersModelClass(id,sid,sname,quantity,amount,date));
                        }

                       MyOrdersAdapter adapter = new MyOrdersAdapter(list,MyOrdersActivity.this);
                       myordersRV.setAdapter(adapter);

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

        RequestQueue requestQueue = Volley.newRequestQueue(MyOrdersActivity.this);
        requestQueue.add(stringRequest);
    }
}