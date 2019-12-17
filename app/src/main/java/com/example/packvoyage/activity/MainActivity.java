package com.example.packvoyage.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.packvoyage.R;
import com.example.packvoyage.ViewModel.PackDetailVM;
import com.example.packvoyage.constant.constant;
import com.example.packvoyage.fragment.fragmentHomeBookingOptions;
import com.example.packvoyage.fragment.fragmentHomePackDetails;
import com.example.packvoyage.fragment.fragmentHomePackList;
import com.example.packvoyage.fragment.fragmentMyBookings;
import com.example.packvoyage.fragment.fragmentMyPreferences;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements IMainActivity {

    @BindView(R.id.bottom_navigation)
    public BottomNavigationView bottom_navbar;

    private PackDetailVM packVM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        changeFragment(constant.HOME);

        bottom_navbar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch(menuItem.getItemId()){
                    case(R.id.ic_navbar_home) :
                        changeFragment(constant.HOME);
                        break;
                    case R.id.ic_navbar_preferences :
                        changeFragment(constant.PREFERENCES);
                        break;
                    case R.id.ic_navbar_bookings :
                        changeFragment(constant.MY_BOOKINGS);
                }
                return false;
            }
        });
    }

    public void changeFragment(int selectedFragment){

        FragmentManager fragmentManager = this.getSupportFragmentManager();

        Fragment newFragment;
        switch (selectedFragment){
            case constant.HOME :
                newFragment = new fragmentHomePackList();
                break;
            case constant.PREFERENCES :
                newFragment = new fragmentMyPreferences();
                break;
            case constant.MY_BOOKINGS:
                newFragment = new fragmentMyBookings();
                break;
            case constant.PACK_DETAILS :
                newFragment = new fragmentHomePackDetails();
                break;
            case constant.OPTIONS_FOR_BOOKING :
                newFragment = new fragmentHomeBookingOptions();
                break;
            default :
                newFragment = new fragmentHomePackList();

        }
        boolean fragmentPopped = fragmentManager.popBackStackImmediate(newFragment.getClass().getName(), 0);
        if(!fragmentPopped){
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
            fragmentTransaction.replace(R.id.main_activity_fragment_container, newFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    }
}
