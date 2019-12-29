package com.example.packvoyage.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.packvoyage.R;
import com.example.packvoyage.Singleton.SingletonDao;
import com.example.packvoyage.ViewModel.PackDetailVM;
import com.example.packvoyage.activity.IMainActivity;
import com.example.packvoyage.repository.PackDao;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class fragmentHomePackDetails extends Fragment {

    public static final String TAG = "PACK_DETAILS";

    @BindView(R.id.pack_details_pack_description)
    public TextView pack_description;
    @BindView(R.id.pack_details_pack_name)
    public TextView pack_name;
    private String packName;
    @BindView(R.id.pack_details_book_this_pack)
    public Button bookThisPack;
    @BindView(R.id.pack_details_show_activity_fragment)
    public Button display_activities_fragment;
    @BindView(R.id.pack_details_show_flights_fragment)
    public Button display_flights_fragment;
    @BindView(R.id.pack_details_show_accommodations_fragment)
    public Button display_housing_fragment;
    private PackDao packDao;
    private PackDetailVM packDetailVM;
    private IMainActivity parent;
    private int packId;
    private String packDescriptionText;
    private static final int ACTIVITIES = 1;
    private static final int FLIGHTS = 2;
    private static final int HOUSING = 3;

    public fragmentHomePackDetails(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_home_pack_details, container, false);
        ButterKnife.bind(this, view);

        changeFragment(ACTIVITIES);

        bookThisPack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parent.changeFragment(fragmentHomeBookingOptions.TAG);
            }
        });
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
        //pack_description.setText(packDescriptionText);
        pack_name.setText(packName);

        return view;
    }

    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        packDetailVM = ViewModelProviders.of(getActivity()).get(PackDetailVM.class);
        packDao = SingletonDao.getPackDao();

        String languageCode = Locale.getDefault().getLanguage().equals("fr") ? "fr":"en";
        packDetailVM.getSelectedPackId().observe(getViewLifecycleOwner(), id -> {
            packId = id;
            packDao.loadPackDescription(id, languageCode, packDetailVM);
        });

        /*packDetailVM.getCurrentPackDescription().observe(getViewLifecycleOwner(), description -> {
            packDescriptionText = description;
        });*/
        packDetailVM.getSelectedPackName().observe(getViewLifecycleOwner(), name -> packName = name);
    }

    private void changeFragment(int selectedFragment){
        try {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            Fragment newFragment;
            switch (selectedFragment){
                case ACTIVITIES :
                    newFragment = new ActivityList();
                    break;
                case FLIGHTS :
                    newFragment = new FlightListOfPack();
                    break;
                default :
                    newFragment = new AccommodationList();
            }
            fragmentTransaction.replace(R.id.pack_details_fragment_container, newFragment);
            fragmentTransaction.commit();
        }
        catch(NullPointerException e){
            e.printStackTrace();
        }
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
