package com.example.watersupply.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.watersupply.GlobalPreference;
import com.example.watersupply.ModelClass.FeedbacksModelClass;
import com.example.watersupply.ModelClass.PaymentsListModelClass;
import com.example.watersupply.R;

import java.util.ArrayList;

public class FeedbacksAdapter extends RecyclerView.Adapter<FeedbacksAdapter.MyViewHolder>  {

    ArrayList<FeedbacksModelClass> list;
    Context context;
    String ip;

    public FeedbacksAdapter(ArrayList<FeedbacksModelClass> list, Context context){
        this.list = list;
        this.context = context;

        GlobalPreference globalPreference = new GlobalPreference(context);
        ip = globalPreference.getIp();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.raw_feedback_list, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        FeedbacksModelClass feedbackList = list.get(position);
        holder.fnameTV.setText("User name:  "+feedbackList.getUsername());
        holder.fFeedbackTV.setText("Feedback:  "+feedbackList.getFeedback());
        holder.fComplaintTV.setText("Complaint:  "+feedbackList.getComplaint());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView fnameTV;
        TextView fFeedbackTV;
        TextView fComplaintTV;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            fnameTV = itemView.findViewById(R.id.fNameTextView);
            fFeedbackTV = itemView.findViewById(R.id.fFeedbackTextView);
            fComplaintTV = itemView.findViewById(R.id.fComplaintTextView);

        }
    }
}
