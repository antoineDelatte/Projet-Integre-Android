package com.example.packvoyage.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.packvoyage.Constant.Constants;
import com.example.packvoyage.R;
import com.example.packvoyage.ViewModel.LoginVM;
import com.example.packvoyage.bindingModel.UserBindingModel;
import com.example.packvoyage.repository.LoginDao;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Login extends AppCompatActivity {

    private UserBindingModel userToLogin;
    private LoginVM loginVM;
    private LoginDao loginDao;
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;

    @BindView(R.id.connection_button)
    public Button connection_button;
    @BindView(R.id.create_account)
    public TextView register;
    @BindView(R.id.email)
    public TextInputEditText email;
    @BindView(R.id.login_password_text)
    public TextInputEditText password;

    @Override
    public void onStart() {
        super.onStart();
        connection_button.setEnabled(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        loginVM = ViewModelProviders.of(this).get(LoginVM.class);
        loginDao = new LoginDao();

        loginVM.setLoggedUser(null);
        loginVM.setLoginStatus(null);

        loginVM.getLoginStatus().observe(this, status -> {
            if(status == null)
                return;
            connection_button.setEnabled(true);
            switch (status){
                case 401:
                    Toast.makeText(this, this.getResources().getString(R.string.wrong_account_information), Toast.LENGTH_LONG).show();
                    break;
                case 400:
                case 404:
                    Toast.makeText(this, this.getResources().getString(R.string.internal_server_error), Toast.LENGTH_LONG).show();
                    break;
                case Constants.NO_CONNECTION :
                    Toast.makeText(this, this.getResources().getString(R.string.no_connection), Toast.LENGTH_LONG).show();
            }
        });

        loginVM.getLoggedUser().observe(this, userWithIdAndToken -> {
            if(userWithIdAndToken == null)
                return;

            sharedPref = getSharedPreferences(getResources().getString(R.string.ACCESS_TOKEN), Context.MODE_PRIVATE);
            editor = sharedPref.edit();
            editor.putString(getResources().getString(R.string.ACCESS_TOKEN), userWithIdAndToken.getAccess_token());
            editor.apply();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.putExtra("user_id", userWithIdAndToken.getUser_id());

            startActivity(intent);
        });

        connection_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connection_button.setEnabled(false);
                String emailText = Objects.requireNonNull(email.getText()).toString();
                String passwordText = Objects.requireNonNull(password.getText()).toString();
                if(emailText.length() == 0 || passwordText.length() == 0){
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.invalid_account_information), Toast.LENGTH_SHORT).show();
                    return;
                }
                userToLogin = new UserBindingModel();
                userToLogin.setEmail(emailText);
                userToLogin.setPassword(passwordText);
                loginDao.login(loginVM, userToLogin, getApplicationContext());
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SignUp.class));
            }
        });
    }
}
