package com.example.packvoyage.activity;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.packvoyage.R;


public class FlightListOfPack extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_flight_list_of_pack, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }
}
