package com.example.packvoyage.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.example.packvoyage.R;
import com.example.packvoyage.ViewModel.LoginVM;
import com.example.packvoyage.bindingModel.UserBindingModel;
import com.example.packvoyage.repository.LoginDao;
import com.example.packvoyage.repository.SignUpDao;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignUp extends AppCompatActivity {

    public static final int MIN_LENGTH_USERNAME = 6;
    public static final int MIN_LENGTH_NAME = 2;
    public static final int MIN_LENGTH_PASSWORD = 8;
    public static final String DEFAULT_PROFILE_PICTURE_URI = "https://moonvillageassociation.org/wp-content/uploads/2018/06/default-profile-picture1.jpg";

    private LoginVM loginVM;
    private LoginDao loginDao;
    private SignUpDao signUpDao;
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;


    @BindView(R.id.textinputlayout_username)
    public TextInputLayout username;

    @BindView(R.id.textinputlayout_lastname)
    public TextInputLayout lastname;

    @BindView(R.id.textinputlayout_firstname)
    public TextInputLayout firstname;

    @BindView(R.id.textInputLayout_email_address)
    public TextInputLayout emailAddress;

    @BindView(R.id.textInputLayout_password)
    public TextInputLayout password;

    @BindView(R.id.textInputLayout_confirm_password)
    public TextInputLayout confirm_password;

    @BindView(R.id.button_sign_up)
    Button signIn;

    @Override
    public void onStart() {
        super.onStart();
        signIn.setEnabled(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);

        signIn.setEnabled(true);
        loginVM = ViewModelProviders.of(this).get(LoginVM.class);
        loginDao = new LoginDao();
        signUpDao = new SignUpDao();

        loginVM.getSignUpStatus().observe(this, signUpStatus -> {
            switch (signUpStatus){
                case 409 :
                    Toast.makeText(getApplicationContext(), getApplicationContext().getResources().getString(R.string.email_or_username_already_exists), Toast.LENGTH_LONG).show();
                    signIn.setEnabled(true);
                    return;
                case 400 :
                case 408 :
                case 500 :
                    // internal server error
                    Toast.makeText(getApplicationContext(), getApplicationContext().getResources().getString(R.string.internal_server_error), Toast.LENGTH_LONG).show();
                    signIn.setEnabled(true);
                    return;
                case 200 :
                    Toast.makeText(getApplicationContext(), getApplicationContext().getResources().getString(R.string.registration_successful), Toast.LENGTH_LONG).show();
                    // generate token for the user
                    UserBindingModel userBindingModel = new UserBindingModel();
                    userBindingModel.setEmail(Objects.requireNonNull(emailAddress.getEditText()).getText().toString());
                    userBindingModel.setPassword(Objects.requireNonNull(password.getEditText()).getText().toString());
                    loginDao.login(loginVM, userBindingModel, getApplicationContext());
                    return;
                default :
                    Toast.makeText(getApplicationContext(), getApplicationContext().getResources().getString(R.string.error_during_sign_up), Toast.LENGTH_LONG).show();
                    signIn.setEnabled(true);
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
            Toast.makeText(this, R.string.registration_successful, Toast.LENGTH_SHORT).show();
            startActivity(intent);
        });

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean usernameOk = validateUsername();
                boolean firstnameOk = validateFirstName();
                boolean lastnameOk = validateLastName();
                boolean emailAddressOk = validateEmail();
                boolean passwordOk = validatePassword();
                boolean confirmPasswordOk = validateConfirmPassword();
                if(usernameOk && firstnameOk && lastnameOk && emailAddressOk && passwordOk && confirmPasswordOk){
                    // signup the user
                    UserBindingModel userBindingModel = new UserBindingModel();
                    userBindingModel.setConfirmPassword(Objects.requireNonNull(confirm_password.getEditText()).getText().toString().trim());
                    userBindingModel.setFirstName(Objects.requireNonNull(firstname.getEditText()).getText().toString());
                    userBindingModel.setLastName(Objects.requireNonNull(lastname.getEditText()).getText().toString());
                    userBindingModel.setEmail(Objects.requireNonNull(emailAddress.getEditText()).getText().toString().trim());
                    userBindingModel.setPassword(Objects.requireNonNull(password.getEditText()).getText().toString().trim());
                    String usernameToString = Objects.requireNonNull(username.getEditText()).getText().toString().trim();
                    usernameToString = usernameToString.replaceAll("\\s+","");
                    userBindingModel.setUsername(usernameToString);
                    signIn.setEnabled(false);
                    signUpDao.registerAccount(loginVM, userBindingModel, getApplicationContext());
                }
            }
        });
    }

    private boolean validateUsername(){
        this.username.setError(null);
        String name = this.username.getEditText().getText().toString().trim();
        if(name.isEmpty()){
            this.username.setError(getResources().getString(R.string.can_not_be_empty));
            return false;
        }else{
            if(name.length() < MIN_LENGTH_USERNAME){
                this.username.setError(getResources().getString(R.string.min_character_username));
                return false;
            }
        }
        return true;
    }

    private boolean validateFirstName(){
        this.firstname.setError(null);
        String name = this.firstname.getEditText().getText().toString().trim();
        if(name.isEmpty()){
            this.firstname.setError(getResources().getString(R.string.can_not_be_empty));
            return false;
        }else{
            if(name.length() < MIN_LENGTH_NAME){
                this.firstname.setError(getResources().getString(R.string.min_character_name));
                return false;
            }else{
                if(name.matches(".*\\d.*")){
                    this.firstname.setError(getResources().getString(R.string.can_not_contain_digit));
                    return false;
                } else {
                    if (!name.matches("[a-zA-Z]*")){
                        this.firstname.setError(getResources().getString(R.string.can_not_contain_special_character));
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private boolean validateLastName(){
        this.lastname.setError(null);
        String name = this .lastname.getEditText().getText().toString().trim();
        if(name.isEmpty()){
            this.lastname.setError(getResources().getString(R.string.can_not_be_empty));
            return false;
        }else{
            if(name.length() < MIN_LENGTH_NAME){
                this.lastname.setError(getResources().getString(R.string.min_character_name));
                return false;
            }else{
                if(name.matches(".*\\d.*")){
                    this.lastname.setError(getResources().getString(R.string.can_not_contain_digit));
                    return false;
                } else {
                    if (!name.matches("[a-zA-Z]*")){
                        this.lastname.setError(getResources().getString(R.string.can_not_contain_special_character));
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private boolean validateEmail(){
        this.emailAddress.setError(null);
        String mail = this.emailAddress.getEditText().getText().toString().trim();
        if(mail.isEmpty()){
            this.emailAddress.setError(getResources().getString(R.string.can_not_be_empty));
            return false;
        }else{
            if(!mail.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$")){
                this.emailAddress.setError(getResources().getString(R.string.must_be_email));
                return false;
            }
        }
        return true;
    }

    private boolean validatePassword(){
        this.password.setError(null);
        String word = this.password.getEditText().getText().toString().trim();
        if(word.isEmpty()){
            this.password.setError(getResources().getString(R.string.can_not_be_empty));
            return false;
        }else{
            if(word.length() < MIN_LENGTH_PASSWORD){
                this.password.setError(getResources().getString(R.string.min_character_password));
                return false;
            }else{
                if(!word.matches(".*\\d.*")){
                    this.password.setError(getResources().getString(R.string.password_must_contain_digit));
                    return false;
                }else{
                    if(!word.matches(".*[A-Z].*")) {
                        this.password.setError(getResources().getString(R.string.password_must_contain_capital_letter));
                        return false;
                    } else{
                        if(!word.matches(".*[a-z].*")){
                            this.password.setError(getResources().getString(R.string.password_must_contain_small_letter));
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    private boolean validateConfirmPassword(){
        this.confirm_password.setError(null);
        String word = this.confirm_password.getEditText().getText().toString().trim();
        if(word.isEmpty()){
            this.confirm_password.setError(getResources().getString(R.string.can_not_be_empty));
            return false;
        }else{
            if(!word.equals(this.password.getEditText().getText().toString())){
                this.confirm_password.setError(getResources().getString(R.string.must_match_password));
                return false;
            }
        }
        return true;
    }
}
