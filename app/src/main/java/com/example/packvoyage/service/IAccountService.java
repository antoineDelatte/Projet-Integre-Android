package com.example.packvoyage.service;

import com.example.packvoyage.bindingModel.UserForEvaluationBindingModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IAccountService {
    String BASE_URL = "https://trip4studentnathansurquin.azurewebsites.net/";
    String CLOUNDINARY_API = "https://api.cloudinary.com/v1_1/etu36459/image/";

    @GET("Account")
    Call<UserForEvaluationBindingModel> getAccountInfo(@Query("userId") String userId);
}
