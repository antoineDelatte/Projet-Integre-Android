package com.example.packvoyage.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.packvoyage.R;
import com.example.packvoyage.adapterRecyclerView.PackListAdapter;
import com.example.packvoyage.model.Pack;
import com.example.packvoyage.repository.PackDao;

import java.util.ArrayList;

public class PackList extends AppCompatActivity {

    PackDao packDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_packs);

        packDao = new PackDao();
        ArrayList<Pack> packList = packDao.getPacks();
        RecyclerView.Adapter adapter = new PackListAdapter(packList);
    }
}
