package com.example.packvoyage.repository;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.example.packvoyage.Constant.Constants;
import com.example.packvoyage.R;
import com.example.packvoyage.Utils.ConnectionState;
import com.example.packvoyage.ViewModel.LoginVM;
import com.example.packvoyage.bindingModel.UserBindingModel;
import com.example.packvoyage.model.User;
import com.example.packvoyage.service.ILoginService;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginDao {

    public void login(LoginVM loginVM, UserBindingModel userToSignIn, Context context) {

        if(!ConnectionState.isNetworkAvailable(context)){
            loginVM.setLoginStatus(Constants.NO_CONNECTION);
            return;
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ILoginService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ILoginService loginService = retrofit.create(ILoginService.class);
        Call<User> call = loginService.login(userToSignIn);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (!response.isSuccessful()) {
                    if(response.code() == 401){
                        loginVM.setLoginStatus(response.code());
                        return;
                    }
                    if(response.code() == 400 || response.code() == 404){
                        loginVM.setLoginStatus(response.code());
                        return;
                    }
                }
                User user = response.body();
                loginVM.setLoggedUser(user);
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("Trip4Student", Objects.requireNonNull(t.getMessage()));
            }
        });
    }
}
