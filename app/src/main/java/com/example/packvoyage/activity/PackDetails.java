package com.example.packvoyage.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.packvoyage.R;
import com.example.packvoyage.ViewModel.PackDetailVM;
import com.example.packvoyage.model.Pack;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PackDetails extends AppCompatActivity implements IPackDetails{

    @BindView(R.id.pack_details_pack_description)
    public TextView pack_description;
    @BindView(R.id.pack_details_pack_name)
    public TextView pack_name;
    private PackDetailVM packDetailVM;
    private int packId;
    @BindView(R.id.pack_details_book_this_pack)
    public Button bookThisPack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        packDetailVM = ViewModelProviders.of(this).get(PackDetailVM.class);
        Intent intent = getIntent();
        if(intent != null){
            packId = intent.getIntExtra("currentPack", 1);
        }
        packDetailVM.setSelectedPackId(packId);
        setContentView(R.layout.activity_pack_details);
        ButterKnife.bind(this);
        bookThisPack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToPackOptionsForReserv = new Intent(PackDetails.this, ChooseOptionsForBooking.class);
                goToPackOptionsForReserv.putExtra("selected_pack_id", packId);
                startActivity(goToPackOptionsForReserv);
            }
        });
        packDetailVM.getCurrentPack().observe(this, pack -> {
                pack_description.setText(pack.getDescription());
                pack_name.setText(pack.getName());
        });
    }

    @Override
    public void setCurrentPack(Pack pack) {
        packDetailVM.setCurrentPack(pack);
    }
}
