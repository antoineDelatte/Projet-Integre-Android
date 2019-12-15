package com.example.packvoyage.adapterRecyclerView;

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
    private boolean isOutwardFlights;

    public FlightListAdapter(ArrayList<Flight> flights){
        this.flights = flights;
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
        Flight flight = flights.get(position);
        holder.flight_departure_and_destination.setText(flight.getArrivalAndDestinationTitle());
        holder.departure_flight_info.setText(flight.getDepartureInfo());
        holder.arrival_flight_info.setText(flight.getArrivalInfo());
    }

    @Override
    public int getItemCount() { return flights == null ? 0 : flights.size();}

    public static class FlightHolder extends RecyclerView.ViewHolder {
        private TextView flight_departure_and_destination;
        private TextView departure_flight_info;
        private TextView arrival_flight_info;


        public FlightHolder(@NonNull View itemView) {
            super(itemView);
            flight_departure_and_destination = itemView.findViewById(R.id.flight_departure_and_destination);
            departure_flight_info = itemView.findViewById(R.id.departure_flight_info);
            arrival_flight_info = itemView.findViewById(R.id.arrival_flight_info);
        }
    }
}