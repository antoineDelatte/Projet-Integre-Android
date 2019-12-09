package com.example.packvoyage.adapterRecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.packvoyage.R;
import com.example.packvoyage.model.Activity;

import java.util.ArrayList;

public class ActivityListAdapter extends RecyclerView.Adapter<ActivityListAdapter.ActivityHolder>{

    private ArrayList<Activity> activities;
    private Context context;
    private final OnItemClickListener listener;

    public ActivityListAdapter(Context context, ArrayList<Activity> activityList, ActivityListAdapter.OnItemClickListener listener){
        this.context = context;
        this.activities = activityList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ActivityHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.display_activities_recyclerview, parent, false);
        v.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Toast.makeText(context, "aloha", Toast.LENGTH_SHORT).show();
                //todo
            }
        });
        ActivityListAdapter.ActivityHolder holder = new ActivityListAdapter.ActivityHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ActivityHolder holder, int position) {
        holder.bind(activities.get(position).getId(), listener);
        Glide.with(this.context).load(this.activities.get(position).getImage_url()).into(holder.activity_image);
        holder.activity_name.setText(activities.get(position).getName());
        holder.activity_price.setText(Double.toString(activities.get(position).getPrice()));
        holder.activity_location.setText(activities.get(position).getLocation());
    }

    @Override
    public int getItemCount() {
        return this.activities == null ? 0 : activities.size();
    }

    public interface OnItemClickListener {
        void onClick(int offerId);
    }

    public static class ActivityHolder extends RecyclerView.ViewHolder {
        private ImageView activity_image;
        private TextView activity_name;
        private TextView activity_price;
        private TextView activity_location;

        public ActivityHolder(@NonNull View itemView) {
            super(itemView);
            activity_image = itemView.findViewById(R.id.activities_recycler_activity_image);
            activity_name = itemView.findViewById(R.id.activities_recycler_activity_name);
            activity_price = itemView.findViewById(R.id.activities_recycler_activity_price);
            activity_location = itemView.findViewById(R.id.activities_recycler_activity_location);
        }

        public void bind(Integer id, OnItemClickListener listener){
            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    listener.onClick(id);
                }
            });
        }
    }
}
