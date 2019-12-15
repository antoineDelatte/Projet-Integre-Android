package com.example.packvoyage.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.packvoyage.R;
import com.example.packvoyage.Singleton.SingletonDao;
import com.example.packvoyage.ViewModel.PackDetailVM;
import com.example.packvoyage.adapterRecyclerView.PackListAdapter;
import com.example.packvoyage.model.Pack;
import com.example.packvoyage.repository.PackDao;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PackList extends AppCompatActivity implements PackListAdapter.OnPackListener{

    @BindView(R.id.pack_display_rv)
    public RecyclerView rVPackList;

    private PackDao packDao;
    private RecyclerView.Adapter rVadapter;
    private ArrayList<Pack> packList;
    private PackDetailVM packVM;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_packs);

        ButterKnife.bind(this);

        packVM = ViewModelProviders.of(this).get(PackDetailVM.class);
        packDao = SingletonDao.getPackDao();
        packDao.loadPacks(packVM);
        packVM.getPacks().observe(this, packs -> {
            initRecyclerView(packs);
        });
        /*packList = new ArrayList<>();
        packList.add(new Pack(1, "Voyage Combodge", null, "https://www.routesdumonde.com/wp-content/uploads/thumb/thumb-circuit-cambodge.jpg"));
        packList.add(new Pack(2, "Voyage Belgique", null, "https://media.routard.com/image/73/7/belgique-gand.1487737.c1000x300.jpg"));
        packList.add(new Pack(3, "Voyage Zambie", null, "https://img.ev.mu/images/portfolio/pays/245/600x400/846346.jpg"));
        packList.add(new Pack(4, "Voyage Bois de boulogne", null, "https://ak.jogurucdn.com/media/image/p25/place-2016-01-4-12-Boisdeboulogne2065e49fc359db8a638314b88f9f216d.jpg"));
        packList.add(new Pack(5, "Voyage Danemark", null, "https://live.staticflickr.com/1831/42367565350_b3577e9f9b_b.jpg"));*/
    }

    public void initRecyclerView(ArrayList<Pack>packList){
        rVPackList.setHasFixedSize(true);
        rVPackList.setLayoutManager(new LinearLayoutManager(this));
        rVadapter = new PackListAdapter(packList, getApplicationContext(), this);
        rVPackList.setAdapter(rVadapter);
    }

    @Override
    public void onPackClick(int packId, String packName) {
        Intent intent = new Intent(PackList.this, PackDetails.class);
        intent.putExtra("pack_id", packId);
        intent.putExtra("pack_name", packName);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_test, menu);
        return true;
    }
}
