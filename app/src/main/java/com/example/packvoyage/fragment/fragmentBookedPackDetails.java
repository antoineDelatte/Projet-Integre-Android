package com.example.packvoyage.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import com.example.packvoyage.R;
import com.example.packvoyage.Singleton.SingletonDao;
import com.example.packvoyage.ViewModel.PackDetailVM;
import com.example.packvoyage.repository.PackDao;

public class fragmentBookedPackDetails extends Fragment {

    public static final String TAG = "BOOKED_PACK_DETAILS";

    private PackDetailVM packVM;
    private PackDao packDao;

    public fragmentBookedPackDetails() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_booked_pack_detail, container, false);

        packVM.getSelectedBookedPackComments().observe(getActivity(), comments -> {
            //initRecyclerView(comments); // todo creer init rv comments
        });

        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        packVM = ViewModelProviders.of(getActivity()).get(PackDetailVM.class);
        packDao = SingletonDao.getPackDao();
        packVM.getSelectedBookedPackId().observe(getActivity(), id -> {
            packDao.loadComments(packVM, id);
        });
    }
}
