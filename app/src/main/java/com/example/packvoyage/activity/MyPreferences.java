package com.example.packvoyage.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.packvoyage.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyPreferences extends AppCompatActivity {

    @BindView(R.id.bottom_navigation)
    public BottomNavigationView bottom_navbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_preferences);
        ButterKnife.bind(this);

        bottom_navbar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Intent intent;
                switch(menuItem.getItemId()){
                    case R.id.ic_navbar_home :
                        intent = new Intent(MyPreferences.this, PackList.class);
                        startActivity(intent);
                        break;
                    case R.id.ic_navbar_bookings :
                        intent = new Intent(MyPreferences.this, MyBookings.class);
                        startActivity(intent);
                        break;
                }
                return false;
            }
        });
    }
}
