package com.example.watersupply.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.watersupply.GlobalPreference;
import com.example.watersupply.ModelClass.BookingDetailsModelClass;
import com.example.watersupply.ModelClass.MyOrdersModelClass;
import com.example.watersupply.PaymentActivity;
import com.example.watersupply.R;

import java.util.ArrayList;

public class MyOrdersAdapter extends RecyclerView.Adapter<MyOrdersAdapter.MyViewHolder>  {

    ArrayList<MyOrdersModelClass> list;
    Context context;
    String ip;

    public MyOrdersAdapter(ArrayList<MyOrdersModelClass> list, Context context){
        this.list = list;
        this.context = context;

        GlobalPreference globalPreference = new GlobalPreference(context);
        ip = globalPreference.getIp();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.raw_my_orders, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        MyOrdersModelClass nyOrdersList = list.get(position);
        holder.bNameTV.setText("Supplier Name:  "+nyOrdersList.getSname());
        holder.bQuantityTV.setText("Quantity:  "+nyOrdersList.getQuantity());
        holder.bAmountTV.setText("Amount:  "+nyOrdersList.getAmount());
        holder.bDateTV.setText("Date:  "+nyOrdersList.getDate());


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView bNameTV;
        TextView bQuantityTV;
        TextView bAmountTV;
        TextView bDateTV;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            bNameTV = itemView.findViewById(R.id.mNameTextView);
            bQuantityTV = itemView.findViewById(R.id.mQuantityTextView);
            bAmountTV = itemView.findViewById(R.id.mAmountTextView);
            bDateTV = itemView.findViewById(R.id.mDateTextView);


        }
    }
}
