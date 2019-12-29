package com.example.packvoyage.service;

import com.example.packvoyage.bindingModel.UserBindingModel;
import com.example.packvoyage.model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ILoginService {
    String BASE_URL = "https://trip4studentnathansurquin.azurewebsites.net/";

    @POST("Jwt")
    Call<User> login(@Body UserBindingModel user);
}
