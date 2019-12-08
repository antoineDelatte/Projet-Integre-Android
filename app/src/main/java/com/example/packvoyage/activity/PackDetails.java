package com.example.packvoyage.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.util.Log;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        packDetailVM = ViewModelProviders.of(this).get(PackDetailVM.class);
        setContentView(R.layout.activity_pack_details);
        ButterKnife.bind(this);
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
