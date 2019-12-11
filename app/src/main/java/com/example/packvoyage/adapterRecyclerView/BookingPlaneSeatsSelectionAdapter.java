package com.example.packvoyage.adapterRecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.packvoyage.R;
import com.example.packvoyage.model.PlaneSeat;

import java.util.ArrayList;

public class BookingPlaneSeatsSelectionAdapter extends RecyclerView.Adapter<BookingPlaneSeatsSelectionAdapter.PlaneSeatSelectionHolder> {

    private ArrayList<PlaneSeat>planeSeats;
    private OnSeatCheckboxClick listener;

    public BookingPlaneSeatsSelectionAdapter(ArrayList<PlaneSeat>planeSeats, OnSeatCheckboxClick listener){
        this.planeSeats = planeSeats;
        this.listener = listener;
    }

    @NonNull
    @Override
    public PlaneSeatSelectionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.plane_seats_booking_recyclerview, parent, false);
        BookingPlaneSeatsSelectionAdapter.PlaneSeatSelectionHolder holder = new BookingPlaneSeatsSelectionAdapter.PlaneSeatSelectionHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull PlaneSeatSelectionHolder holder, int position) {
        holder.seatDescription.setText(this.planeSeats.get(position).toString());
        holder.seatCheckbox.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                PlaneSeat planeSeat = planeSeats.get(position);
                listener.onSeatCheckBoxClick(planeSeat.getId(), planeSeat.getPrice(), holder.seatCheckbox.isChecked());
            }
        });
    }

    @Override
    public int getItemCount() { return this.planeSeats == null ? 0 : planeSeats.size(); }

    public interface OnSeatCheckboxClick{
        void onSeatCheckBoxClick(int seatId, double seatPrice, boolean isCheckboxSelected);
    }

    public static class PlaneSeatSelectionHolder extends  RecyclerView.ViewHolder {
        private TextView seatDescription;
        private CheckBox seatCheckbox;

        public PlaneSeatSelectionHolder(@NonNull View itemView) {
            super(itemView);
            seatDescription = itemView.findViewById(R.id.booking_plane_seat_info);
            seatCheckbox = itemView.findViewById(R.id.booking_plane_seat_checkbox);
        }
    }
}
