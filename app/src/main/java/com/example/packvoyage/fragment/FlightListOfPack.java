package com.example.packvoyage.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.packvoyage.R;
import com.example.packvoyage.Singleton.SingletonDao;
import com.example.packvoyage.ViewModel.PackDetailVM;
import com.example.packvoyage.adapterRecyclerView.FlightListAdapter;
import com.example.packvoyage.model.Pack;
import com.example.packvoyage.repository.PackDao;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;


public class FlightListOfPack extends Fragment {
    private PackDao packDao;
    private Pack pack;
    private int selectedPackId;
    private PackDetailVM packDetailVM;
    @BindView(R.id.outward_flight_details_rv)
    public RecyclerView outward_journey;
    @BindView(R.id.homeward_flight_details_rv)
    public RecyclerView homeward_journey;
    private FlightListAdapter flightAdapter;

    public FlightListOfPack(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_flight_list_of_pack, container, false);
        ButterKnife.bind(this, view);
        packDetailVM = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(PackDetailVM.class);
        packDetailVM.getSelectedPackId().observe(getViewLifecycleOwner(), packId -> selectedPackId = packId);
        packDao = SingletonDao.getPackDao();
        pack = packDao.getPackWithGeneralFlightInfos(selectedPackId);
        initOutwardFlightsRV();
        initHomewardFlightsRV();
        return view;
    }

    private void initOutwardFlightsRV(){
        flightAdapter = new FlightListAdapter(pack.getOutwardsFlights());
        outward_journey.setHasFixedSize(true);
        outward_journey.setAdapter(flightAdapter);
        outward_journey.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void initHomewardFlightsRV(){
        flightAdapter = new FlightListAdapter(pack.getOutwardsFlights());
        homeward_journey.setHasFixedSize(true);
        homeward_journey.setAdapter(flightAdapter);
        homeward_journey.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }
}
