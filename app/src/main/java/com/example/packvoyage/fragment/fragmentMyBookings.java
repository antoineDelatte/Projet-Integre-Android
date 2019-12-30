package com.example.packvoyage.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.packvoyage.R;
import com.example.packvoyage.Singleton.SingletonDao;
import com.example.packvoyage.ViewModel.PackDetailVM;
import com.example.packvoyage.activity.IMainActivity;
import com.example.packvoyage.adapterRecyclerView.PackListAdapter;
import com.example.packvoyage.model.Pack;
import com.example.packvoyage.repository.PackDao;

import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class fragmentMyBookings extends Fragment implements PackListAdapter.OnPackListener {

    private PackDao packDao;
    private PackDetailVM packVM;
    @BindView(R.id.my_booked_packs_rv)
    public RecyclerView rVPackList;
    private IMainActivity parent;

    public static final String TAG = "MY_BOOKINGS";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_my_bookings, container, false);
        ButterKnife.bind(this, view);

        packVM.getCurrentUserId().observe(getViewLifecycleOwner(), userId -> {
            packDao.loadMyBookings(packVM, userId, getContext());
        });

        packVM.getMyBookedPacks().observe(getViewLifecycleOwner(), list -> initRecyclerView(list));
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        packVM = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(PackDetailVM.class);
        packDao = SingletonDao.getPackDao();
    }

    public void initRecyclerView(ArrayList<Pack> packList){
        rVPackList.setHasFixedSize(true);
        rVPackList.setLayoutManager(new LinearLayoutManager(getContext()));
        RecyclerView.Adapter rVAdapter = new PackListAdapter(packList, getContext(), this);
        rVPackList.setAdapter(rVAdapter);
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        try{
            parent = (IMainActivity)context;
        }
        catch (ClassCastException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onPackClick(int packId, String packName) {
        packVM.setSelectedBookedPackId(packId);
        packVM.setSelectedBookedPackName(packName);
        parent.changeFragment(fragmentBookedPackDetails.TAG);
    }
}
