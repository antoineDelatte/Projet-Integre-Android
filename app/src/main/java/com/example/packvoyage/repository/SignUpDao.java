package com.example.packvoyage.repository;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.packvoyage.R;
import com.example.packvoyage.ViewModel.LoginVM;
import com.example.packvoyage.ViewModel.PackDetailVM;
import com.example.packvoyage.bindingModel.UserBindingModel;
import com.example.packvoyage.service.ISignUpService;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignUpDao {

    public void registerAccount(LoginVM loginVM, UserBindingModel userToRegister, Context context) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ISignUpService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ISignUpService signUpService = retrofit.create(ISignUpService.class);
        Call<ResponseBody> call = signUpService.register(userToRegister);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                loginVM.setSignUpStatus(response.code());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("Trip4Student", t.getMessage());
            }
        });
    }
}
