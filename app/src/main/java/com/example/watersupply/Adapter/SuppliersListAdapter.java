package com.example.watersupply.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.watersupply.BookingActivity;
import com.example.watersupply.GlobalPreference;
import com.example.watersupply.ModelClass.SuppliersListModelClass;
import com.example.watersupply.R;
import com.example.watersupply.SplashActivity;

import java.util.ArrayList;

public class SuppliersListAdapter extends RecyclerView.Adapter<SuppliersListAdapter.MyViewHolder> {

    ArrayList<SuppliersListModelClass> list;
    Context context;
    String ip;

    public SuppliersListAdapter(ArrayList<SuppliersListModelClass> list, Context context){
        this.list = list;
        this.context = context;

        GlobalPreference globalPreference = new GlobalPreference(context);
        ip = globalPreference.getIp();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.raw_supplier_list, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SuppliersListAdapter.MyViewHolder holder, int position) {
        SuppliersListModelClass supplierList = list.get(position);
        holder.snameTV.setText(supplierList.getName());
        holder.saddressTV.setText(supplierList.getAddress());
        holder.snumberTV.setText(supplierList.getNumber());
        holder.ratingBar.setRating(Float.valueOf(supplierList.getRating()));

        holder.bookBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, BookingActivity.class);
                intent.putExtra("id",supplierList.getId());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

        holder.callIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String number = supplierList.getNumber();

                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:"+number));
                context.startActivity(callIntent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

         TextView snameTV;
         TextView saddressTV;
         TextView snumberTV;
         Button bookBT;
         LinearLayout bookDetailsLL;
         ImageView callIV;

         RatingBar ratingBar;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            snameTV = itemView.findViewById(R.id.snameTextView);
            saddressTV = itemView.findViewById(R.id.saddressTextView);
            snumberTV = itemView.findViewById(R.id.snumberTextView);
            bookBT = itemView.findViewById(R.id.bookButton);
            bookDetailsLL = itemView.findViewById(R.id.bookDetailsLinearLayout);
            callIV = itemView.findViewById(R.id.callImageView);
            ratingBar = itemView.findViewById(R.id.supplierRatingBar);




        }
    }
}
