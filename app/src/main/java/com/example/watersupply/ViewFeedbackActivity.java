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
import com.example.watersupply.Adapter.FeedbacksAdapter;
import com.example.watersupply.Adapter.PaymentListAdapter;
import com.example.watersupply.ModelClass.FeedbacksModelClass;
import com.example.watersupply.ModelClass.PaymentsListModelClass;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class ViewFeedbackActivity extends AppCompatActivity {

    private static String TAG ="ViewFeedback";

    RecyclerView feedbackRV;
    ArrayList<FeedbacksModelClass> list;
    private GlobalPreference globalPreference;
    private String ip,uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_feedback);

        globalPreference = new GlobalPreference(this);
        ip = globalPreference.getIp();
        uid = globalPreference.getID();

        feedbackRV = findViewById(R.id.feedbacksRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        feedbackRV.setLayoutManager(layoutManager);

        getFeedbacks();
    }

    private void getFeedbacks() {
        list = new ArrayList<>();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://" + ip + "/water_supply/api/getFeedbacks.php?uid="+uid, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if(response.equals("failed")){
                    Toast.makeText(ViewFeedbackActivity.this, "No Feedbacks Available"+response, Toast.LENGTH_SHORT).show();
                }
                else {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            String id = object.getString("id");
                            String username = object.getString("name");
                            String complaint = object.getString("complaint");
                            String feedback = object.getString("feedback");

                            list.add(new FeedbacksModelClass(id,username,feedback,complaint));
                        }

                        FeedbacksAdapter adapter = new FeedbacksAdapter(list,ViewFeedbackActivity.this);
                        feedbackRV.setAdapter(adapter);

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

        RequestQueue requestQueue = Volley.newRequestQueue(ViewFeedbackActivity.this);
        requestQueue.add(stringRequest);
    }
}