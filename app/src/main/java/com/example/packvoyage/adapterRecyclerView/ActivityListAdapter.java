package com.example.packvoyage.adapterRecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.packvoyage.R;
import com.example.packvoyage.model.Activity;

import java.util.ArrayList;

public class ActivityListAdapter extends RecyclerView.Adapter<ActivityListAdapter.ActivityHolder>{

    private ArrayList<Activity> activities;
    private Context context;

    public ActivityListAdapter(Context context, ArrayList<Activity> activityList){
        this.context = context;
        this.activities = activityList;
    }

    @NonNull
    @Override
    public ActivityHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.display_activities_recyclerview, parent, false);
        ActivityListAdapter.ActivityHolder holder = new ActivityListAdapter.ActivityHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ActivityHolder holder, int position) {
        Activity activity = this.activities.get(position);
        Glide.with(this.context).load(activity.getImage_url()).into(holder.activity_image);
        holder.activity_image.setClipToOutline(true);
        holder.activity_name.setText(activity.getName());
        holder.activity_price.setText(String.format("%.2f", activity.getPrice()) + " €");
        holder.activity_location.setText(activity.getLocation());
    }

    @Override
    public int getItemCount() {
        return this.activities == null ? 0 : activities.size();
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
    }
}
