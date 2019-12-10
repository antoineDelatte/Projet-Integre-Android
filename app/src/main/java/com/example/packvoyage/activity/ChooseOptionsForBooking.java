package com.example.packvoyage.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.packvoyage.R;
import com.example.packvoyage.ViewModel.PackDetailVM;
import com.example.packvoyage.model.Flight;
import com.example.packvoyage.repository.PackDao;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChooseOptionsForBooking extends AppCompatActivity {

    @BindView(R.id.choose_options_for_booking_pack_name)
    public TextView packName;
    private int selectedPackId;
    private PackDao packDao;
    private PackDetailVM packDetailVM;
    private List<Flight>flightsAndSeats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_options_for_booking);
        ButterKnife.bind(this);
        packDetailVM = ViewModelProviders.of(this).get(PackDetailVM.class);
        packDetailVM.getSelectedPackName().observe(this, name -> packName.setText(name));
        packDetailVM.getSelectedPackId().observe(this, id -> selectedPackId = id);
        packDao = new PackDao();
        flightsAndSeats = packDao.getFlightsWithSeats(selectedPackId);
    }
}
