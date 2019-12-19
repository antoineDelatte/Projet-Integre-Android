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
import com.example.packvoyage.adapterRecyclerView.CommentsAdapter;
import com.example.packvoyage.model.Comment;
import com.example.packvoyage.repository.PackDao;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class fragmentBookedPackDetails extends Fragment {

    public static final String TAG = "BOOKED_PACK_DETAILS";

    private PackDetailVM packVM;
    private PackDao packDao;

    @BindView(R.id.comments_rv)
    public RecyclerView commentsRV;

    public fragmentBookedPackDetails() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_booked_pack_details, container, false);
        ButterKnife.bind(this, view);

        packVM.getSelectedBookedPackComments().observe(getActivity(), comments -> {
            initRecyclerView(comments);
        });

        return view;
    }

    public void initRecyclerView(ArrayList<Comment> comments){
        commentsRV.setHasFixedSize(true);
        commentsRV.setLayoutManager(new LinearLayoutManager(getContext()));
        RecyclerView.Adapter rVAdapter = new CommentsAdapter(comments, getContext());
        commentsRV.setAdapter(rVAdapter);
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
