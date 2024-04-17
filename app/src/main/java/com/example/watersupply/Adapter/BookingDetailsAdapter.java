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
import com.example.watersupply.ModelClass.OrdersListModelClass;
import com.example.watersupply.PaymentActivity;
import com.example.watersupply.R;

import java.util.ArrayList;

public class BookingDetailsAdapter extends RecyclerView.Adapter<BookingDetailsAdapter.MyViewHolder>  {

    ArrayList<BookingDetailsModelClass> list;
    Context context;
    String ip;

    public BookingDetailsAdapter(ArrayList<BookingDetailsModelClass> list, Context context){
        this.list = list;
        this.context = context;

        GlobalPreference globalPreference = new GlobalPreference(context);
        ip = globalPreference.getIp();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.raw_booking_list, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookingDetailsAdapter.MyViewHolder holder, int position) {

        BookingDetailsModelClass bookingDetailsList = list.get(position);
        holder.bNameTV.setText("Supplier Name:  "+bookingDetailsList.getSname());
        holder.bQuantityTV.setText("Quantity:  "+bookingDetailsList.getQuantity());
        holder.bAmountTV.setText("Amount:  "+bookingDetailsList.getAmount());
        holder.bDateTV.setText("Date:  "+bookingDetailsList.getDate());

        holder.paymentBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PaymentActivity.class);
                intent.putExtra("id",bookingDetailsList.getId());
                intent.putExtra("amount",bookingDetailsList.getAmount());
                intent.putExtra("supplierId",bookingDetailsList.getSid());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

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
        Button paymentBT;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            bNameTV = itemView.findViewById(R.id.bNameTextView);
            bQuantityTV = itemView.findViewById(R.id.bQuantityTextView);
            bAmountTV = itemView.findViewById(R.id.bAmountTextView);
            bDateTV = itemView.findViewById(R.id.bDateTextView);
            paymentBT = itemView.findViewById(R.id.bPaymentButton);


        }
    }
}
