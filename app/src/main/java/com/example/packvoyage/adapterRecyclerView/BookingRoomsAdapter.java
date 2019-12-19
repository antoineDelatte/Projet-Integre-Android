package com.example.packvoyage.adapterRecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.packvoyage.R;
import com.example.packvoyage.model.BedRoom;

import java.util.ArrayList;

public class BookingRoomsAdapter extends RecyclerView.Adapter<BookingRoomsAdapter.BookingRoomHolder>{

    private ArrayList<BedRoom> rooms;
    private OnRoomCheckboxClickListener roomCheckboxListener;

    public BookingRoomsAdapter(ArrayList<BedRoom> rooms, OnRoomCheckboxClickListener roomCheckboxListener){
        this.rooms = rooms;
        this.roomCheckboxListener = roomCheckboxListener;
    }

    @NonNull
    @Override
    public BookingRoomHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rooms_booking_recyclerview, parent, false);
        BookingRoomsAdapter.BookingRoomHolder holder = new BookingRoomsAdapter.BookingRoomHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull BookingRoomHolder holder, int position) {
        BedRoom room = rooms.get(position);
        holder.room_size.setText(String.valueOf(room.getNbBeds()));
        holder.booking_room_price.setText(room.getFormattedPrice());
        holder.room_checkbox.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                roomCheckboxListener.onRoomCheckboxClickListener(room.getId(), room.getPrice(), holder.room_checkbox.isChecked());
            }
        });
    }

    @Override
    public int getItemCount() {
        return rooms == null ? 0 : rooms.size();
    }

    public interface OnRoomCheckboxClickListener{
        void onRoomCheckboxClickListener(int roomId, double roomPrice, boolean isCheckboxSelected);
    }

    public static class BookingRoomHolder extends RecyclerView.ViewHolder {

        private TextView room_size;
        private CheckBox room_checkbox;
        private TextView booking_room_price;

        public BookingRoomHolder(@NonNull View itemView) {
            super(itemView);
            room_size = itemView.findViewById(R.id.room_size);
            room_checkbox = itemView.findViewById(R.id.booking_room_checkbox);
            booking_room_price = itemView.findViewById(R.id.booking_room_price);
        }
    }
}
