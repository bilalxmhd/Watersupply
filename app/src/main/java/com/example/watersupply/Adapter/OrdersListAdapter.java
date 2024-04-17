package com.example.watersupply.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.watersupply.GlobalPreference;
import com.example.watersupply.MapsActivity;
import com.example.watersupply.ModelClass.OrdersListModelClass;
import com.example.watersupply.ModelClass.SuppliersListModelClass;
import com.example.watersupply.R;

import java.util.ArrayList;

public class OrdersListAdapter extends RecyclerView.Adapter<OrdersListAdapter.MyViewHolder> {

    ArrayList<OrdersListModelClass> list;
    Context context;
    String ip;

    public OrdersListAdapter(ArrayList<OrdersListModelClass> list, Context context){
        this.list = list;
        this.context = context;

        GlobalPreference globalPreference = new GlobalPreference(context);
        ip = globalPreference.getIp();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.raw_orders_list, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrdersListAdapter.MyViewHolder holder, int position) {
        OrdersListModelClass orderList = list.get(position);
        holder.onameTV.setText("Username:  "+orderList.getName());
        holder.oquantityTV.setText("Quantity:  "+orderList.getQuantity());
        holder.odateTV.setText("Date:  "+orderList.getDate());

       holder.locateBT.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(context, MapsActivity.class);
               intent.putExtra("latitude",orderList.getLatitude());
               intent.putExtra("longitude",orderList.getLongitude());
               context.startActivity(intent);
           }
       });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView onameTV;
        TextView oquantityTV;
        TextView odateTV;
        Button locateBT;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            onameTV = itemView.findViewById(R.id.oNameTextView);
            oquantityTV = itemView.findViewById(R.id.oQuantityTextView);
            odateTV = itemView.findViewById(R.id.oDateTextView);
            locateBT = itemView.findViewById(R.id.locateButton);

        }
    }
}
