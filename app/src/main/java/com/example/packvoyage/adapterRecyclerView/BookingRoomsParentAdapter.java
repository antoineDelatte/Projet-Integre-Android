package com.example.packvoyage.adapterRecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.packvoyage.R;
import com.example.packvoyage.model.Accommodation;

import java.util.ArrayList;

public class BookingRoomsParentAdapter extends RecyclerView.Adapter<BookingRoomsParentAdapter.BookingRoomHolder> {

    private RecyclerView.RecycledViewPool recycledViewPool = new RecyclerView.RecycledViewPool();
    private BookingRoomsAdapter.OnRoomCheckboxClickListener onRoomCheckboxClickListener;
    private ArrayList<Accommodation>accommodations;

    public BookingRoomsParentAdapter(BookingRoomsAdapter.OnRoomCheckboxClickListener onRoomCheckboxClickListener, ArrayList<Accommodation> accommodations) {
        this.onRoomCheckboxClickListener = onRoomCheckboxClickListener;
        this.accommodations = accommodations;
    }

    @NonNull
    @Override
    public BookingRoomHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.booking_rooms_parent_recyclerview, parent, false);
        return new BookingRoomsParentAdapter.BookingRoomHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookingRoomHolder holder, int position) {
        Accommodation accommodation = accommodations.get(position);
        holder.hotel_booking_info.setText(accommodation.getName());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(
                holder.rooms_booking_rv.getContext(),
                LinearLayoutManager.VERTICAL,
                false
        );
        linearLayoutManager.setInitialPrefetchItemCount(accommodation.getBedrooms().size());
        BookingRoomsAdapter bookingRoomsAdapter = new BookingRoomsAdapter(accommodation.getBedrooms(), onRoomCheckboxClickListener);
        holder.rooms_booking_rv.setLayoutManager(linearLayoutManager);
        holder.rooms_booking_rv.setAdapter(bookingRoomsAdapter);
        holder.rooms_booking_rv.setRecycledViewPool(recycledViewPool);
    }

    @Override
    public int getItemCount() {
        return accommodations == null ? 0 : accommodations.size();
    }

    public static class BookingRoomHolder extends RecyclerView.ViewHolder {
        private TextView hotel_booking_info;
        private RecyclerView rooms_booking_rv;

        public BookingRoomHolder(@NonNull View itemView) {
            super(itemView);
            hotel_booking_info = itemView.findViewById(R.id.hotel_booking_info);
            rooms_booking_rv = itemView.findViewById(R.id.rooms_booking_rv);
        }
    }
}
