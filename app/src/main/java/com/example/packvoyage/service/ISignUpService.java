package com.example.packvoyage.service;

import com.example.packvoyage.bindingModel.UserBindingModel;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ISignUpService {
    String BASE_URL = "https://trip4studentnathansurquin.azurewebsites.net/";

    @POST("Account")
    Call<ResponseBody> register(@Body UserBindingModel user);
}