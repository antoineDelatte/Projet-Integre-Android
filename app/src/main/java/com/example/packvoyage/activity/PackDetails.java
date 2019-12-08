package com.example.packvoyage.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.packvoyage.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PackDetails extends AppCompatActivity {

    @BindView(R.id.pack_details_pack_description)
    public TextView pack_description;
    @BindView(R.id.pack_details_pack_name)
    public TextView pack_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pack_details);
        ButterKnife.bind(this);
        pack_description.setText("super voyage en zambie pour visiter la savane et se faire dévorer par des lions affamés");
        pack_name.setText("Voyage zambie");
    }
}
