package com.example.packvoyage.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
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
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class fragmentHomePackDetails extends Fragment {

    public static final String TAG = "PACK_DETAILS";

    @BindView(R.id.pack_details_pack_description)
    public TextView pack_description;
    @BindView(R.id.pack_details_pack_name)
    public TextView pack_name;
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

    private static final int ACTIVITIES = 1;
    private static final int FLIGHTS = 2;
    private static final int HOUSING = 3;

    public fragmentHomePackDetails(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_home_pack_details, container, false);
        ButterKnife.bind(this, view);

        String languageCode = Locale.getDefault().getLanguage().equals("fr") ? "fr":"en";
        packDetailVM.getSelectedPackId().observe(getViewLifecycleOwner(), id -> {
            packDao.loadPackDescription(id, languageCode, packDetailVM, getContext());
        });

        packDetailVM.getCurrentPackDescription().observe(getViewLifecycleOwner(), description -> {
            pack_description.setText(description);
        });
        packDetailVM.getSelectedPackName().observe(getViewLifecycleOwner(), name -> {
            pack_name.setText(name);
        });

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

        return view;
    }

    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        packDetailVM = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(PackDetailVM.class);
        packDao = SingletonDao.getPackDao();
    }

    private void changeFragment(int selectedFragment){
        try {
            FragmentManager fragmentManager = getFragmentManager();
            assert fragmentManager != null;
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
    public void onAttach(@NonNull Context context){
        super.onAttach(context);
        try{
            parent = (IMainActivity)context;
        }
        catch (ClassCastException e){
            e.printStackTrace();
        }
    }
}
