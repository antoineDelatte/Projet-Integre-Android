package com.example.packvoyage.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.packvoyage.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Login extends AppCompatActivity {


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }
}
