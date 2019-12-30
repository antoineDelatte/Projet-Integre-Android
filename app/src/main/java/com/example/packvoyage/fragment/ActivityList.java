package com.example.packvoyage.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.packvoyage.R;
import com.example.packvoyage.Singleton.SingletonDao;
import com.example.packvoyage.ViewModel.PackDetailVM;
import com.example.packvoyage.adapterRecyclerView.ActivityListAdapter;
import com.example.packvoyage.model.Activity;
import com.example.packvoyage.model.Pack;
import com.example.packvoyage.repository.PackDao;

import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ActivityList extends Fragment {

    @BindView(R.id.activity_list_rv)
    public RecyclerView activityList_rv;
    private ActivityListAdapter rvAdapter;
    private PackDetailVM packDetailVM;
    private PackDao packDao;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_activity_list, container, false);
        ButterKnife.bind(this, view);

        packDao = SingletonDao.getPackDao();

        packDetailVM.getSelectedPackId().observe(getViewLifecycleOwner(), packId -> {
            packDao.loadPackActivities(packDetailVM, packId, getContext());
        });

        packDetailVM.getCurrentPackActivities().observe(getViewLifecycleOwner(), this::initRecyclerView);

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        packDetailVM = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(PackDetailVM.class);
    }

    public ActivityList(){ }

    private void initRecyclerView(ArrayList<Activity> activities){
        rvAdapter = new ActivityListAdapter(getContext(), activities);
        activityList_rv.setHasFixedSize(true);
        activityList_rv.setAdapter(rvAdapter);
        activityList_rv.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}
