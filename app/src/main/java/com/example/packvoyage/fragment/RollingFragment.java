package com.example.packvoyage.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.packvoyage.R;

public class RollingFragment extends Fragment {
    public static final String TAG = "ROLLING_FRAGMENT";

    public RollingFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_rolling, container, false);
    }
}
