package com.example.packvoyage.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.packvoyage.R;
import com.example.packvoyage.adapterRecyclerView.BookingActivitiesAdapter;
import com.example.packvoyage.adapterRecyclerView.BookingFlightParentAdapter;
import com.example.packvoyage.adapterRecyclerView.BookingPlaneSeatsSelectionAdapter;
import com.example.packvoyage.adapterRecyclerView.BookingRoomsAdapter;
import com.example.packvoyage.adapterRecyclerView.BookingRoomsParentAdapter;
import com.example.packvoyage.repository.PackDao;
import com.google.android.material.textfield.TextInputEditText;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChooseOptionsForBooking extends AppCompatActivity implements BookingPlaneSeatsSelectionAdapter.OnSeatCheckboxClick
        , BookingActivitiesAdapter.OnActivityCheckboxClick, BookingRoomsAdapter.OnRoomCheckboxClickListener {

    @BindView(R.id.choose_options_for_booking_pack_name)
    public TextView packName;
    private PackDao packDao;
    private int packId;
    private Map<Integer, Double> selectedSeatsWithPrice = new HashMap<>();
    @BindView(R.id.book_plane_seats_rv)
    public RecyclerView book_plane_seats_rv;
    private BookingFlightParentAdapter bookingPlaneSeatsAdapter;
    @BindView(R.id.booking_options_nb_travelers)
    public TextInputEditText nbTravelers;
    private Integer numberOfTravelers = 1;
    @BindView(R.id.book_paying_activities_rv)
    public RecyclerView book_paying_activities_rv;
    private Map<Integer, Double> selectedActivitiesWithPrice = new HashMap<>();
    private BookingActivitiesAdapter bookingActivitiesAdapter;
    private Map<Integer, Double> selectedRoomsWithPrice = new HashMap<>();
    private BookingRoomsParentAdapter bookingRoomsParentAdapter;
    @BindView(R.id.book_housing_rooms_rv)
    public RecyclerView book_housing_rooms_rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_options_for_booking);
        Intent intent = getIntent();
        ButterKnife.bind(this);
        packName.setText(intent.getStringExtra("pack_name"));
        packId = intent.getIntExtra("pack_id", 1);
        packDao = new PackDao();
        nbTravelers.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_DONE){
                    try{
                        numberOfTravelers =  Integer.parseInt(nbTravelers.getText().toString());
                        if(numberOfTravelers < 1){
                            Toast.makeText(getApplicationContext(), getResources().getString(R.string.booking_negative_nb_of_travelers_input), Toast.LENGTH_SHORT).show();
                            nbTravelers.setText("1");
                        }
                    }
                    catch(Exception e){
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.booking_wrong_nb_of_travelers_input), Toast.LENGTH_SHORT).show();
                        nbTravelers.setText("1");
                    }
                    InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(getApplicationContext().INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    return true;
                }
                return false;
            }
        });
        initFlightBookingRV();
        initActivitiesBookingRV();
        initRoomsBookingRV();
    }

    @Override
    public void onSeatCheckBoxClick(int seatId, double seatPrice, boolean isCheckboxSelected) {
        if(isCheckboxSelected)
            selectedSeatsWithPrice.put(seatId, seatPrice);
        else
            selectedSeatsWithPrice.remove(seatId);
    }

    private void initFlightBookingRV(){
        bookingPlaneSeatsAdapter = new BookingFlightParentAdapter(packDao.getFlightsWithAirportAndSeats(packId), this);
        book_plane_seats_rv.setHasFixedSize(true);
        book_plane_seats_rv.setAdapter(bookingPlaneSeatsAdapter);
        book_plane_seats_rv.setLayoutManager(new LinearLayoutManager(this));
    }
    private void initActivitiesBookingRV(){
        bookingActivitiesAdapter = new BookingActivitiesAdapter(packDao.getPayingActivities(packId), this);
        book_paying_activities_rv.setHasFixedSize(true);
        book_paying_activities_rv.setAdapter(bookingActivitiesAdapter);
        book_paying_activities_rv.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void OnActivityCheckboxClick(int activityId, double activityPrice, boolean isCheckboxSelected) {
        if(isCheckboxSelected)
            selectedActivitiesWithPrice.put(activityId, activityPrice);
        else
            selectedActivitiesWithPrice.remove(activityId);
    }

    @Override
    public void onRoomCheckboxClickListener(int roomId, double roomPrice, boolean isCheckboxSelected) {
        if(isCheckboxSelected)
            selectedActivitiesWithPrice.put(roomId, roomPrice);
        else
            selectedActivitiesWithPrice.remove(roomId);
    }

    private void initRoomsBookingRV(){
        bookingRoomsParentAdapter = new BookingRoomsParentAdapter(this, packDao.getAccommodationsWithRooms(packId));
        book_housing_rooms_rv.setHasFixedSize(true);
        book_housing_rooms_rv.setAdapter(bookingRoomsParentAdapter);
        book_housing_rooms_rv.setLayoutManager(new LinearLayoutManager(this));
    }
}
