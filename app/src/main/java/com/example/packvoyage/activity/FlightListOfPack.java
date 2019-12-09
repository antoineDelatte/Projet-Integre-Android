package com.example.packvoyage.activity;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.packvoyage.R;
import com.example.packvoyage.adapterRecyclerView.ActivityListAdapter;
import com.example.packvoyage.adapterRecyclerView.FlightListAdapter;
import com.example.packvoyage.model.Pack;
import com.example.packvoyage.repository.PackDao;

import butterknife.BindView;
import butterknife.ButterKnife;


public class FlightListOfPack extends Fragment {
    @BindView(R.id.flight_details_rv)
    public RecyclerView flightDetails_rv;
    private PackDao packDao;
    private FlightListAdapter flightListAdapter;
    private Pack pack;
    private int selectedPackId;

    public FlightListOfPack(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_flight_list_of_pack, container, false);
        ButterKnife.bind(this, view);
        packDao = new PackDao();
        pack = packDao.getPackWithGeneralFlightInfos(selectedPackId);
        initRecyclerView();
        return view;
    }

    private void initRecyclerView(){
        flightListAdapter = new FlightListAdapter(pack.getOutwardsFlights(), pack.getHomewardsFlights(), getContext());
        Log.i("Trip4Student", Integer.toString(pack.getOutwardsFlights().size()));
        flightDetails_rv.setHasFixedSize(true);
        flightDetails_rv.setAdapter(flightListAdapter);
        flightDetails_rv.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }
}
