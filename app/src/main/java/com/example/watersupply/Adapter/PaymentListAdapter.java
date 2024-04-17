package com.example.watersupply.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.watersupply.GlobalPreference;
import com.example.watersupply.ModelClass.OrdersListModelClass;
import com.example.watersupply.ModelClass.PaymentsListModelClass;
import com.example.watersupply.R;

import java.util.ArrayList;

public class PaymentListAdapter extends RecyclerView.Adapter<PaymentListAdapter.MyViewHolder>  {

    ArrayList<PaymentsListModelClass> list;
    Context context;
    String ip;

    public PaymentListAdapter(ArrayList<PaymentsListModelClass> list, Context context){
        this.list = list;
        this.context = context;

        GlobalPreference globalPreference = new GlobalPreference(context);
        ip = globalPreference.getIp();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.raw_payment_list, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        PaymentsListModelClass paymentList = list.get(position);
        holder.pnameTV.setText("Card name:  "+paymentList.getUsername());
        holder.pamountTV.setText("Amount:  "+paymentList.getAmount());
        holder.pdateTV.setText("Date:  "+paymentList.getDate());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView pnameTV;
        TextView pamountTV;
        TextView pdateTV;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            pnameTV = itemView.findViewById(R.id.pNameTextView);
            pamountTV = itemView.findViewById(R.id.pAmountTextView);
            pdateTV = itemView.findViewById(R.id.pDateTextView);

        }
    }
}
