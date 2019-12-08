package com.example.packvoyage.activity;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.packvoyage.R;
import com.example.packvoyage.adapterRecyclerView.ActivityListAdapter;
import com.example.packvoyage.model.Pack;
import com.example.packvoyage.repository.PackDao;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ActivityList extends Fragment {

    @BindView(R.id.activity_list_rv)
    public RecyclerView activityList_rv;

    private PackDao packDao;
    private ActivityListAdapter rvAdapter;
    private Pack pack;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_activity_list, container, false);
        ButterKnife.bind(this, view);
        packDao = new PackDao();
        pack = packDao.getPackWithDescriptionAndActivities(1); // todo remplacer par bonne valeur
        // /!\ laisser cette ligne à la fin
        initRecyclerView();
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    private void initRecyclerView(){
        rvAdapter = new ActivityListAdapter(getContext(), pack.getActivities(), new ActivityListAdapter.OnItemClickListener() {
            @Override
            public void onClick(int id) {
                Toast.makeText(getContext(), "cliqué sur acti", Toast.LENGTH_LONG).show();
            }
        });
        activityList_rv.setHasFixedSize(true);
        activityList_rv.setAdapter(rvAdapter);
        activityList_rv.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}
