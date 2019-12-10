/*package com.example.packvoyage.adapterRecyclerView;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.packvoyage.R;
import com.example.packvoyage.model.Flight;

import java.util.ArrayList;

public class FlightListAdapter extends RecyclerView.Adapter<FlightListAdapter.FlightHolder> {
    private ArrayList<Flight> flights;
    private Context context;

    public FlightListAdapter(ArrayList<Flight> flights, Context context){
        this.context = context;
    }

    @NonNull
    @Override
    public FlightListAdapter.FlightHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.display_flights_recyclerview, parent, false);
        return new FlightHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull FlightListAdapter.FlightHolder holder, int position) {
        if(position < outwardsFlights.size()){
            holder.outwardsInfo.setText(outwardsFlights.get(position).getFullDescription());
        }
        if(position < homewardsFlights.size()){
            holder.homewardsInfo.setText(homewardsFlights.get(position).getFullDescription());
        }
    }

    @Override
    public int getItemCount() {
        if(outwardsFlights == null)
            if(homewardsFlights == null)
                return 0;
            else
                return homewardsFlights.size();
        else
            if(homewardsFlights == null)
                return outwardsFlights.size();
            else
                return outwardsFlights.size() + homewardsFlights.size();
    }

    public static class FlightHolder extends RecyclerView.ViewHolder {
        private TextView homewardsInfo;
        private TextView outwardsInfo;

        public FlightHolder(@NonNull View itemView) {
            super(itemView);
            homewardsInfo = itemView.findViewById(R.id.flight_info_recycler_homeward);
            outwardsInfo = itemView.findViewById(R.id.flight_info_recycler_outward);
        }
    }
}
*/