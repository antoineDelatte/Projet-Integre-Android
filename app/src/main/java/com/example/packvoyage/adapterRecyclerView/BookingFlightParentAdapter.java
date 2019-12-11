package com.example.packvoyage.adapterRecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.packvoyage.R;
import com.example.packvoyage.model.Flight;

import java.util.ArrayList;

public class BookingFlightParentAdapter extends RecyclerView.Adapter<BookingFlightParentAdapter.BookingFlightParentHolder> {

    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
    private ArrayList<Flight> flights;
    private BookingPlaneSeatsSelectionAdapter.OnSeatCheckboxClick onSeatCheckboxClickListener;

    public BookingFlightParentAdapter(ArrayList<Flight> flights, BookingPlaneSeatsSelectionAdapter.OnSeatCheckboxClick onSeatCheckboxClickListener){
        this.flights = flights;
        this.onSeatCheckboxClickListener = onSeatCheckboxClickListener;
    }

    @NonNull
    @Override
    public BookingFlightParentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.booking_flights_parent_recyclerview, parent, false);
        return new BookingFlightParentAdapter.BookingFlightParentHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookingFlightParentHolder holder, int position) {
        holder.flightInfo.setText(flights.get(position).getShortDescription());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(
                holder.planeSeatsRv.getContext(),
                LinearLayoutManager.VERTICAL,
                false
        );
        linearLayoutManager.setInitialPrefetchItemCount(flights.get(position).getPlaneSeats().size());
        BookingPlaneSeatsSelectionAdapter bookingPlaneSeatsAdapter = new BookingPlaneSeatsSelectionAdapter(flights.get(position).getPlaneSeats(), onSeatCheckboxClickListener);
        holder.planeSeatsRv.setLayoutManager(linearLayoutManager);
        holder.planeSeatsRv.setAdapter(bookingPlaneSeatsAdapter);
        holder.planeSeatsRv.setRecycledViewPool(viewPool);
    }

    @Override
    public int getItemCount() {
        return flights == null ? 0 : flights.size();
    }

    public static class BookingFlightParentHolder extends RecyclerView.ViewHolder {
        private TextView flightInfo;
        private RecyclerView planeSeatsRv;

        public BookingFlightParentHolder(@NonNull View itemView) {
            super(itemView);
            flightInfo = itemView.findViewById(R.id.flight_booking_flight_info);
            planeSeatsRv = itemView.findViewById(R.id.flight_booking_plane_seats_rv);
        }
    }
}
