package com.example.packvoyage.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.packvoyage.R;
import com.example.packvoyage.adapterRecyclerView.PackListAdapter;
import com.example.packvoyage.model.Pack;
import com.example.packvoyage.repository.PackDao;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PackList extends AppCompatActivity {

    @BindView(R.id.pack_display_rv)
    public RecyclerView rVPackList;

    private PackDao packDao;
    private RecyclerView.Adapter rVadapter;
    private RecyclerView.LayoutManager rVLayout;
    private ArrayList<Pack> packList;
    private int packId;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_packs);

        ButterKnife.bind(this);

        packDao = new PackDao();
        //packList = packDao.getPacks();
        packList = new ArrayList<>();
        packList.add(new Pack(1, "Voyage Combodge", null, "https://www.routesdumonde.com/wp-content/uploads/thumb/thumb-circuit-cambodge.jpg"));
        packList.add(new Pack(2, "Voyage Belgique", null, "https://media.routard.com/image/73/7/belgique-gand.1487737.c1000x300.jpg"));
        packList.add(new Pack(3, "Voyage Zambie", null, "https://img.ev.mu/images/portfolio/pays/245/600x400/846346.jpg"));
        packList.add(new Pack(4, "Voyage Bois de boulogne", null, "https://ak.jogurucdn.com/media/image/p25/place-2016-01-4-12-Boisdeboulogne2065e49fc359db8a638314b88f9f216d.jpg"));
        packList.add(new Pack(5, "Voyage Danemark", null, "https://live.staticflickr.com/1831/42367565350_b3577e9f9b_b.jpg"));
        rVPackList.setHasFixedSize(true);
        rVLayout = new GridLayoutManager(this, 3);
        rVPackList.setLayoutManager(rVLayout);
        rVadapter = new PackListAdapter(packList, getApplicationContext(), new PackListAdapter.OnItemClickListener() {
            @Override
            public void onClick(int id) {
//                Intent intent = new Intent(PackList.this, PackDetails.class);
//                intent.putExtra("currentPack", id);
//                startActivity(intent);
                Toast.makeText(PackList.this, "détails du pack", Toast.LENGTH_LONG).show();            }
        });
        rVPackList.setAdapter(rVadapter);
    }
}
