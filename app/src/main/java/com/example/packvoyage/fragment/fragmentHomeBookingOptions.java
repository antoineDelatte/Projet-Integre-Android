package com.example.packvoyage.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.packvoyage.R;
import com.example.packvoyage.Singleton.SingletonDao;
import com.example.packvoyage.ViewModel.PackDetailVM;
import com.example.packvoyage.adapterRecyclerView.BookingActivitiesAdapter;
import com.example.packvoyage.adapterRecyclerView.BookingFlightParentAdapter;
import com.example.packvoyage.adapterRecyclerView.BookingPlaneSeatsSelectionAdapter;
import com.example.packvoyage.adapterRecyclerView.BookingRoomsAdapter;
import com.example.packvoyage.adapterRecyclerView.BookingRoomsParentAdapter;
import com.example.packvoyage.repository.PackDao;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class fragmentHomeBookingOptions extends Fragment implements BookingPlaneSeatsSelectionAdapter.OnSeatCheckboxClick
        , BookingActivitiesAdapter.OnActivityCheckboxClick, BookingRoomsAdapter.OnRoomCheckboxClickListener {

    public static final String TAG = "BOOKING_OPTIONS";

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
    private Integer numberOfTravelers = null;
    @BindView(R.id.book_paying_activities_rv)
    public RecyclerView book_paying_activities_rv;
    private Map<Integer, Double> selectedActivitiesWithPrice = new HashMap<>();
    private BookingActivitiesAdapter bookingActivitiesAdapter;
    private Map<Integer, Double> selectedRoomsWithPrice = new HashMap<>();
    private BookingRoomsParentAdapter bookingRoomsParentAdapter;
    private PackDetailVM packDetailVM;
    @BindView(R.id.book_housing_rooms_rv)
    public RecyclerView book_housing_rooms_rv;
    @BindView(R.id.add_to_my_bookings_button)
    public Button add_to_my_bookings;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_home_booking_options, container, false);
        ButterKnife.bind(this, view);

        packDetailVM.getSelectedPackId().observe(getViewLifecycleOwner(), id -> packId = id);
        packDetailVM.getSelectedPackName().observe(getViewLifecycleOwner(), name -> packName.setText(name));

        nbTravelers.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_DONE){
                    try{
                        numberOfTravelers =  Integer.parseInt(nbTravelers.getText().toString());
                        if(numberOfTravelers < 1){
                            Toast.makeText(getContext(), getResources().getString(R.string.booking_negative_nb_of_travelers_input), Toast.LENGTH_SHORT).show();
                            nbTravelers.setText("1");
                            numberOfTravelers = 1;
                        }
                    }
                    catch(Exception e){
                        Toast.makeText(getContext(), getResources().getString(R.string.booking_wrong_nb_of_travelers_input), Toast.LENGTH_SHORT).show();
                        nbTravelers.setText("1");
                        numberOfTravelers = 1;
                    }
                    InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(getContext().INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    return true;
                }
                return false;
            }
        });
        add_to_my_bookings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(numberOfTravelers == null){
                    Toast.makeText(getContext(), R.string.please_enter_a_number_of_traveler, Toast.LENGTH_SHORT).show();
                    return;
                }
                packDao.RegisterNewBooking(getKeys(selectedRoomsWithPrice), getKeys(selectedActivitiesWithPrice), getKeys(selectedSeatsWithPrice));
            }
        });
        initFlightBookingRV();
        initActivitiesBookingRV();
        initRoomsBookingRV();

        return view;
    }

    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        packDetailVM = ViewModelProviders.of(getActivity()).get(PackDetailVM.class);
        packDao = SingletonDao.getPackDao();
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
        book_plane_seats_rv.setLayoutManager(new LinearLayoutManager(getContext()));
    }
    private void initActivitiesBookingRV(){
        bookingActivitiesAdapter = new BookingActivitiesAdapter(packDao.getPayingActivities(packId), this);
        book_paying_activities_rv.setHasFixedSize(true);
        book_paying_activities_rv.setAdapter(bookingActivitiesAdapter);
        book_paying_activities_rv.setLayoutManager(new LinearLayoutManager(getContext()));
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
            selectedRoomsWithPrice.put(roomId, roomPrice);
        else
            selectedRoomsWithPrice.remove(roomId);
    }

    private void initRoomsBookingRV(){
        bookingRoomsParentAdapter = new BookingRoomsParentAdapter(this, packDao.getAccommodationsWithRooms(packId));
        book_housing_rooms_rv.setHasFixedSize(true);
        book_housing_rooms_rv.setAdapter(bookingRoomsParentAdapter);
        book_housing_rooms_rv.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private ArrayList<Integer>getKeys(Map<Integer, Double> hashMap){
        ArrayList<Integer>keys = new ArrayList<>();
        for(Integer key : hashMap.keySet()){
            keys.add(key);
        }
        return keys;
    }
}
