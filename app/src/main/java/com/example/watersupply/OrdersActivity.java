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
import com.example.watersupply.Adapter.OrdersListAdapter;
import com.example.watersupply.Adapter.SuppliersListAdapter;
import com.example.watersupply.ModelClass.OrdersListModelClass;
import com.example.watersupply.ModelClass.SuppliersListModelClass;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class OrdersActivity extends AppCompatActivity {

    private static String TAG ="OrdersActivity";

    RecyclerView orderRV;
    ArrayList<OrdersListModelClass> list;
    private GlobalPreference globalPreference;
    private String ip,uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);

        globalPreference = new GlobalPreference(this);
        ip = globalPreference.getIp();
        uid = globalPreference.getID();

        orderRV = findViewById(R.id.ordersRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        orderRV.setLayoutManager(layoutManager);

        getOrdersList();
    }

    private void getOrdersList() {
        list = new ArrayList<>();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://" + ip + "/water_supply/api/getOrders.php?uid="+uid, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if(response.equals("failed")){
                    Toast.makeText(OrdersActivity.this, ""+response, Toast.LENGTH_SHORT).show();
                }
                else {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            String id = object.getString("id");
                            String name = object.getString("name");
                            String quantity = object.getString("quantity");
                            String date = object.getString("date");
                            String latitude = object.getString("latitude");
                            String longitude = object.getString("longitude");

                            list.add(new OrdersListModelClass(id,name,quantity,date,latitude,longitude));
                        }

                        OrdersListAdapter adapter = new OrdersListAdapter(list,OrdersActivity.this);
                        orderRV.setAdapter(adapter);

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

        RequestQueue requestQueue = Volley.newRequestQueue(OrdersActivity.this);
        requestQueue.add(stringRequest);
    }
}