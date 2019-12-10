package com.example.packvoyage.activity;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.packvoyage.R;
import com.example.packvoyage.model.Flight;
import com.example.packvoyage.model.Pack;
import com.example.packvoyage.repository.PackDao;

import butterknife.BindView;
import butterknife.ButterKnife;


public class FlightListOfPack extends Fragment {
    //@BindView(R.id.flight_details_rv)
    //public RecyclerView flightDetails_rv;
    private PackDao packDao;
    //private FlightListAdapter flightListAdapter;
    private Pack pack;
    private int selectedPackId;
    @BindView(R.id.fragment_flight_details_outward_journey_details)
    public TextView outwardTextInfo;
    @BindView(R.id.fragment_flight_details_homeward_journey_details)
    public TextView homewardTextInfo;

    public FlightListOfPack(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_flight_list_of_pack, container, false);
        ButterKnife.bind(this, view);
        packDao = new PackDao();
        pack = packDao.getPackWithGeneralFlightInfos(selectedPackId);
        //initRecyclerView();
        setFlightInfoText();
        return view;
    }

    /*private void initRecyclerView(){
        flightListAdapter = new FlightListAdapter(pack.getFlights(), getContext());
        Log.i("Trip4Student", Integer.toString(pack.getOutwardsFlights().size()));
        flightDetails_rv.setHasFixedSize(true);
        flightDetails_rv.setAdapter(flightListAdapter);
        flightDetails_rv.setLayoutManager(new LinearLayoutManager(getContext()));
    }*/

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
        Log.i("Trip4Student", homewardTextInfo.toString());
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }
}
