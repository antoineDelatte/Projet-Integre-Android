package com.example.packvoyage.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.packvoyage.R;
import com.example.packvoyage.repository.PackDao;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChooseOptionsForBooking extends AppCompatActivity {

    @BindView(R.id.choose_options_for_booking_pack_name)
    public TextView packName;
    private int selectedPackId;
    private PackDao packDao;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_options_for_booking);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        int selectedPackId = intent.getIntExtra("selected_pack_id", 1);
        


    }
}
