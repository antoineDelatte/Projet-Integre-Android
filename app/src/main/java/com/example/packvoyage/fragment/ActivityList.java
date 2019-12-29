package com.example.packvoyage.fragment;

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
import com.example.packvoyage.adapterRecyclerView.ActivityListAdapter;
import com.example.packvoyage.model.Pack;
import com.example.packvoyage.repository.PackDao;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ActivityList extends Fragment {

    @BindView(R.id.activity_list_rv)
    public RecyclerView activityList_rv;
    private PackDao packDao;
    private ActivityListAdapter rvAdapter;
    private Pack pack;
    private PackDetailVM packDetailVM;
    private int selectedPackId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        packDetailVM = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(PackDetailVM.class);
        packDetailVM.getSelectedPackId().observe(getViewLifecycleOwner(), packId -> selectedPackId = packId);
        View view =  inflater.inflate(R.layout.fragment_activity_list, container, false);
        ButterKnife.bind(this, view);
        packDao = SingletonDao.getPackDao();
        pack = packDao.getPackActivities(selectedPackId);
        // /!\ laisser cette ligne à la fin
        initRecyclerView();
        return view;
    }

    public ActivityList(){ }

    private void initRecyclerView(){
        rvAdapter = new ActivityListAdapter(getContext(), pack.getActivities());
        activityList_rv.setHasFixedSize(true);
        activityList_rv.setAdapter(rvAdapter);
        activityList_rv.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}