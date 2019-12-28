package com.example.packvoyage.service;

import com.example.packvoyage.bindingModel.UserBindingModel;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.POST;

public interface ISignUpService {
    String BASE_URL = "https://trip4students.azurewebsites.net/Account/";

    @POST("Register")
    Call<ResponseBody> register(UserBindingModel user);
}