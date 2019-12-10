package com.example.packvoyage.activity;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.packvoyage.R;
import com.example.packvoyage.ViewModel.PackDetailVM;
import com.example.packvoyage.model.Flight;
import com.example.packvoyage.model.Pack;
import com.example.packvoyage.repository.PackDao;

import butterknife.BindView;
import butterknife.ButterKnife;


public class FlightListOfPack extends Fragment {
    private PackDao packDao;
    private Pack pack;
    private int selectedPackId;
    @BindView(R.id.fragment_flight_details_outward_journey_details)
    public TextView outwardTextInfo;
    @BindView(R.id.fragment_flight_details_homeward_journey_details)
    public TextView homewardTextInfo;
    private PackDetailVM packDetailVM;

    public FlightListOfPack(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_flight_list_of_pack, container, false);
        ButterKnife.bind(this, view);
        packDetailVM = ViewModelProviders.of(this).get(PackDetailVM.class);
        packDetailVM.getSelectedPackId().observe(this, packId -> selectedPackId = packId);
        packDao = new PackDao();
        pack = packDao.getPackWithGeneralFlightInfos(selectedPackId);
        setFlightInfoText();
        return view;
    }

    private void setFlightInfoText(){
        StringBuilder outwardInfo = new StringBuilder();
        StringBuilder homewardInfo = new StringBuilder();
        for(Flight flight: pack.getOutwardsFlights()){
            outwardInfo.append("\n" + flight.getFullDescription());
        }
        for(Flight flight : pack.getHomewardsFlights()){
            homewardInfo.append("\n" + flight.getFullDescription());
        }
        outwardTextInfo.setText(outwardInfo);
        homewardTextInfo.setText(homewardInfo);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }
}
