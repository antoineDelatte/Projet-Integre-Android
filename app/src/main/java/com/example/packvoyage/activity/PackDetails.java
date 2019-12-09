package com.example.packvoyage.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
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
import com.example.packvoyage.repository.PackDao;

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
    @BindView(R.id.pack_details_show_activity_fragment)
    public Button display_activities_fragment;
    @BindView(R.id.pack_details_show_flights_fragment)
    public Button display_flights_fragment;
    @BindView(R.id.pack_details_show_accommodations_fragment)
    public Button display_housing_fragment;
    private PackDao packDao;
    private Pack selectedPack;
    private static final String ACTIVITIES = "activities";
    private static final String FLIGHTS = "flights";
    private static final String HOUSING = "housing";

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
        /*packDetailVM.getCurrentPack().observe(this, pack -> {
                pack_description.setText(pack.getDescription());
                pack_name.setText(pack.getName());
        });*/
        display_activities_fragment.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                changeFragment(ACTIVITIES);
            }
        });
        display_flights_fragment.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                changeFragment(FLIGHTS);
            }
        });
        display_housing_fragment.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                changeFragment(HOUSING);
            }
        });
        packDao = new PackDao();
        selectedPack = packDao.getPackWithImageAndDescription(packId);
        pack_description.setText(selectedPack.getDescription());
        pack_name.setText(selectedPack.getName());
    }

    @Override
    public void setCurrentPack(Pack pack) {
        packDetailVM.setCurrentPack(pack);
    }

    private void changeFragment(String selectedFragment){
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment newFragment;
        switch (selectedFragment){
            case "activities" :
                newFragment = new ActivityList();
                break;
            case "flights" :
                newFragment = new FlightListOfPack();
                break;
            default :
                newFragment = new ActivityList();
                // todo changer par accomodation fragment
        }
        fragmentTransaction.replace(R.id.pack_details_fragment_container, newFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
