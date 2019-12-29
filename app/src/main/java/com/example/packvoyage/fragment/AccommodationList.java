package com.example.packvoyage.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.packvoyage.R;
import com.example.packvoyage.Singleton.SingletonDao;
import com.example.packvoyage.ViewModel.PackDetailVM;
import com.example.packvoyage.adapterRecyclerView.AccommodationAdapter;
import com.example.packvoyage.model.Accommodation;
import com.example.packvoyage.repository.PackDao;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AccommodationList extends Fragment {

    private PackDetailVM packVM;
    private PackDao packDao;

    @BindView(R.id.accommodation_list_rv)
    public RecyclerView accommodation_rv;

    public AccommodationList() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        packDao = SingletonDao.getPackDao();
        packVM = ViewModelProviders.of(getActivity()).get(PackDetailVM.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_accommodation_list, container, false);
        ButterKnife.bind(this, v);

        packVM.getSelectedPackId().observe(getViewLifecycleOwner(), id -> {
            packDao.loadAccommodations(packVM, id);
        });

        packVM.getCurrentPackAccommodations().observe(getViewLifecycleOwner(), accommodations -> initRecyclerView(accommodations));
        return v;
    }

    private void initRecyclerView(ArrayList<Accommodation>accommodations){
        AccommodationAdapter accommodationAdapter = new AccommodationAdapter(getContext(), accommodations);
        accommodation_rv.setHasFixedSize(true);
        accommodation_rv.setAdapter(accommodationAdapter);
        accommodation_rv.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}
