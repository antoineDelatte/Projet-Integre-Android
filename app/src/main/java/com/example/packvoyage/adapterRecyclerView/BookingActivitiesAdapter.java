package com.example.packvoyage.adapterRecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.packvoyage.R;
import com.example.packvoyage.model.Activity;

import java.util.ArrayList;

public class BookingActivitiesAdapter extends RecyclerView.Adapter<BookingActivitiesAdapter.BookingActivityHolder>{

    private ArrayList<Activity> payingActivities;
    private OnActivityCheckboxClick checkboxListener;

    public BookingActivitiesAdapter(ArrayList<Activity> payingActivities, OnActivityCheckboxClick checkboxListener){
        this.payingActivities = payingActivities;
        this.checkboxListener = checkboxListener;
    }

    @NonNull
    @Override
    public BookingActivityHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.booking_activities_recyclerview, parent, false);
        BookingActivityHolder holder = new BookingActivityHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull BookingActivityHolder holder, int position) {
        holder.activity_name.setText(payingActivities.get(position).getShortDescription());
        holder.activity_checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity activity = payingActivities.get(position);
                checkboxListener.OnActivityCheckboxClick(activity.getId(), activity.getPrice(), holder.activity_checkbox.isSelected());
            }
        });
    }

    @Override
    public int getItemCount() {
        return payingActivities == null ? 0 : payingActivities.size();
    }

    public interface OnActivityCheckboxClick {
        void OnActivityCheckboxClick(int activityId, double activityPrice, boolean isCheckboxSelected);
    }

    public static class BookingActivityHolder extends RecyclerView.ViewHolder {

        private TextView activity_name;
        private CheckBox activity_checkbox;

        public BookingActivityHolder(@NonNull View itemView) {
            super(itemView);
            activity_name = itemView.findViewById(R.id.booking_activities_activity_info);
            activity_checkbox = itemView.findViewById(R.id.booking_activities_activity_checkbox);
        }
    }
}
