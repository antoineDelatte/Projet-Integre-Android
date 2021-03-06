package com.example.packvoyage.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.packvoyage.Constant.Constants;
import com.example.packvoyage.R;
import com.example.packvoyage.Singleton.SingletonDao;
import com.example.packvoyage.ViewModel.PackDetailVM;
import com.example.packvoyage.fragment.RollingFragment;
import com.example.packvoyage.fragment.fragmentBookedPackDetails;
import com.example.packvoyage.fragment.fragmentHomeBookingOptions;
import com.example.packvoyage.fragment.fragmentHomePackDetails;
import com.example.packvoyage.fragment.fragmentHomePackList;
import com.example.packvoyage.fragment.fragmentMyBookings;
import com.example.packvoyage.fragment.fragmentMyPreferences;
import com.example.packvoyage.model.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements IMainActivity {

    @BindView(R.id.bottom_navigation)
    public BottomNavigationView bottom_navbar;
    private HashMap<String, Integer>fragment_nav_bar_order = new HashMap<>();
    private PackDetailVM packVM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        fragment_nav_bar_order.put(fragmentMyPreferences.TAG, 1);
        fragment_nav_bar_order.put(fragmentHomePackList.TAG, 2);
        fragment_nav_bar_order.put(fragmentHomeBookingOptions.TAG, 2);
        fragment_nav_bar_order.put(fragmentHomePackDetails.TAG, 2);
        fragment_nav_bar_order.put(fragmentMyBookings.TAG, 3);
        fragment_nav_bar_order.put(fragmentBookedPackDetails.TAG, 3);

        String userId = getIntent().getStringExtra("user_id");
        packVM = ViewModelProviders.of(this).get(PackDetailVM.class);
        packVM.setCurrentUserId(userId);
        SingletonDao.getAccountDao().loadAccountInformations(packVM, this, userId);

        FragmentManager fragmentManager = this.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment newFragment = new fragmentHomePackList();
        fragmentTransaction.replace(R.id.main_activity_fragment_container, newFragment);
        fragmentTransaction.commit();

        packVM.getApiCallStatus().observe(this, status -> {
            if(status == null)
                return;
            String message ="";
            switch (status) {
                case Constants.NO_CONNECTION :
                    message = this.getResources().getString(R.string.no_connection);
                    break;
                case 200:
                    return;
                default :
                    message = this.getResources().getString(R.string.internal_server_error);
                    break;
            }
            if(message.length()==0)
                return;
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            packVM.setApiCallStatus(null);
        });

        bottom_navbar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch(menuItem.getItemId()){
                    case(R.id.ic_navbar_home) :
                        changeFragment(fragmentHomePackList.TAG);
                        break;
                    case R.id.ic_navbar_preferences :
                        changeFragment(fragmentMyPreferences.TAG);
                        break;
                    case R.id.ic_navbar_bookings :
                        changeFragment(fragmentMyBookings.TAG);
                }
                return false;
            }
        });
    }

    public void changeFragment(String selectedFragmentTag){

        String currentFragmentTag = getCurrentFragmentTag();
        if(selectedFragmentTag == currentFragmentTag)
            return;
        if(currentFragmentTag == null)
            currentFragmentTag = fragmentHomePackList.TAG;

        FragmentManager fragmentManager = this.getSupportFragmentManager();

        Fragment newFragment;
        switch (selectedFragmentTag){
            case fragmentMyPreferences.TAG :
                newFragment = new fragmentMyPreferences();
                break;
            case fragmentMyBookings.TAG:
                newFragment = new fragmentMyBookings();
                break;
            case fragmentHomePackDetails.TAG :
                newFragment = new fragmentHomePackDetails();
                break;
            case fragmentHomeBookingOptions.TAG :
                newFragment = new fragmentHomeBookingOptions();
                break;
            case fragmentBookedPackDetails.TAG :
                newFragment = new fragmentBookedPackDetails();
                break;
            case fragmentBookedPackDetails.SECRET_CODE :
                newFragment = new RollingFragment();
                break;
            default :
                newFragment = new fragmentHomePackList();
        }
        boolean fragmentPopped = false;
        try{
            fragmentPopped = fragmentManager.popBackStackImmediate(newFragment.getClass().getName(), 0);
        }
        catch (IllegalStateException e){
            Log.e("Trip4", e.getMessage());
        }
        if(!fragmentPopped) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            setCustomAnimation(fragmentTransaction, currentFragmentTag, selectedFragmentTag);
            fragmentTransaction.replace(R.id.main_activity_fragment_container, newFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

    private void setCustomAnimation(FragmentTransaction fragmentTransaction, String currentFragmentTag, String selectedFragmentTag){

        //spinning
        if(selectedFragmentTag == fragmentBookedPackDetails.SECRET_CODE){
            fragmentTransaction.setCustomAnimations(R.anim.rotation, R.anim.fade_out_personalized);
            return;
        }

        // de home à pack details
        if(currentFragmentTag == fragmentHomePackList.TAG && selectedFragmentTag == fragmentHomePackDetails.TAG){
            fragmentTransaction.setCustomAnimations(R.anim.slide_in_from_bottom, R.anim.slide_out_to_top, R.anim.slide_in_from_top, R.anim.slide_out_to_bottom);
            return;
        }

        // de pack details à my bookings
        if(currentFragmentTag == fragmentHomePackDetails.TAG && selectedFragmentTag == fragmentHomeBookingOptions.TAG){
            fragmentTransaction.setCustomAnimations(R.anim.slide_in_from_top, R.anim.slide_out_to_bottom, R.anim.slide_in_from_bottom, R.anim.slide_out_to_top);
            return;
        }

        // de my bookings à my booked pack detail
        if(currentFragmentTag == fragmentMyBookings.TAG && selectedFragmentTag == fragmentBookedPackDetails.TAG){
            fragmentTransaction.setCustomAnimations(R.anim.slide_in_from_bottom, R.anim.slide_out_to_top, R.anim.slide_in_from_top, R.anim.slide_out_to_bottom);
            return;
        }

        // de my booked pack detail à my bookings
        if(currentFragmentTag == fragmentBookedPackDetails.TAG && selectedFragmentTag == fragmentMyBookings.TAG){
            fragmentTransaction.setCustomAnimations(R.anim.slide_in_from_top, R.anim.slide_out_to_bottom, R.anim.slide_in_from_bottom, R.anim.slide_out_to_top);
            return;
        }

        // de pack details ou booking options à home
        if((currentFragmentTag == fragmentHomePackDetails.TAG || currentFragmentTag == fragmentHomeBookingOptions.TAG) && selectedFragmentTag == fragmentHomePackList.TAG){
            fragmentTransaction.setCustomAnimations(R.anim.slide_in_from_top, R.anim.slide_out_to_bottom, R.anim.slide_in_from_bottom, R.anim.slide_out_to_top);
            return;
        }

        // go right
        if(fragment_nav_bar_order.get(currentFragmentTag) < fragment_nav_bar_order.get(selectedFragmentTag))
            fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
            // go left
        else
            fragmentTransaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left);
    }

    private String getCurrentFragmentTag(){
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.main_activity_fragment_container);
        if(currentFragment instanceof fragmentHomePackList)
            return fragmentHomePackList.TAG;
        if(currentFragment instanceof fragmentHomePackDetails)
            return fragmentHomePackDetails.TAG;
        if(currentFragment instanceof fragmentHomeBookingOptions)
            return fragmentHomeBookingOptions.TAG;
        if(currentFragment instanceof fragmentMyBookings)
            return fragmentMyBookings.TAG;
        if(currentFragment instanceof fragmentMyPreferences)
            return fragmentMyPreferences.TAG;
        if(currentFragment instanceof fragmentBookedPackDetails)
            return fragmentBookedPackDetails.TAG;
        return null;
    }

    @Override
    public void onBackPressed(){
        if(shouldDoSomething())
            super.onBackPressed();
    }

    //should do something regarde s'il ne faut pas associer une autre action au back button que celle de base
    private boolean shouldDoSomething(){
        if(getCurrentFragmentTag() == fragmentBookedPackDetails.TAG){
            boolean somethingHappened;
            fragmentBookedPackDetails fragment = (fragmentBookedPackDetails)getSupportFragmentManager().findFragmentById(R.id.main_activity_fragment_container);
            if(fragment != null) {
                somethingHappened = fragment.backPressed();
                return !somethingHappened;
            }
        }
        return true;
    }
}
