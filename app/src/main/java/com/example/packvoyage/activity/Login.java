package com.example.packvoyage.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.packvoyage.R;
import com.example.packvoyage.ViewModel.LoginVM;
import com.example.packvoyage.bindingModel.UserBindingModel;
import com.example.packvoyage.model.User;
import com.example.packvoyage.repository.LoginDao;

public class Login extends AppCompatActivity {

    private User user;
    private UserBindingModel userToLogin;
    private LoginVM loginVM;
    private LoginDao loginDao;
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;

    @BindView(R.id.connection_button)
    public Button connection_button;
    @BindView(R.id.create_account)
    public TextView register;
    @BindView(R.id.login_email_text)
    public TextView email;
    @BindView(R.id.login_password_text)
    public TextView password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        loginVM = ViewModelProviders.of(this).get(LoginVM.class);
        loginDao = new LoginDao();

        loginVM.setLoggedUser(null);
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
                String emailText = email.getText().toString();
                String passwordText = password.getText().toString();
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
