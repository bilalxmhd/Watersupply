package com.example.watersupply;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import com.example.watersupply.Adapter.SuppliersListAdapter;
import com.example.watersupply.ModelClass.SuppliersListModelClass;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SupplierActivity extends AppCompatActivity {

    private static String TAG ="SupplierActivity";

    EditText placeET;
    ImageView searchIV;

    RecyclerView supplierRV;
    ArrayList<SuppliersListModelClass> list;
    private String ip;
    private GlobalPreference globalPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supplier);

        globalPreference = new GlobalPreference(this);
        ip = globalPreference.getIp();

       // Toast.makeText(this, ""+ip, Toast.LENGTH_SHORT).show();

        placeET = findViewById(R.id.placeEditText);
        searchIV = findViewById(R.id.searchImageView);

        supplierRV = findViewById(R.id.suppliersListRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        supplierRV.setLayoutManager(layoutManager);

        getSuppliersList();

        searchIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(SupplierActivity.this, ""+placeET.getText().toString(), Toast.LENGTH_SHORT).show();
               searchSuppliers();
            }
        });
    }

    private void getSuppliersList() {

        list = new ArrayList<>();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://" + ip + "/water_supply/api/getSuppliers.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if(response.equals("failed")){
                    Toast.makeText(SupplierActivity.this, ""+response, Toast.LENGTH_SHORT).show();
                }
                else {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            String id = object.getString("id");
                            String name = object.getString("name");
                            String address = object.getString("address");
                            String number = object.getString("number");
                            String rating = object.getString("rating");

                            list.add(new SuppliersListModelClass(id,name,address,number,rating));
                        }

                        SuppliersListAdapter adapter = new SuppliersListAdapter(list,SupplierActivity.this);
                        supplierRV.setAdapter(adapter);

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

        RequestQueue requestQueue = Volley.newRequestQueue(SupplierActivity.this);
        requestQueue.add(stringRequest);
    }

    //search suppliers based on location

    private void searchSuppliers() {
        list = new ArrayList<>();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://" + ip + "/water_supply/api/searchSuppliers.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if(response.equals("failed")){
                    Toast.makeText(SupplierActivity.this, "No Suppliers Available", Toast.LENGTH_SHORT).show();

                    supplierRV.setVisibility(View.INVISIBLE);
                }
                else {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            String id = object.getString("id");
                            String name = object.getString("name");
                            String address = object.getString("address");
                            String number = object.getString("number");
                            String rating = object.getString("rating");

                            list.add(new SuppliersListModelClass(id,name,address,number,rating));
                        }

                        SuppliersListAdapter adapter = new SuppliersListAdapter(list,SupplierActivity.this);
                        supplierRV.setAdapter(adapter);

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
        }){
            @Override
            @Nullable
            protected Map<String,String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("place",placeET.getText().toString());
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(SupplierActivity.this);
        requestQueue.add(stringRequest);
    }
}