package com.example.packvoyage.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.packvoyage.R;
import com.example.packvoyage.Singleton.SingletonDao;
import com.example.packvoyage.ViewModel.PackDetailVM;
import com.example.packvoyage.activity.IMainActivity;
import com.example.packvoyage.adapterRecyclerView.PackListAdapter;
import com.example.packvoyage.model.Pack;
import com.example.packvoyage.repository.PackDao;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class fragmentHomePackList extends Fragment implements PackListAdapter.OnPackListener {

    public static final String TAG = "HOME";

    @BindView(R.id.pack_display_rv)
    public RecyclerView rVPackList;
    @BindView(R.id.pack_list_nested_scrollview)
    public NestedScrollView nestedScrollView;
    @BindView(R.id.loadingPanel)
    public RelativeLayout loadingPanel;
    private RecyclerView.Adapter rVAdapter;

    private ArrayList<Pack>packs = new ArrayList<>();
    private int nbPacksRecentlyAdded;
    private int pageIndex;
    private boolean requestBeingTreated = false;

    private PackDao packDao;
    private PackDetailVM packVM;

    private IMainActivity parent;

    public fragmentHomePackList(){ }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_home_pack_list, container, false);
        ButterKnife.bind(this, view);
        packVM.getPacks().observe(getActivity(), list -> {
            packs.addAll(list);
            nbPacksRecentlyAdded = list.size();
            updateRecyclerView();
        });
        nestedScrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

                int bottomDetector = rVPackList.getBottom() - (nestedScrollView.getHeight()-loadingPanel.getHeight() + scrollY);
                if(!requestBeingTreated && bottomDetector == 0){
                    requestBeingTreated = true;
                    packDao.loadPacks(packVM, pageIndex);
                }
            }
        });
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        packVM = ViewModelProviders.of(getActivity()).get(PackDetailVM.class);
        packDao = SingletonDao.getPackDao();
        pageIndex = 0;
        packDao.loadPacks(packVM, pageIndex);
    }

    public void updateRecyclerView(){
        if(pageIndex == 0){
            rVPackList.setHasFixedSize(true);
            rVPackList.setLayoutManager(new LinearLayoutManager(getContext()));
            rVAdapter = new PackListAdapter(packs, getContext(), this);
            rVPackList.setAdapter(rVAdapter);
        }
        else{
            // notifier changement dans la liste
            int packsSize = packs.size();
            for(int i = nbPacksRecentlyAdded-1; i >= 0; i--){
                rVAdapter.notifyItemInserted(packsSize-i);
                rVAdapter.notifyItemRangeChanged(packsSize-1-i, packsSize-i);
            }
        }
        requestBeingTreated = false;
        pageIndex++;
    }

    @Override
    public void onPackClick(int packId, String packName) {
        packVM.setSelectedPackId(packId);
        packVM.setSelectedPackName(packName);
        parent.changeFragment(fragmentHomePackDetails.TAG);
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
}
