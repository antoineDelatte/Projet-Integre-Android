package com.example.packvoyage.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

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

public class fragmentHomePackList extends Fragment implements PackListAdapter.OnPackListener {

    public static final String TAG = "HOME";

    @BindView(R.id.pack_display_rv)
    public RecyclerView rVPackList;
    @BindView(R.id.pack_list_nested_scrollview)
    public NestedScrollView nestedScrollView;
    @BindView(R.id.load_more_button)
    public Button load_more_packs;
    private RecyclerView.Adapter rVAdapter;

    private ArrayList<Pack>packs = new ArrayList<>();
    private int nbPacksRecentlyAdded;
    private int pageIndex;

    private PackDao packDao;
    private PackDetailVM packVM;

    private IMainActivity parent;

    public fragmentHomePackList(){ }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_home_pack_list, container, false);
        ButterKnife.bind(this, view);
        pageIndex = 0;
        packs = new ArrayList<>();
        packVM.setPacks(packs);
        packDao.loadPacks(packVM, pageIndex);

        packVM.getPacks().observe(getViewLifecycleOwner(), list -> {
            if(list.size() > 0){
                packs.addAll(list);
                nbPacksRecentlyAdded = list.size();
                updateRecyclerView();
            }
        });

        load_more_packs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                packDao.loadPacks(packVM, pageIndex);
            }
        });
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        packVM = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(PackDetailVM.class);
        packDao = SingletonDao.getPackDao();
    }

    private void updateRecyclerView(){
        Log.i("Trip4", Integer.toString(pageIndex));
        if(pageIndex == 0){
            rVPackList.setHasFixedSize(true);
            rVPackList.setLayoutManager(new LinearLayoutManager(getContext()));
            rVAdapter = new PackListAdapter(packs, getContext(), this);
            rVPackList.setAdapter(rVAdapter);
        }
        else{
            int packsSize = packs.size();
            for(int i = nbPacksRecentlyAdded-1; i >= 0; i--){
                rVAdapter.notifyItemInserted(packsSize-i);
                rVAdapter.notifyItemRangeChanged(packsSize-1-i, packsSize-i);
            }
            Toast.makeText(getContext(), Objects.requireNonNull(getContext()).getResources().getString(R.string.scroll_down_for_more), Toast.LENGTH_SHORT).show();
        }
        pageIndex++;
    }

    @Override
    public void onPackClick(int packId, String packName) {
        packVM.setSelectedPackId(packId);
        packVM.setSelectedPackName(packName);
        parent.changeFragment(fragmentHomePackDetails.TAG);
    }

    @Override
    public void onAttach(@NonNull Context context){
        super.onAttach(context);
        try{
            parent = (IMainActivity)context;
        }
        catch (ClassCastException e){
            e.printStackTrace();
        }
    }
}
