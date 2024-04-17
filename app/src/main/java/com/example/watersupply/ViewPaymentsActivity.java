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
import com.example.watersupply.Adapter.PaymentListAdapter;
import com.example.watersupply.ModelClass.OrdersListModelClass;
import com.example.watersupply.ModelClass.PaymentsListModelClass;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class ViewPaymentsActivity extends AppCompatActivity {

    private static String TAG ="ViewPayments";

    RecyclerView paymentsRV;
    ArrayList<PaymentsListModelClass> list;
    private GlobalPreference globalPreference;
    private String ip,uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_payments);

        globalPreference = new GlobalPreference(this);
        ip = globalPreference.getIp();
        uid = globalPreference.getID();

        //Toast.makeText(ViewPaymentsActivity.this, "servid"+uid, Toast.LENGTH_SHORT).show();

        paymentsRV = findViewById(R.id.paymentsRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        paymentsRV.setLayoutManager(layoutManager);

        getPaymentsList();
    }

    private void getPaymentsList() {
        list = new ArrayList<>();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://" + ip + "/water_supply/api/getPayments.php?uid="+uid, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if(response.equals("failed")){
                    Toast.makeText(ViewPaymentsActivity.this, "No Payments Available", Toast.LENGTH_SHORT).show();
                }
                else {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            String id = object.getString("id");
                            String username = object.getString("cardname");
                            String amount = object.getString("amount");
                            String date = object.getString("date");

                            list.add(new PaymentsListModelClass(id,username,amount,date));
                        }

                        PaymentListAdapter adapter = new PaymentListAdapter(list,ViewPaymentsActivity.this);
                        paymentsRV.setAdapter(adapter);

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

        RequestQueue requestQueue = Volley.newRequestQueue(ViewPaymentsActivity.this);
        requestQueue.add(stringRequest);
    }
}