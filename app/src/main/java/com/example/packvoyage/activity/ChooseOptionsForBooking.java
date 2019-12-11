package com.example.packvoyage.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.packvoyage.R;
import com.example.packvoyage.ViewModel.PackDetailVM;
import com.example.packvoyage.adapterRecyclerView.BookingFlightParentAdapter;
import com.example.packvoyage.adapterRecyclerView.BookingPlaneSeatsSelectionAdapter;
import com.example.packvoyage.model.Flight;
import com.example.packvoyage.repository.PackDao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChooseOptionsForBooking extends AppCompatActivity implements BookingPlaneSeatsSelectionAdapter.OnSeatCheckboxClick {

    @BindView(R.id.choose_options_for_booking_pack_name)
    public TextView packName;
    private int selectedPackId;
    private PackDao packDao;
    private PackDetailVM packDetailVM;
    private ArrayList<Flight> flightsAndSeats;
    private int packId;
    private Map<Integer, Double> selectedSeatsWithPrice = new HashMap<>();
    @BindView(R.id.book_plane_seats_rv)
    public RecyclerView book_plane_seats_rv;
    private BookingFlightParentAdapter bookingPlaneSeatsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_options_for_booking);
        Intent intent = getIntent();
        ButterKnife.bind(this);
        packName.setText(intent.getStringExtra("pack_name"));
        packId = intent.getIntExtra("pack_id", 1);
        packDetailVM = ViewModelProviders.of(this).get(PackDetailVM.class);
        packDetailVM.getSelectedPackName().observe(this, name -> packName.setText(name));
        packDetailVM.getSelectedPackId().observe(this, id -> selectedPackId = id);
        packDao = new PackDao();
        flightsAndSeats = packDao.getFlightsWithAirportAndSeats(selectedPackId);
        initFlightBookingRV();
    }

    @Override
    public void onSeatCheckBoxClick(int seatId, double seatPrice, boolean isCheckboxSelected) {
        if(isCheckboxSelected)
            selectedSeatsWithPrice.put(seatId, seatPrice);
        else
            selectedSeatsWithPrice.remove(seatId);
        Log.i("Trip4", Integer.toString(selectedSeatsWithPrice.size()));
    }

    public void initFlightBookingRV(){
        bookingPlaneSeatsAdapter = new BookingFlightParentAdapter(packDao.getFlightsWithAirportAndSeats(packId), this);
        book_plane_seats_rv.setHasFixedSize(true);
        book_plane_seats_rv.setAdapter(bookingPlaneSeatsAdapter);
        book_plane_seats_rv.setLayoutManager(new LinearLayoutManager(this));
    }
}
