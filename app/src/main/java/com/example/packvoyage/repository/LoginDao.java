package com.example.packvoyage.repository;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.example.packvoyage.R;
import com.example.packvoyage.ViewModel.LoginVM;
import com.example.packvoyage.ViewModel.PackDetailVM;
import com.example.packvoyage.bindingModel.UserBindingModel;
import com.example.packvoyage.model.User;
import com.example.packvoyage.service.ILoginService;

import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginDao {

    private static SharedPreferences sharedPref;
    private static SharedPreferences.Editor editor;

    public void login(LoginVM loginVM, UserBindingModel userToSignIn, Context context) {
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
                    Toast.makeText(context, context.getResources().getString(R.string.wrong_account_information), Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(context, R.string.registration_successful, Toast.LENGTH_SHORT).show();
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
